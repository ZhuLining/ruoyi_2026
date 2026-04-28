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
      <el-table-column label="计划时间" align="center" prop="planTime" width="180" />
      <el-table-column label="创建人" align="center" prop="createStaffName" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleCheck(scope.row)" v-hasPermi="['exp:expManage:check']">审核</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['exp:expManage:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 审核弹窗 -->
    <check-dialog ref="checkDialog" @refresh="getList" />
    <!-- 查看详情弹窗 -->
    <descriptions-dialog ref="descDialog" :showResult="false" />
  </div>
</template>

<script>
import { listExp, delExp } from '@/api/experimental/experimental'
import CheckDialog from '../check'
import DescriptionsDialog from '../descriptions'

export default {
  name: 'TobeReviewed',
  components: { CheckDialog, DescriptionsDialog },
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      expList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        expName: undefined,
        expStatus: 0
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listExp(this.queryParams).then(response => {
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
    handleCheck(row) {
      this.$refs.checkDialog.open(row)
    },
    handleView(row) {
      this.$refs.descDialog.open(row)
    },
    handleDelete(row) {
      this.$confirm('是否确认删除该实验？', '提示', { type: 'warning' }).then(() => {
        delExp([row]).then(() => {
          this.$message.success('删除成功')
          this.getList()
        })
      })
    }
  }
}
</script>
