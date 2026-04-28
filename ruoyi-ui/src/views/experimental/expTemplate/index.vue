<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['exp:expTemplate:add']">新增</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="temList">
      <el-table-column label="模板编号" align="center" prop="temCode" />
      <el-table-column label="模板名称" align="center" prop="temName" />
      <el-table-column label="创建人" align="center" prop="createStaffName" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)"
            v-hasPermi="['exp:expTemplate:edit']">编辑</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['exp:expTemplate:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <tem-edit ref="temEdit" @refresh="getList" />
  </div>
</template>

<script>
import { listTem, delTem } from '@/api/experimental/expTem'
import TemEdit from './components/temEdit'

export default {
  name: 'ExpTemplate',
  components: { TemEdit },
  data() {
    return {
      loading: false,
      total: 0,
      temList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listTem(this.queryParams).then(response => {
        this.temList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    handleAdd() {
      this.$refs.temEdit.open()
    },
    handleEdit(row) {
      this.$refs.temEdit.open(row)
    },
    handleDelete(row) {
      this.$confirm('是否确认删除该模板？', '提示', { type: 'warning' }).then(() => {
        delTem(row.temId).then(() => {
          this.$message.success('删除成功')
          this.getList()
        })
      })
    }
  }
}
</script>