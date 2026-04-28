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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-hasPermi="['exp:expManage:edit']">编辑</el-button>
          <el-button size="mini" type="text" icon="el-icon-document-checked" @click="handleResult(scope.row)" v-hasPermi="['exp:expManage:result']">结果</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <result-dialog ref="resultDialog" @refresh="getList" />
  </div>
</template>

<script>
import { backlogList } from '@/api/experimental/experimental'
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
      backlogList(this.queryParams).then(response => {
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
    handleEdit(row) {
      this.$emit('edit', row)
    },
    handleResult(row) {
      this.$refs.resultDialog.open(row)
    }
  }
}
</script>
