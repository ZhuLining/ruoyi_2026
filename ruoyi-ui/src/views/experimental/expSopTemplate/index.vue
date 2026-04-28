<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 左侧导航栏 -->
      <el-col :span="6">
        <el-card class="tree-card">
          <!-- 标题 + 按钮 -->
          <div class="tree-header">
            <span class="tree-title">目录</span>
            <div class="tree-actions">
              <el-button type="text" icon="el-icon-plus" title="新增" @click="handleAddCatalog" />
              <el-button type="text" icon="el-icon-edit" title="编辑" @click="handleEditCatalog" />
              <el-button type="text" icon="el-icon-refresh" title="刷新" @click="getCatalogTree" />
            </div>
          </div>
          <!-- 搜索框 -->
          <div class="tree-search">
            <el-input
              v-model="catalogKeyword"
              placeholder="输入关键字搜索"
              prefix-icon="el-icon-search"
              size="small"
              clearable
            />
          </div>
          <!-- 树形菜单 -->
          <el-tree
            ref="catalogTreeRef"
            :data="filteredCatalogTree"
            :props="defaultProps"
            :highlight-current="true"
            :expand-on-click-node="false"
            node-key="id"
            :current-node-key="currentCatalogId"
            @node-click="handleNodeClick"
            default-expand-all
            class="catalog-tree"
          />
        </el-card>
      </el-col>

      <!-- 右侧主操作区 -->
      <el-col :span="18">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
              v-hasPermi="['exp:expSopTemplate:add']">新增</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="temList">
          <el-table-column label="模板编号" align="center" prop="temCode" />
          <el-table-column label="模板名称" align="center" prop="temName" />
          <el-table-column label="创建人" align="center" prop="createBy" />
          <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)"
                v-hasPermi="['exp:expSopTemplate:edit']">编辑</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['exp:expSopTemplate:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize" @pagination="getList" />
      </el-col>
    </el-row>

    <!-- SOP模板新增/编辑弹窗 -->
    <sop-tem-edit ref="sopTemEdit" @refresh="getList" />

    <!-- 目录新增/编辑弹窗 -->
    <el-dialog :title="catalogTitle" :visible.sync="catalogVisible" width="500px" append-to-body>
      <el-form ref="catalogFormRef" :model="catalogForm" :rules="catalogRules" label-width="100px">
        <el-form-item label="上级目录">
          <treeselect v-model="catalogForm.parentId" :options="catalogTreeOptions" :normalizer="normalizer"
            placeholder="请选择上级目录" :disabled="catalogForm.temCatalogId !== undefined" />
        </el-form-item>
        <el-form-item label="目录名称" prop="temCatalogName">
          <el-input v-model="catalogForm.temCatalogName" placeholder="请输入目录名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCatalogForm">确 定</el-button>
        <el-button @click="cancelCatalog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSopTem, delSopTem, catalogTree, addCatalog, updateCatalog, getCatalog, delCatalog, checkExistTem } from '@/api/experimental/expSopTem'
import SopTemEdit from './components/sopTemEdit'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'ExpSopTemplate',
  components: { SopTemEdit, Treeselect },
  data() {
    return {
      loading: false,
      total: 0,
      temList: [],
      catalogTree: [],
      catalogKeyword: '',
      currentCatalogId: undefined,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        temCatalogId: undefined
      },
      // 目录弹窗
      catalogVisible: false,
      catalogTitle: '',
      catalogForm: {
        temCatalogId: undefined,
        parentId: 0,
        temCatalogName: ''
      },
      catalogRules: {
        temCatalogName: [{ required: true, message: '目录名称不能为空', trigger: 'blur' }]
      }
    }
  },
  computed: {
    catalogTreeOptions() {
      const root = { id: 0, label: '根目录', children: this.catalogTree }
      return [root]
    },
    filteredCatalogTree() {
      if (!this.catalogKeyword) {
        return this.catalogTree
      }
      const keyword = this.catalogKeyword.toLowerCase()
      const filterNode = (nodes) => {
        return nodes.filter(node => {
          const matchSelf = node.label && node.label.toLowerCase().includes(keyword)
          const matchChildren = node.children && node.children.length > 0 ? filterNode(node.children).length > 0 : false
          if (matchChildren) {
            node.children = filterNode(node.children)
          }
          return matchSelf || matchChildren
        })
      }
      return filterNode(JSON.parse(JSON.stringify(this.catalogTree)))
    }
  },
  created() {
    this.getCatalogTree()
    this.getList()
  },
  methods: {
    normalizer(node) {
      return {
        id: node.id,
        label: node.label,
        children: node.children
      }
    },
    getList() {
      this.loading = true
      listSopTem(this.queryParams).then(response => {
        this.temList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getCatalogTree() {
      catalogTree().then(response => {
        this.catalogTree = response.data || []
        // 默认选中第一个节点
        if (this.catalogTree.length > 0 && !this.currentCatalogId) {
          this.$nextTick(() => {
            const firstNode = this.findFirstNode(this.catalogTree)
            if (firstNode) {
              this.currentCatalogId = firstNode.id
              this.$refs.catalogTreeRef && this.$refs.catalogTreeRef.setCurrentKey(firstNode.id)
              this.queryParams.temCatalogId = firstNode.id
              this.getList()
            }
          })
        }
      })
    },
    findFirstNode(nodes) {
      if (!nodes || nodes.length === 0) return null
      return nodes[0]
    },
    handleNodeClick(data) {
      this.currentCatalogId = data.id
      this.queryParams.temCatalogId = data.id
      this.getList()
    },
    handleAddCatalog() {
      this.catalogTitle = '新增目录'
      this.catalogForm = {
        temCatalogId: undefined,
        parentId: this.currentCatalogId || 0,
        temCatalogName: ''
      }
      this.catalogVisible = true
      this.$nextTick(() => {
        this.$refs.catalogFormRef && this.$refs.catalogFormRef.clearValidate()
      })
    },
    handleEditCatalog() {
      if (!this.currentCatalogId) {
        this.$message.warning('请先选择要编辑的目录')
        return
      }
      if (this.currentCatalogId === 0) {
        this.$message.warning('根目录不允许编辑')
        return
      }
      this.catalogTitle = '编辑目录'
      getCatalog(this.currentCatalogId).then(response => {
        this.catalogForm = response.data || {}
        this.catalogVisible = true
        this.$nextTick(() => {
          this.$refs.catalogFormRef && this.$refs.catalogFormRef.clearValidate()
        })
      })
    },
    submitCatalogForm() {
      this.$refs.catalogFormRef.validate(valid => {
        if (!valid) return
        const api = this.catalogForm.temCatalogId ? updateCatalog : addCatalog
        api(this.catalogForm).then(() => {
          this.$message.success(this.catalogForm.temCatalogId ? '修改成功' : '新增成功')
          this.catalogVisible = false
          this.getCatalogTree()
        })
      })
    },
    cancelCatalog() {
      this.catalogVisible = false
    },
    handleAdd() {
      this.$refs.sopTemEdit.open(null, this.currentCatalogId)
    },
    handleEdit(row) {
      this.$refs.sopTemEdit.open(row)
    },
    handleDelete(row) {
      this.$confirm('是否确认删除该SOP模板？', '提示', { type: 'warning' }).then(() => {
        delSopTem(row.temId).then(() => {
          this.$message.success('删除成功')
          this.getList()
        })
      })
    }
  }
}
</script>

<style scoped>
.tree-card {
  height: calc(100vh - 130px);
  display: flex;
  flex-direction: column;
}
.tree-card ::v-deep .el-card__body {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 10px;
  overflow: hidden;
}
.tree-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 0 10px;
  border-bottom: 1px solid #ebeef5;
  margin-bottom: 10px;
}
.tree-title {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
}
.tree-actions {
  display: flex;
  gap: 8px;
}
.tree-actions .el-button {
  padding: 0;
  font-size: 16px;
}
.tree-search {
  margin-bottom: 10px;
}
.catalog-tree {
  flex: 1;
  overflow-y: auto;
}
.catalog-tree ::v-deep .el-tree-node.is-current > .el-tree-node__content {
  background-color: #409eff;
  color: #fff;
}
.catalog-tree ::v-deep .el-tree-node.is-current > .el-tree-node__content .el-tree-node__expand-icon {
  color: #fff;
}
</style>
