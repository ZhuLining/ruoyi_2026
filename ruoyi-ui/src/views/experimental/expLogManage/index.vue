<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="操作人" prop="operateStaffName">
        <el-input v-model="queryParams.operateStaffName" placeholder="请输入操作人" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="操作类型" prop="operationType">
        <el-select v-model="queryParams.operationType" placeholder="请选择操作类型" clearable size="small">
          <el-option label="删除" :value="0" />
          <el-option label="还原" :value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="实验名称" prop="expName">
        <el-input v-model="queryParams.expName" placeholder="请输入实验名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="操作时间">
        <el-date-picker v-model="dateRange" size="small" style="width: 240px" value-format="yyyy-MM-dd"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleClean"
          v-hasPermi="['exp:expLogManage:remove']">清空</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="logList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日志编号" align="center" prop="logId" width="80" />
      <el-table-column label="操作人" align="center" prop="operateStaffName" />
      <el-table-column label="操作类型" align="center" prop="operationType">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.operationType === 0" type="danger">删除</el-tag>
          <el-tag v-else type="success">还原</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="实验名称" align="center" prop="expName" />
      <el-table-column label="操作时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-refresh-left" @click="handleRecover(scope.row)"
            v-if="scope.row.operationType === 0"
            v-hasPermi="['exp:expLogManage:restore']">还原</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['exp:expLogManage:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listExpLog, delExpLog, cleanExpLog, recoverExpLog } from '@/api/experimental/expLog'

export default {
  name: 'ExpLogManage',
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      logList: [],
      dateRange: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        operateStaffName: undefined,
        operationType: undefined,
        expName: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listExpLog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.logList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.selection = selection
    },
    handleRecover(row) {
      this.$confirm('是否确认还原实验 "' + row.expName + '"？', '提示', { type: 'warning' }).then(() => {
        recoverExpLog([row]).then(() => {
          this.$message.success('还原成功')
          this.getList()
        })
      })
    },
    handleDelete(row) {
      this.$confirm('是否确认删除该日志？', '提示', { type: 'warning' }).then(() => {
        delExpLog(row.logId).then(() => {
          this.$message.success('删除成功')
          this.getList()
        })
      })
    },
    handleClean() {
      this.$confirm('是否确认清空所有日志？', '提示', { type: 'warning' }).then(() => {
        cleanExpLog().then(() => {
          this.$message.success('清空成功')
          this.getList()
        })
      })
    }
  }
}
</script>
