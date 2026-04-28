<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 左侧目录树 -->
      <el-col :span="6">
        <el-card class="tree-card">
          <div class="tree-header">
            <span class="tree-title">目录</span>
            <div class="tree-actions">
              <el-button type="text" icon="el-icon-plus" title="新增" @click="handleAddCatalog" />
              <el-button type="text" icon="el-icon-edit" title="编辑" @click="handleEditCatalog" />
              <el-button type="text" icon="el-icon-refresh" title="刷新" @click="getCatalogTree" />
            </div>
          </div>
          <div class="tree-search">
            <el-input
              v-model="catalogKeyword"
              placeholder="输入关键字搜索"
              prefix-icon="el-icon-search"
              size="small"
              clearable
            />
          </div>
          <el-tree
            ref="catalogTreeRef"
            :data="filteredCatalogTree"
            :props="{ label: 'label', children: 'children' }"
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

      <!-- 右侧资产列表 -->
      <el-col :span="18">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
          <el-form-item label="资产名称" prop="assetsName">
            <el-input
              v-model="queryParams.assetsName"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="资产类别" prop="assetsType">
            <el-select v-model="queryParams.assetsType" placeholder="请选择" clearable>
              <el-option
                v-for="dict in dict.type.assets_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:expAssets:add']"
            >入库</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:expAssets:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="expAssetsList" height="calc(100vh - 300px)" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="资产名称" align="center" prop="assetsName" :show-overflow-tooltip="true" />
          <el-table-column label="资产类别" align="center" prop="assetsType" width="120">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.assets_type" :value="scope.row.assetsType"/>
            </template>
          </el-table-column>
          <el-table-column label="资产总数" align="center" prop="assetsNumber" width="100" />
          <el-table-column label="出库数量" align="center" prop="useTotalNumber" width="100" />
          <el-table-column label="剩余库存" align="center" width="100">
            <template slot-scope="scope">
              <span>{{ scope.row.assetsNumber - scope.row.useTotalNumber }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="更新时间" align="center" prop="updateTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.updateTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:expAssets:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-s-promotion"
                @click="handleUse(scope.row)"
                v-hasPermi="['system:expAssets:use']"
              >出库</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:expAssets:remove']"
              >删除</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-document"
                @click="handleUseDetail(scope.row)"
                v-hasPermi="['system:expAssets:list']"
              >出库明细</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 资产入库/修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产类别" prop="assetsType">
              <el-select v-model="form.assetsType" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.assets_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产名称" prop="assetsName">
              <el-input v-model="form.assetsName" placeholder="请输入资产名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资产数量" prop="assetsNumber">
              <el-input-number v-model="form.assetsNumber" controls-position="right" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最低库存" prop="assetsThreshold">
              <el-input-number v-model="form.assetsThreshold" controls-position="right" :min="0" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商" prop="supplierType">
              <el-select v-model="form.supplierType" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.supplier_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产规格" prop="assetsModel">
              <el-input v-model="form.assetsModel" placeholder="请输入资产规格" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="保管人" prop="assetsStoreId">
              <el-input v-model="form.assetsStoreName" placeholder="请选择保管人" readonly>
                <el-button slot="append" icon="el-icon-user" @click="handleSelectUser"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="保质期" prop="assetsExpirationDate">
              <el-date-picker
                v-model="form.assetsExpirationDate"
                type="datetime"
                placeholder="选择日期时间"
                style="width: 100%"
                value-format="yyyy-MM-dd HH:mm:ss"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" maxlength="100" show-word-limit placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 出库对话框 -->
    <el-dialog title="资产出库" :visible.sync="useOpen" width="500px" append-to-body>
      <el-form ref="useForm" :model="useForm" :rules="useRules" label-width="100px">
        <el-form-item label="资产名称">
          <el-input v-model="useForm.assetsName" disabled />
        </el-form-item>
        <el-form-item label="剩余库存">
          <el-input v-model="useForm.remainNumber" disabled />
        </el-form-item>
        <el-form-item label="出库数量" prop="useNumber">
          <el-input-number v-model="useForm.useNumber" controls-position="right" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="useForm.remark" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUseForm">确 定</el-button>
        <el-button @click="cancelUse">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 出库明细对话框 -->
    <el-dialog title="出库明细" :visible.sync="detailOpen" width="800px" append-to-body>
      <el-table v-loading="detailLoading" :data="expAssetsUseList" height="400px">
        <el-table-column label="出库数量" align="center" prop="useNumber" width="100" />
        <el-table-column label="出库人" align="center" prop="userName" width="120" />
        <el-table-column label="出库时间" align="center" prop="createTime" width="160">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      </el-table>
      <pagination
        v-show="detailTotal>0"
        :total="detailTotal"
        :page.sync="detailQuery.pageNum"
        :limit.sync="detailQuery.pageSize"
        @pagination="getUseList"
      />
    </el-dialog>

    <!-- 选择用户对话框 -->
    <el-dialog :title="'选择用户'" :visible.sync="userOpen" width="800px" append-to-body>
      <el-table :data="userList" @row-click="handleUserSelect">
        <el-table-column label="用户名称" align="center" prop="userName" />
        <el-table-column label="用户昵称" align="center" prop="nickName" />
      </el-table>
      <pagination
        v-show="userTotal>0"
        :total="userTotal"
        :page.sync="userQuery.pageNum"
        :limit.sync="userQuery.pageSize"
        @pagination="getUserList"
      />
    </el-dialog>

    <!-- 资产目录新增/编辑弹窗 -->
    <el-dialog :title="catalogTitle" :visible.sync="catalogVisible" width="500px" append-to-body>
      <el-form ref="catalogFormRef" :model="catalogForm" :rules="catalogRules" label-width="100px">
        <el-form-item label="上级目录">
          <treeselect v-model="catalogForm.parentId" :options="catalogTreeOptions" :normalizer="catalogNormalizer"
            placeholder="请选择上级目录" :disabled="catalogForm.assetsCatalogId !== undefined" />
        </el-form-item>
        <el-form-item label="目录名称" prop="assetsCatalogName">
          <el-input v-model="catalogForm.assetsCatalogName" placeholder="请输入目录名称" />
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
import { listExpAssets, getExpAssets, addExpAssets, updateExpAssets, delExpAssets, useExpAssets } from "@/api/system/expAssets"
import { listExpAssetsUse } from "@/api/system/expAssetsUse"
import { listUser } from "@/api/system/user"
import { assetsCatalogTree, addAssetsCatalog, updateAssetsCatalog, getAssetsCatalog, delAssetsCatalog } from "@/api/experimental/expAssets"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"

export default {
  name: "ExpAssets",
  dicts: ['assets_type', 'supplier_type'],
  components: { Treeselect },
  data() {
    return {
      loading: true,
      showSearch: true,
      ids: [],
      single: true,
      multiple: true,
      total: 0,
      expAssetsList: [],
      title: "",
      open: false,
      useOpen: false,
      detailOpen: false,
      detailLoading: false,
      expAssetsUseList: [],
      detailTotal: 0,
      userOpen: false,
      userList: [],
      userTotal: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        assetsName: undefined,
        assetsType: undefined,
        assetsCatalogId: undefined
      },
      form: {},
      rules: {
        assetsName: [
          { required: true, message: "资产名称不能为空", trigger: "blur" }
        ],
        assetsType: [
          { required: true, message: "资产类别不能为空", trigger: "change" }
        ],
        assetsNumber: [
          { required: true, message: "资产数量不能为空", trigger: "blur" }
        ],
        assetsThreshold: [
          { required: true, message: "最低库存不能为空", trigger: "blur" }
        ],
        supplierType: [
          { required: true, message: "供应商不能为空", trigger: "change" }
        ]
      },
      useForm: {},
      useRules: {
        useNumber: [
          { required: true, message: "出库数量不能为空", trigger: "blur" }
        ]
      },
      detailQuery: {
        pageNum: 1,
        pageSize: 10,
        assetsId: undefined
      },
      userQuery: {
        pageNum: 1,
        pageSize: 10
      },
      // 目录树相关
      catalogTree: [],
      catalogKeyword: '',
      currentCatalogId: undefined,
      catalogVisible: false,
      catalogTitle: '',
      catalogForm: {
        assetsCatalogId: undefined,
        parentId: 0,
        assetsCatalogName: ''
      },
      catalogRules: {
        assetsCatalogName: [{ required: true, message: '目录名称不能为空', trigger: 'blur' }]
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
    catalogNormalizer(node) {
      return {
        id: node.id,
        label: node.label,
        children: node.children
      }
    },
    getCatalogTree() {
      assetsCatalogTree().then(response => {
        this.catalogTree = response.data || []
        if (this.catalogTree.length > 0 && !this.currentCatalogId) {
          this.$nextTick(() => {
            const firstNode = this.catalogTree[0]
            if (firstNode) {
              this.currentCatalogId = firstNode.id
              this.$refs.catalogTreeRef && this.$refs.catalogTreeRef.setCurrentKey(firstNode.id)
              this.queryParams.assetsCatalogId = firstNode.id
              this.getList()
            }
          })
        }
      })
    },
    handleNodeClick(data) {
      this.currentCatalogId = data.id
      this.queryParams.assetsCatalogId = data.id
      this.queryParams.pageNum = 1
      this.getList()
    },
    handleAddCatalog() {
      this.catalogTitle = '新增目录'
      this.catalogForm = {
        assetsCatalogId: undefined,
        parentId: this.currentCatalogId || 0,
        assetsCatalogName: ''
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
      getAssetsCatalog(this.currentCatalogId).then(response => {
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
        const api = this.catalogForm.assetsCatalogId ? updateAssetsCatalog : addAssetsCatalog
        api(this.catalogForm).then(() => {
          this.$message.success(this.catalogForm.assetsCatalogId ? '修改成功' : '新增成功')
          this.catalogVisible = false
          this.getCatalogTree()
        })
      })
    },
    cancelCatalog() {
      this.catalogVisible = false
    },
    /** 查询资产列表 */
    getList() {
      this.loading = true
      listExpAssets(this.queryParams).then(response => {
        this.expAssetsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.queryParams.assetsCatalogId = this.currentCatalogId
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.assetsId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.form.assetsCatalogId = this.currentCatalogId
      this.open = true
      this.title = "资产入库"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const assetsId = row.assetsId || this.ids
      getExpAssets(assetsId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改资产"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.assetsId != undefined) {
            updateExpAssets(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addExpAssets(this.form).then(() => {
              this.$modal.msgSuccess("入库成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const assetsIds = row.assetsId || this.ids
      this.$modal.confirm('是否确认删除选中的数据项？').then(function() {
        return delExpAssets(assetsIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    // 表单重置
    reset() {
      this.form = {
        assetsId: undefined,
        assetsName: undefined,
        assetsType: undefined,
        assetsNumber: 0,
        useTotalNumber: 0,
        assetsThreshold: 0,
        supplierType: undefined,
        assetsModel: undefined,
        assetsStoreId: undefined,
        assetsStoreName: undefined,
        assetsExpirationDate: undefined,
        assetsCatalogId: this.currentCatalogId,
        remark: undefined
      }
      this.resetForm("form")
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    /** 出库按钮操作 */
    handleUse(row) {
      this.useForm = {
        assetsId: row.assetsId,
        assetsName: row.assetsName,
        remainNumber: row.assetsNumber - row.useTotalNumber,
        useNumber: 1,
        remark: undefined
      }
      this.useOpen = true
    },
    /** 提交出库 */
    submitUseForm() {
      this.$refs["useForm"].validate(valid => {
        if (valid) {
          const data = {
            assetsId: this.useForm.assetsId,
            useNumber: this.useForm.useNumber,
            remark: this.useForm.remark
          }
          useExpAssets(data).then(() => {
            this.$modal.msgSuccess("出库成功")
            this.useOpen = false
            this.getList()
          })
        }
      })
    },
    // 取消出库
    cancelUse() {
      this.useOpen = false
    },
    /** 出库明细 */
    handleUseDetail(row) {
      this.detailQuery.assetsId = row.assetsId
      this.detailQuery.pageNum = 1
      this.detailOpen = true
      this.getUseList()
    },
    /** 查询出库记录 */
    getUseList() {
      this.detailLoading = true
      listExpAssetsUse(this.detailQuery).then(response => {
        this.expAssetsUseList = response.rows
        this.detailTotal = response.total
        this.detailLoading = false
      })
    },
    /** 选择用户 */
    handleSelectUser() {
      this.userOpen = true
      this.getUserList()
    },
    /** 查询用户列表 */
    getUserList() {
      listUser(this.userQuery).then(response => {
        this.userList = response.rows
        this.userTotal = response.total
      })
    },
    /** 选中用户 */
    handleUserSelect(row) {
      this.form.assetsStoreId = row.userId
      this.form.assetsStoreName = row.userName
      this.userOpen = false
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
