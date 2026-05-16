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
        <el-button type="success" plain icon="el-icon-document-checked" size="mini" :disabled="!selectedList || selectedList.length === 0" @click="handleBatchCheck" v-hasPermi="['exp:expManage:check']">批量审批</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="!selectedList || selectedList.length === 0" @click="handleBatchDelete" v-hasPermi="['exp:expManage:remove']">批量删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="expList" @selection-change="handleSelectionChange" border :max-height="tableMaxHeight">
      <el-table-column type="selection" width="55" align="center" fixed />
      <el-table-column label="实验编号" align="center" prop="expCode" width="160" />
      <el-table-column label="实验名称" align="center" prop="expName" min-width="240" :show-overflow-tooltip="true" />
      <el-table-column label="计划开始" align="center" prop="planStartTime" width="160" />
      <el-table-column label="计划结束" align="center" prop="planEndTime" width="160" />
      <el-table-column label="创建人" align="center" prop="createStaffName" width="100" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="getTimeStatus(scope.row).type === 'info'" type="info" size="mini">未开始</el-tag>
          <el-tag v-else-if="getTimeStatus(scope.row).type === 'success'" type="success" size="mini">正常</el-tag>
          <el-tag v-else-if="getTimeStatus(scope.row).type === 'warning'" type="warning" size="mini">即将超时</el-tag>
          <el-tag v-else type="danger" size="mini">超时</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200" fixed="right">
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

    <!-- 批量审批弹窗 -->
    <el-dialog title="批量审批" :visible.sync="batchCheckVisible" width="500px" append-to-body>
      <el-form ref="batchCheckForm" :model="batchCheckForm" :rules="batchCheckRules" label-width="100px">
        <el-form-item label="选中数量">
          <span>{{ batchCheckForm.count }} 条</span>
        </el-form-item>
        <el-form-item label="审核结果" prop="checkStatus">
          <el-radio-group v-model="batchCheckForm.checkStatus">
            <el-radio label="1">通过</el-radio>
            <el-radio label="2">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见" prop="checkRemark">
          <el-input v-model="batchCheckForm.checkRemark" type="textarea" :rows="3" placeholder="请输入审核意见" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBatchCheck">确 定</el-button>
        <el-button @click="batchCheckVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listExp, delExp, checkExp } from '@/api/experimental/experimental'
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
      selectedList: [],
      planTimeRange: [],
      tableMaxHeight: 500,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        expName: undefined,
        expCode: undefined,
        expStatus: 0,
        params: {}
      },
      // 批量审批
      batchCheckVisible: false,
      batchCheckForm: {
        count: 0,
        checkStatus: '1',
        checkRemark: undefined
      },
      batchCheckRules: {
        checkStatus: [{ required: true, message: '请选择审核结果', trigger: 'change' }]
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
      listExp(this.queryParams).then(response => {
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
    handleCheck(row) {
      this.$refs.checkDialog.open(row)
    },
    handleBatchCheck() {
      if (!this.selectedList || this.selectedList.length === 0) {
        this.$message.warning('请至少选择一条数据')
        return
      }
      this.batchCheckForm = {
        count: this.selectedList.length,
        checkStatus: '1',
        checkRemark: undefined
      }
      this.batchCheckVisible = true
    },
    submitBatchCheck() {
      this.$refs.batchCheckForm.validate(valid => {
        if (!valid) return
        const data = {
          expIdList: this.selectedList.map(item => item.expId),
          expStatus: parseInt(this.batchCheckForm.checkStatus),
          checkRemark: this.batchCheckForm.checkRemark
        }
        checkExp(data).then(() => {
          this.$message.success('批量审批成功')
          this.batchCheckVisible = false
          this.getList()
        })
      })
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
