<template>
  <div class="app-container home">
    <!-- 固定头部：问候语 + 统计卡片 -->
    <div class="home-header">
      <!-- 问候语 -->
      <el-card class="welcome-card" shadow="never">
      <div class="welcome-body">
        <div class="welcome-left">
          <div class="welcome-title">{{ greeting }}，{{ userName }}</div>
          <div class="welcome-desc">欢迎使用实验管理系统，今日共 {{ totalExp }} 条实验数据</div>
        </div>
        <div class="welcome-actions">
          <!-- <i class="el-icon-refresh refresh-btn" :class="{ spinning: refreshing }" @click="handleRefresh" title="刷新数据" /> -->
          <div class="welcome-icon">
            <i class="el-icon-sunrise-1" v-if="isMorning" />
            <i class="el-icon-sunny" v-else-if="isNoon" />
            <i class="el-icon-sunset" v-else-if="isAfternoon" />
            <i class="el-icon-moon" v-else />
          </div>
        </div>
      </div>
    </el-card>

    <!-- 实验统计卡片 -->
    <el-row :gutter="20" class="exp-stat-row">
      <el-col :span="8">
        <el-card class="stat-card stat-tobe" shadow="hover">
          <div class="stat-icon"><i class="el-icon-s-check" /></div>
          <div class="stat-info">
            <div class="stat-value">{{ statistics.tobeReviewed || 0 }}</div>
            <div class="stat-label">待审核实验</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card stat-pending" shadow="hover">
          <div class="stat-icon"><i class="el-icon-s-operation" /></div>
          <div class="stat-info">
            <div class="stat-value">{{ statistics.unFinish || 0 }}</div>
            <div class="stat-label">待处理实验</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="stat-card stat-finish" shadow="hover">
          <div class="stat-icon"><i class="el-icon-s-claim" /></div>
          <div class="stat-info">
            <div class="stat-value">{{ statistics.finished || 0 }}</div>
            <div class="stat-label">已完成实验</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    </div>

    <!-- 滚动内容区 -->
    <div class="home-scroll">
      <el-row :gutter="20" class="chart-row">
        <el-col :span="12">
          <el-card class="chart-card" shadow="hover">
            <div slot="header" class="chart-header">实验统计分布</div>
            <div ref="pieChart" style="width:100%;height:260px;" />
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card class="chart-card" shadow="hover">
            <div slot="header" class="chart-header">实验数量概览</div>
            <div ref="barChart" style="width:100%;height:260px;" />
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="chart-row">
        <el-col :span="24">
          <el-card class="chart-card" shadow="hover">
            <div slot="header" class="chart-header">各部门实验数量</div>
            <div ref="deptChart" style="width:100%;height:280px;" />
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import { expStatistics, expDeptStatistics } from '@/api/experimental/experimental'

export default {
  name: "Index",
  computed: {
    userName() {
      return this.$store.getters.name || '用户'
    },
    greeting() {
      const hour = new Date().getHours()
      if (hour >= 5 && hour < 12) return '早上好'
      if (hour >= 12 && hour < 14) return '中午好'
      if (hour >= 14 && hour < 18) return '下午好'
      if (hour >= 18 && hour < 23) return '晚上好'
      return '夜深了'
    },
    isMorning() {
      const hour = new Date().getHours()
      return hour >= 5 && hour < 12
    },
    isNoon() {
      const hour = new Date().getHours()
      return hour >= 12 && hour < 14
    },
    isAfternoon() {
      const hour = new Date().getHours()
      return hour >= 14 && hour < 18
    },
    totalExp() {
      return (this.statistics.tobeReviewed || 0) + (this.statistics.unFinish || 0) + (this.statistics.finished || 0)
    }
  },
  data() {
    return {
      statistics: {
        tobeReviewed: 0,
        unFinish: 0,
        finished: 0
      },
      pieChart: null,
      barChart: null,
      deptChart: null,
      deptData: [],
      refreshing: false
    }
  },
  mounted() {
    this.loadStatistics()
  },
  activated() {
    this.handleRefresh()
  },
  beforeDestroy() {
    if (this.pieChart) { this.pieChart.dispose(); this.pieChart = null }
    if (this.barChart) { this.barChart.dispose(); this.barChart = null }
    if (this.deptChart) { this.deptChart.dispose(); this.deptChart = null }
  },
  methods: {
    handleRefresh() {
      if (this.refreshing) return
      this.refreshing = true
      this.loadStatistics().finally(() => {
        setTimeout(() => { this.refreshing = false }, 600)
      })
    },
    loadStatistics() {
      return Promise.all([
        expStatistics(),
        expDeptStatistics()
      ]).then(([statRes, deptRes]) => {
        const data = statRes.data || {}
        this.statistics = {
          tobeReviewed: data.tobeReviewed || 0,
          unFinish: data.unFinish || 0,
          finished: data.finished || 0
        }
        this.deptData = deptRes.data || []
        this.$nextTick(() => {
          this.initPieChart()
          this.initBarChart()
          this.initDeptChart()
        })
      })
    },
    initPieChart() {
      const el = this.$refs.pieChart
      if (!el) return
      if (this.pieChart) { this.pieChart.dispose(); this.pieChart = null }
      this.pieChart = echarts.init(el)
      this.pieChart.setOption({
        tooltip: {
          trigger: 'item',
          backgroundColor: 'rgba(255,255,255,0.95)',
          borderColor: '#ebeef5',
          borderWidth: 1,
          textStyle: { color: '#303133', fontSize: 13 },
          extraCssText: 'box-shadow: 0 2px 12px rgba(0,0,0,0.1);border-radius:4px;',
          formatter: '{b}: {c} ({d}%)'
        },
        legend: {
          bottom: 8,
          icon: 'circle',
          itemWidth: 10,
          itemHeight: 10,
          itemGap: 20,
          textStyle: { color: '#606266', fontSize: 13 }
        },
        color: ['#f5a623', '#409EFF', '#67C23A'],
        series: [{
          type: 'pie',
          radius: ['45%', '70%'],
          center: ['50%', '42%'],
          avoidLabelOverlap: true,
          itemStyle: {
            borderRadius: 8,
            borderColor: '#fff',
            borderWidth: 3
          },
          label: {
            show: true,
            fontSize: 13,
            color: '#606266',
            formatter: '{b}\n{c} 条'
          },
          labelLine: {
            length: 12,
            length2: 8,
            smooth: true
          },
          emphasis: {
            scale: true,
            scaleSize: 8,
            itemStyle: {
              shadowBlur: 12,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0,0,0,0.15)'
            }
          },
          data: [
            { value: this.statistics.tobeReviewed, name: '待审核' },
            { value: this.statistics.unFinish, name: '待处理' },
            { value: this.statistics.finished, name: '已完成' }
          ]
        }]
      })
    },
    initBarChart() {
      const el = this.$refs.barChart
      if (!el) return
      if (this.barChart) { this.barChart.dispose(); this.barChart = null }
      this.barChart = echarts.init(el)
      const colors = ['#f5a623', '#409EFF', '#67C23A']
      this.barChart.setOption({
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(255,255,255,0.95)',
          borderColor: '#ebeef5',
          borderWidth: 1,
          textStyle: { color: '#303133', fontSize: 13 },
          extraCssText: 'box-shadow: 0 2px 12px rgba(0,0,0,0.1);border-radius:4px;',
          axisPointer: { type: 'shadow', shadowStyle: { color: 'rgba(0,0,0,0.02)' } }
        },
        grid: { top: 24, left: '2%', right: '4%', bottom: '4%', containLabel: true },
        xAxis: {
          type: 'category',
          data: ['待审核', '待处理', '已完成'],
          axisTick: { show: false },
          axisLine: { lineStyle: { color: '#dcdfe6' } },
          axisLabel: { color: '#606266', fontSize: 13 }
        },
        yAxis: {
          type: 'value',
          splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } },
          axisLabel: { color: '#909399', fontSize: 12 }
        },
        series: [{
          name: '实验数量',
          type: 'bar',
          barWidth: '45%',
          data: [
            { value: this.statistics.tobeReviewed, itemStyle: { color: colors[0] } },
            { value: this.statistics.unFinish, itemStyle: { color: colors[1] } },
            { value: this.statistics.finished, itemStyle: { color: colors[2] } }
          ],
          itemStyle: {
            borderRadius: [6, 6, 0, 0],
            shadowColor: 'rgba(0,0,0,0.08)',
            shadowBlur: 6,
            shadowOffsetY: 2
          },
          emphasis: {
            itemStyle: {
              shadowColor: 'rgba(0,0,0,0.15)',
              shadowBlur: 12
            }
          }
        }]
      })
    },
    initDeptChart() {
      const el = this.$refs.deptChart
      if (!el) return
      if (this.deptChart) { this.deptChart.dispose(); this.deptChart = null }
      this.deptChart = echarts.init(el)
      const deptNames = this.deptData.map(item => item.deptName || '未知部门')
      const tobeData = this.deptData.map(item => item.tobeReviewed || 0)
      const unfinData = this.deptData.map(item => item.unFinish || 0)
      const finData = this.deptData.map(item => item.finished || 0)
      this.deptChart.setOption({
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(255,255,255,0.95)',
          borderColor: '#ebeef5',
          borderWidth: 1,
          textStyle: { color: '#303133', fontSize: 13 },
          extraCssText: 'box-shadow: 0 2px 12px rgba(0,0,0,0.1);border-radius:4px;',
          axisPointer: { type: 'shadow', shadowStyle: { color: 'rgba(0,0,0,0.02)' } }
        },
        legend: {
          data: ['待审核', '待处理', '已完成'],
          top: 4,
          textStyle: { color: '#606266', fontSize: 13 }
        },
        grid: { top: 50, left: '2%', right: '2%', bottom: '8%', containLabel: true },
        xAxis: {
          type: 'category',
          data: deptNames,
          axisTick: { show: false },
          axisLine: { lineStyle: { color: '#dcdfe6' } },
          axisLabel: { color: '#606266', fontSize: 12, rotate: deptNames.length > 6 ? 30 : 0 }
        },
        yAxis: {
          type: 'value',
          splitLine: { lineStyle: { color: '#f0f0f0', type: 'dashed' } },
          axisLabel: { color: '#909399', fontSize: 12 }
        },
        series: [
          {
            name: '待审核',
            type: 'bar',
            stack: 'total',
            barWidth: '45%',
            data: tobeData,
            itemStyle: { color: '#f5a623', borderRadius: [0, 0, 0, 0] }
          },
          {
            name: '待处理',
            type: 'bar',
            stack: 'total',
            barWidth: '45%',
            data: unfinData,
            itemStyle: { color: '#409EFF', borderRadius: [0, 0, 0, 0] }
          },
          {
            name: '已完成',
            type: 'bar',
            stack: 'total',
            barWidth: '45%',
            data: finData,
            itemStyle: { color: '#67C23A', borderRadius: [4, 4, 0, 0] }
          }
        ]
      })
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  display: flex;
  flex-direction: column;
  height: calc(100vh - 84px);
}

.home-header {
  flex-shrink: 0;
}

.home-scroll {
  flex: 1;
  overflow-y: auto;
  padding-right: 6px;

  &::-webkit-scrollbar {
    width: 6px;
  }
  &::-webkit-scrollbar-thumb {
    background: #c0c4cc;
    border-radius: 3px;
  }
  &::-webkit-scrollbar-track {
    background: transparent;
  }
}

.exp-stat-row {
  margin-bottom: 12px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  transition: transform 0.25s ease, box-shadow 0.25s ease;

  &:hover {
    transform: translateY(-2px);
  }

  ::v-deep .el-card__body {
    display: flex;
    align-items: center;
    width: 100%;
    padding: 0;
  }
}

.stat-icon {
  width: 44px;
  height: 44px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22px;
  color: #fff;
  margin-right: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.25s ease, box-shadow 0.25s ease;

  .stat-card:hover & {
    transform: scale(1.08);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }
}

.stat-tobe .stat-icon {
  background: linear-gradient(135deg, #f5a623 0%, #e67e22 100%);
}

.stat-pending .stat-icon {
  background: linear-gradient(135deg, #409EFF 0%, #2c6fd1 100%);
}

.stat-finish .stat-icon {
  background: linear-gradient(135deg, #67C23A 0%, #45a049 100%);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 22px;
  font-weight: 700;
  color: #303133;
  line-height: 1.2;
}

.stat-label {
  font-size: 12px;
  color: #909399;
  margin-top: 2px;
}

.welcome-card {
  margin-bottom: 12px;
  border: none;
  background: linear-gradient(100deg, #ecf5ff 0%, #f0f9eb 60%, #fdf6ec 100%);
  position: relative;
  overflow: hidden;

  ::v-deep .el-card__body {
    padding: 14px 20px;
  }
}

.welcome-body {
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  z-index: 1;
}

.welcome-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  letter-spacing: 0.5px;
}

.welcome-desc {
  font-size: 12px;
  color: #606266;
  margin-top: 4px;
}

.welcome-actions {
  display: flex;
  align-items: center;
  gap: 10px;
}

.refresh-btn {
  font-size: 18px;
  color: #909399;
  cursor: pointer;
  transition: color 0.2s ease;

  &:hover {
    color: #409eff;
  }

  &.spinning {
    color: #409eff;
    animation: spin 0.8s linear infinite;
  }
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.welcome-icon {
  font-size: 40px;
  color: #409eff;
  opacity: 0.6;
  animation: float 3s ease-in-out infinite;
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-4px); }
}

.chart-card ::v-deep .el-card__header {
  padding: 8px 14px;
  font-size: 14px;
  font-weight: 600;
  color: #303133;
  border-bottom: 1px solid #f0f0f0;
}

.chart-card ::v-deep .el-card__body {
  padding: 8px 12px 12px;
}

.chart-row {
  margin-bottom: 20px;
}
</style>
