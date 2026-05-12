<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="实验编号" prop="expCode">
        <el-input v-model="queryParams.expCode" placeholder="请输入实验编号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="实验名称" prop="expName">
        <el-input v-model="queryParams.expName" placeholder="请输入实验名称" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计划时间" prop="planTimeRange">
        <el-date-picker
          v-model="planTimeRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          size="small"
          style="width: 240px;"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['exp:expManage:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" @click="handleBatchDelete"
          v-hasPermi="['exp:expManage:remove']">批量删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="expList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="实验编号" align="center" prop="expCode" />
      <el-table-column label="实验名称" align="center" prop="expName" />
      <el-table-column label="计划开始" align="center" prop="planStartTime" width="180" />
      <el-table-column label="计划结束" align="center" prop="planEndTime" width="180" />
      <el-table-column label="创建人" align="center" prop="createStaffName" />
      <el-table-column label="审核状态" align="center" prop="expStatus" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.expStatus == 1" type="success" size="mini">已通过</el-tag>
          <el-tag v-else-if="scope.row.expStatus == 2" type="danger" size="mini">已驳回</el-tag>
          <el-tag v-else-if="scope.row.expStatus == 3" type="warning" size="mini">退回修改</el-tag>
          <el-tag v-else type="info" size="mini">待审核</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="getTimeStatus(scope.row).type === 'info'" type="info" size="mini">未开始</el-tag>
          <el-tag v-else-if="getTimeStatus(scope.row).type === 'success'" type="success" size="mini">正常</el-tag>
          <el-tag v-else-if="getTimeStatus(scope.row).type === 'warning'" type="warning" size="mini">即将超时</el-tag>
          <el-tag v-else type="danger" size="mini">超时</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)"
            v-hasPermi="['exp:expManage:edit']">编辑</el-button>
          <el-button size="mini" type="text" icon="el-icon-document-checked" @click="handleResult(scope.row)"
            v-hasPermi="['exp:expManage:result']">结果</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['exp:expManage:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize" @pagination="getList" />

    <result-dialog ref="resultDialog" @refresh="getList" />
  </div>
</template>

<script>
import { backlogList, delExp } from '@/api/experimental/experimental'
import ResultDialog from '../result'

export default {
  name: 'UnFinishList',
  components: { ResultDialog },
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      expList: [],
      selectedList: [],
      planTimeRange: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        expName: undefined,
        expCode: undefined,
        params: {}
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      backlogList(this.queryParams).then(response => {
        this.expList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.queryParams.params = {
        planBeginTime: this.planTimeRange && this.planTimeRange.length ? this.planTimeRange[0] : undefined,
        planEndTime: this.planTimeRange && this.planTimeRange.length ? this.planTimeRange[1] : undefined
      }
      this.getList()
    },
    resetQuery() {
      this.planTimeRange = []
      this.queryParams.params = {}
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.selectedList = selection
    },
    handleAdd() {
      this.$emit('add')
    },
    getTimeStatus(row) {
      const now = new Date().getTime()
      const start = row.planStartTime ? new Date(row.planStartTime.replace(/-/g, '/')).getTime() : 0
      const end = row.planEndTime ? new Date(row.planEndTime.replace(/-/g, '/')).getTime() : 0
      if (!start || !end) return { type: 'info' }
      if (now < start) return { type: 'info' }
      if (now > end) return { type: 'danger' }
      if (end - now <= 3 * 24 * 60 * 60 * 1000) return { type: 'warning' }
      return { type: 'success' }
    },
    handleEdit(row) {
      this.$emit('edit', row)
    },
    handleResult(row) {
      this.$refs.resultDialog.open(row)
    },
    handleDelete(row) {
      this.$confirm('是否确认删除该实验？', '提示', { type: 'warning' }).then(() => {
        delExp([row]).then(() => {
          this.$message.success('删除成功')
          this.getList()
        })
      })
    },
    handleBatchDelete() {
      if (!this.selectedList || this.selectedList.length === 0) {
        this.$message.warning('请至少选择一条数据')
        return
      }
      this.$confirm('是否确认删除选中的 ' + this.selectedList.length + ' 条实验？', '提示', { type: 'warning' }).then(() => {
        delExp(this.selectedList).then(() => {
          this.$message.success('批量删除成功')
          this.getList()
        })
      })
    }
  }
}
</script>