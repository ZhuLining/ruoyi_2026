<template>
  <el-dialog title="选择人员" :visible.sync="visible" width="600px" append-to-body>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-tree :data="deptTree" :props="defaultProps" @node-click="handleDeptClick" default-expand-all />
      </el-col>
      <el-col :span="16">
        <el-table :data="userList" @row-click="handleRowClick" highlight-current-row>
          <el-table-column label="用户名" prop="userName" />
          <el-table-column label="昵称" prop="nickName" />
          <el-table-column label="部门" prop="dept.deptName" />
        </el-table>
        <pagination v-show="userTotal > 0" :total="userTotal" :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize" @pagination="getUserList" />
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { deptTreeSelect, listUser } from '@/api/experimental/common'

export default {
  name: 'SelectOrgStaff',
  data() {
    return {
      visible: false,
      deptTree: [],
      userList: [],
      userTotal: 0,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined
      }
    }
  },
  created() {
    this.getDeptTree()
  },
  methods: {
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptTree = response.data || []
      })
    },
    getUserList() {
      listUser(this.queryParams).then(response => {
        this.userList = response.rows
        this.userTotal = response.total
      })
    },
    open() {
      this.visible = true
      this.getUserList()
    },
    handleDeptClick(data) {
      this.queryParams.deptId = data.id
      this.queryParams.pageNum = 1
      this.getUserList()
    },
    handleRowClick(row) {
      this.$emit('select', row)
      this.visible = false
    }
  }
}
</script>