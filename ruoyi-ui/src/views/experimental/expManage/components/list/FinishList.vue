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
        <el-button type="warning" plain icon="el-icon-download" size="mini" :disabled="!selectedList || selectedList.length === 0" @click="handleExport"
          v-hasPermi="['exp:expManage:export']">批量导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="!selectedList || selectedList.length === 0" @click="handleBatchDelete"
          v-hasPermi="['exp:expManage:remove']">批量删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="expList" @selection-change="handleSelectionChange" border :max-height="tableMaxHeight">
      <el-table-column type="selection" width="55" align="center" fixed />
      <el-table-column label="实验编号" align="center" prop="expCode" width="160" />
      <el-table-column label="实验名称" align="center" prop="expName" min-width="240" :show-overflow-tooltip="true" />
      <el-table-column label="完成时间" align="center" prop="finishDate" width="160" />
      <el-table-column label="完成人" align="center" prop="createStaffName" width="100" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="140" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleDetail(scope.row)">详情</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['exp:expManage:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <descriptions-dialog ref="descriptionsDialog" />
  </div>
</template>

<script>
import { finishList, delExp } from '@/api/experimental/experimental'
import DescriptionsDialog from '../descriptions'

export default {
  name: 'FinishList',
  components: { DescriptionsDialog },
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      expList: [],
      selectedList: [],
      planTimeRange: [],
      tableMaxHeight: 500,
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
    this.calcTableHeight()
    window.addEventListener('resize', this.calcTableHeight)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.calcTableHeight)
  },
  methods: {
    getList() {
      this.loading = true
      finishList(this.queryParams).then(response => {
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
    handleDetail(row) {
      this.$refs.descriptionsDialog.open(row)
    },
    handleExport() {
      if (!this.selectedList || this.selectedList.length === 0) {
        this.$message.warning('请至少选择一条数据')
        return
      }
      const expIds = this.selectedList.map(item => item.expId).join(',')
      this.download('/experimental/expManage/export', { expIds: expIds }, `experiment_export.xlsx`)
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
    },
    calcTableHeight() {
      this.$nextTick(() => {
        this.tableMaxHeight = window.innerHeight - 380
      })
    }
  }
}
</script>
