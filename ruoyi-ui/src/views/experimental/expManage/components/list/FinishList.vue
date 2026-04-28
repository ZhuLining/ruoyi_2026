<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="实验名称" prop="expName">
        <el-input v-model="queryParams.expName" placeholder="请输入实验名称" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="expList">
      <el-table-column label="实验编号" align="center" prop="expCode" />
      <el-table-column label="实验名称" align="center" prop="expName" />
      <el-table-column label="完成时间" align="center" prop="finishDate" width="180" />
      <el-table-column label="完成人" align="center" prop="createStaffName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleDetail(scope.row)">详情</el-button>
          <el-button size="mini" type="text" icon="el-icon-download" @click="handleExport(scope.row)" v-hasPermi="['exp:expManage:export']">导出</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <descriptions-dialog ref="descriptionsDialog" />
  </div>
</template>

<script>
import { finishList } from '@/api/experimental/experimental'
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
      queryParams: {
        pageNum: 1,
        pageSize: 10,
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
      finishList(this.queryParams).then(response => {
        this.expList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleDetail(row) {
      this.$refs.descriptionsDialog.open(row)
    },
    handleExport(row) {
      this.download('/experimental/expManage/export', { expId: row.expId }, `${row.expCode || row.expId}_experiment.xlsx`)
    }
  }
}
</script>
