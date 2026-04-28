<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 左侧目录树 -->
      <el-col :span="5" :xs="24">
        <div class="head-container">
          <div class="tree-title">
            <span>目录</span>
            <span class="tree-tools">
              <el-button
                type="text"
                icon="el-icon-plus"
                @click="handleCatalogAdd"
                v-hasPermi="['system:eleCatalog:add']"
              />
              <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleCatalogEdit"
                v-hasPermi="['system:eleCatalog:edit']"
              />
              <el-button
                type="text"
                icon="el-icon-refresh"
                @click="getCatalogTree"
              />
            </span>
          </div>
          <el-input
            v-model="catalogName"
            placeholder="输入关键字搜索"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 10px"
          />
        </div>
        <div class="tree-container">
          <el-tree
            :data="catalogTreeOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            node-key="catalogId"
            highlight-current
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>

      <!-- 右侧数据表格 -->
      <el-col :span="19" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
          <el-form-item label="要素名称" prop="eleName">
            <el-input
              v-model="queryParams.eleName"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:eleTable:add']"
            >新增</el-button>
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:eleTable:remove']"
            >删除</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="eleTableList" height="calc(100vh - 300px)" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="要素名称" align="center" prop="eleName" :show-overflow-tooltip="true" />
          <el-table-column label="要素类型" align="center" prop="eleType" width="120" />
          <el-table-column label="排序" align="center" prop="orderNum" width="80" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:eleTable:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:eleTable:remove']"
              >删除</el-button>
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

    <!-- 添加或修改元素目录对话框 -->
    <el-dialog :title="catalogTitle" :visible.sync="catalogOpen" width="500px" append-to-body>
      <el-form ref="catalogForm" :model="catalogForm" :rules="catalogRules" label-width="80px">
        <el-form-item label="上级目录" prop="parentId">
          <treeselect v-model="catalogForm.parentId" :options="catalogTreeOptionsForSelect" :normalizer="normalizer" placeholder="选择上级目录" />
        </el-form-item>
        <el-form-item label="目录名称" prop="catalogName">
          <el-input v-model="catalogForm.catalogName" placeholder="请输入目录名称" />
        </el-form-item>
        <el-form-item label="目录编码" prop="catalogCode">
          <el-input v-model="catalogForm.catalogCode" placeholder="请输入目录编码" />
        </el-form-item>
        <el-form-item label="显示排序" prop="orderNum">
          <el-input-number v-model="catalogForm.orderNum" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-radio-group v-model="catalogForm.state">
            <el-radio label="10A">有效</el-radio>
            <el-radio label="10P">作废</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCatalogForm">确 定</el-button>
        <el-button @click="cancelCatalog">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改元素对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="要素名称" prop="eleName">
          <el-input v-model="form.eleName" placeholder="请输入要素名称" />
        </el-form-item>
        <el-form-item label="要素编码" prop="eleCode">
          <el-input v-model="form.eleCode" placeholder="请输入要素编码" />
        </el-form-item>
        <el-form-item label="所属目录" prop="catalogId">
          <treeselect v-model="form.catalogId" :options="catalogTreeOptionsForSelect" :normalizer="normalizer" placeholder="选择所属目录" />
        </el-form-item>
        <el-form-item label="要素类型" prop="eleType">
          <el-select v-model="form.eleType" placeholder="请选择要素类型" clearable>
            <el-option label="文本型" value="文本型" />
            <el-option label="数字型" value="数字型" />
            <el-option label="日期型" value="日期型" />
          </el-select>
        </el-form-item>
        <el-form-item label="默认值" prop="eleDefaultValue">
          <el-input v-model="form.eleDefaultValue" placeholder="请输入默认值" />
        </el-form-item>
        <el-form-item label="显示排序" prop="orderNum">
          <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-radio-group v-model="form.state">
            <el-radio label="10A">有效</el-radio>
            <el-radio label="10P">作废</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { treeEleCatalog, getEleCatalog, addEleCatalog, updateEleCatalog, delEleCatalog } from "@/api/system/eleCatalog"
import { listEleTable, getEleTable, addEleTable, updateEleTable, delEleTable } from "@/api/system/eleTable"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"

export default {
  name: "Ele",
  components: { Treeselect },
  watch: {
    // 根据名称筛选目录树
    catalogName(val) {
      this.$refs.tree.filter(val)
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 元素表格数据
      eleTableList: [],
      // 目录树选项（左侧树用）
      catalogTreeOptions: [],
      // 目录树选项（带虚拟根节点，供Treeselect用）
      catalogTreeOptionsForSelect: [],
      // 目录名称
      catalogName: undefined,
      // 弹出层标题
      title: "",
      // 目录弹出层标题
      catalogTitle: "",
      // 是否显示弹出层
      open: false,
      // 是否显示目录弹出层
      catalogOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        eleName: undefined,
        catalogId: undefined
      },
      // 表单参数
      form: {},
      // 目录表单参数
      catalogForm: {},
      // 表单校验
      rules: {
        eleName: [
          { required: true, message: "要素名称不能为空", trigger: "blur" }
        ],
        catalogId: [
          { required: true, message: "所属目录不能为空", trigger: "blur" }
        ]
      },
      // 目录表单校验
      catalogRules: {
        catalogName: [
          { required: true, message: "目录名称不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "显示排序不能为空", trigger: "blur" }
        ]
      },
      defaultProps: {
        children: "children",
        label: "catalogName"
      }
    }
  },
  created() {
    this.getCatalogTree()
    this.getList()
  },
  methods: {
    /** 查询元素列表 */
    getList() {
      this.loading = true
      listEleTable(this.queryParams).then(response => {
        this.eleTableList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询目录树结构 */
    getCatalogTree() {
      treeEleCatalog().then(response => {
        this.catalogTreeOptions = response.data
        // 给Treeselect添加虚拟根节点，使parentId=0能正确显示
        this.catalogTreeOptionsForSelect = [
          { catalogId: 0, catalogName: '根目录', children: response.data }
        ]
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true
      return data.catalogName.indexOf(value) !== -1
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.catalogId = data.catalogId
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.catalogId = undefined
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.eleId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加元素"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const eleId = row.eleId || this.ids
      getEleTable(eleId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改元素"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.eleId != undefined) {
            updateEleTable(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addEleTable(this.form).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const eleIds = row.eleId || this.ids
      this.$modal.confirm('是否确认删除选中的数据项？').then(function() {
        return delEleTable(eleIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    // 表单重置
    reset() {
      this.form = {
        eleId: undefined,
        eleName: undefined,
        eleCode: undefined,
        catalogId: undefined,
        eleType: "文本型",
        eleDefaultValue: undefined,
        orderNum: 0,
        state: "10A",
        remark: undefined
      }
      this.resetForm("form")
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    /** 新增目录按钮操作 */
    handleCatalogAdd() {
      this.resetCatalog()
      const node = this.$refs.tree.getCurrentNode()
      if (node) {
        // 有选中节点，默认上级目录为选中节点的父节点
        this.catalogForm.parentId = node.parentId
      } else {
        // 没有选中节点，默认根目录
        this.catalogForm.parentId = 0
      }
      this.catalogOpen = true
      this.catalogTitle = "添加目录"
    },
    /** 修改目录按钮操作 */
    handleCatalogEdit() {
      const node = this.$refs.tree.getCurrentNode()
      if (!node) {
        this.$modal.msgWarning("请先选择要编辑的目录")
        return
      }
      this.resetCatalog()
      getEleCatalog(node.catalogId).then(response => {
        this.catalogForm = response.data
        this.catalogOpen = true
        this.catalogTitle = "修改目录"
      })
    },
    /** 提交目录按钮 */
    submitCatalogForm() {
      this.$refs["catalogForm"].validate(valid => {
        if (valid) {
          if (this.catalogForm.catalogId != undefined) {
            updateEleCatalog(this.catalogForm).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.catalogOpen = false
              this.getCatalogTree()
            })
          } else {
            addEleCatalog(this.catalogForm).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.catalogOpen = false
              this.getCatalogTree()
            })
          }
        }
      })
    },
    // 目录表单重置
    resetCatalog() {
      this.catalogForm = {
        catalogId: undefined,
        parentId: 0,
        catalogName: undefined,
        catalogCode: undefined,
        orderNum: 0,
        state: "10A"
      }
      this.resetForm("catalogForm")
    },
    // 目录取消按钮
    cancelCatalog() {
      this.catalogOpen = false
      this.resetCatalog()
    },
    /** 转换目录数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.catalogId,
        label: node.catalogName,
        children: node.children
      }
    }
  }
}
</script>

<style scoped>
.tree-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  font-size: 16px;
  font-weight: bold;
}
.tree-tools {
  display: flex;
  gap: 5px;
}
.tree-tools .el-button {
  padding: 0;
  font-size: 16px;
}
.tree-container {
  max-height: calc(100vh - 240px);
  overflow-y: auto;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 8px;
}
</style>
