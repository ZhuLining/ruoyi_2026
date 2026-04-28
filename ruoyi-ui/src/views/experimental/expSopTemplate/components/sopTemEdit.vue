<template>
  <el-dialog :title="title" :visible.sync="visible" width="900px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <!-- 模板基础信息 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="模板名称" prop="temName">
            <el-input v-model="form.temName" placeholder="请输入模板名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="模板编号" prop="temCode">
            <el-input v-model="form.temCode" placeholder="请输入模板编号" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="所属目录" prop="temCatalogId">
        <treeselect v-model="form.temCatalogId" :options="catalogOptions" :normalizer="catalogNormalizer"
          placeholder="请选择所属目录" style="width: 100%;" />
      </el-form-item>

      <!-- 步骤区域 -->
      <div class="step-section">
        <div class="step-section-header">
          <span class="step-section-title">模板步骤</span>
          <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleAddStep">新增步骤</el-button>
        </div>

        <div v-for="(step, stepIndex) in form.stepList" :key="stepIndex" class="step-card">
          <!-- 步骤标题栏 -->
          <div class="step-card-header">
            <div class="step-name-wrap">
              <span class="step-index">步骤{{ stepIndex + 1 }}</span>
              <el-input v-model="step.stepName" size="small" placeholder="步骤名称" style="width: 200px;" />
            </div>
            <div class="step-actions">
              <el-button type="text" size="mini" icon="el-icon-circle-plus-outline" @click="handleAddElement(stepIndex)">添加要素</el-button>
              <el-button type="text" size="mini" icon="el-icon-circle-plus-outline" @click="handleAddAssets(stepIndex)">添加设备</el-button>
              <el-button type="text" size="mini" icon="el-icon-top" :disabled="stepIndex === 0" @click="handleMoveStep(stepIndex, -1)">上移</el-button>
              <el-button type="text" size="mini" icon="el-icon-bottom" :disabled="stepIndex === form.stepList.length - 1" @click="handleMoveStep(stepIndex, 1)">下移</el-button>
              <el-button type="text" size="mini" icon="el-icon-delete" style="color: #f56c6c;" @click="handleRemoveStep(stepIndex)">删除步骤</el-button>
            </div>
          </div>

          <!-- 要素列表 -->
          <div v-if="step.eleList && step.eleList.length > 0" class="step-content">
            <div class="content-label">要素信息</div>
            <div v-for="(eleGroup, gIdx) in step.eleList" :key="'eg-'+gIdx" class="ele-group">
              <div class="ele-group-title">
                <span>{{ eleGroup.catalogName || '要素分组' }}</span>
                <div class="ele-group-actions">
                  <el-button type="text" size="mini" icon="el-icon-top" :disabled="gIdx === 0" @click="handleMoveEleGroup(stepIndex, gIdx, -1)">上移</el-button>
                  <el-button type="text" size="mini" icon="el-icon-bottom" :disabled="gIdx === step.eleList.length - 1" @click="handleMoveEleGroup(stepIndex, gIdx, 1)">下移</el-button>
                  <el-button type="text" size="mini" icon="el-icon-delete" style="color: #f56c6c;" @click="handleRemoveEleGroup(stepIndex, gIdx)">删除分组</el-button>
                </div>
              </div>
              <el-table :data="eleGroup.eleContent" size="mini" :show-header="true" class="ele-table">
                <el-table-column label="要素名称" prop="eleName" min-width="120" />
                <el-table-column label="要素备注" prop="remark" min-width="120" />
                <el-table-column label="输入值" min-width="150">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.eleValue" size="mini" placeholder="请输入值" />
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="60" align="center">
                  <template slot-scope="scope">
                    <el-button type="text" size="mini" style="color: #f56c6c;" @click="handleRemoveElement(stepIndex, gIdx, scope.$index)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>

          <!-- 设备列表 -->
          <div v-if="step.assetsList && step.assetsList.length > 0" class="step-content">
            <div class="content-label">设备信息</div>
            <el-table :data="step.assetsList" size="mini" :show-header="true" class="assets-table">
              <el-table-column label="资产名称" prop="assetsName" min-width="120" />
              <el-table-column label="资产类型" prop="assetsType" width="100" />
              <el-table-column label="数量" width="150">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.expAssetsNumber" size="mini" :min="1" :precision="0" style="width: 100px;" />
                </template>
              </el-table-column>
              <el-table-column label="操作" width="60" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="mini" style="color: #f56c6c;" @click="handleRemoveAsset(stepIndex, scope.$index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>

    <!-- 要素选择弹窗 -->
    <el-dialog title="选择要素" :visible.sync="elementDialogVisible" width="750px" append-to-body :close-on-click-modal="false">
      <el-row :gutter="15">
        <el-col :span="7">
          <div class="dialog-tree-title">要素目录</div>
          <el-tree :data="eleCatalogTree" :props="{ label: 'catalogName', children: 'children' }"
            :highlight-current="true" @node-click="handleEleCatalogClick" default-expand-all class="dialog-tree" />
        </el-col>
        <el-col :span="17">
          <div class="dialog-table-title">要素列表</div>
          <el-table ref="elementTable" :data="elementList" size="small" @selection-change="handleElementSelectionChange" height="350">
            <el-table-column type="selection" width="45" />
            <el-table-column label="要素名称" prop="eleName" min-width="120" />
            <el-table-column label="要素编号" prop="eleCode" width="100" />
            <el-table-column label="要素类型" prop="eleType" width="100" />
          </el-table>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmElementSelect">确 定</el-button>
        <el-button @click="elementDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设备选择弹窗 -->
    <el-dialog title="选择设备" :visible.sync="assetsDialogVisible" width="750px" append-to-body :close-on-click-modal="false">
      <el-row :gutter="15">
        <el-col :span="7">
          <div class="dialog-tree-title">资产目录</div>
          <el-tree :data="assetsCatalogTree" :props="{ label: 'label', children: 'children' }"
            :highlight-current="true" @node-click="handleAssetsCatalogClick" default-expand-all class="dialog-tree" />
        </el-col>
        <el-col :span="17">
          <div class="dialog-table-title">资产列表</div>
          <el-table ref="assetsTable" :data="assetsList" size="small" @selection-change="handleAssetsSelectionChange" height="350">
            <el-table-column type="selection" width="45" />
            <el-table-column label="资产名称" prop="assetsName" min-width="120" />
            <el-table-column label="资产类型" prop="assetsType" width="100" />
            <el-table-column label="库存数量" prop="assetsNumber" width="100" />
          </el-table>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmAssetsSelect">确 定</el-button>
        <el-button @click="assetsDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
import { addSopTem, updateSopTem, getSopTem, catalogTree } from '@/api/experimental/expSopTem'
import { treeEleCatalog } from '@/api/system/eleCatalog'
import { listEleTable } from '@/api/system/eleTable'
import { assetsCatalogTree, allAssets } from '@/api/experimental/expAssets'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: 'SopTemEdit',
  components: { Treeselect },
  data() {
    return {
      visible: false,
      title: '',
      catalogOptions: [],
      // 当前操作的步骤索引
      currentStepIndex: null,
      // 要素选择
      elementDialogVisible: false,
      eleCatalogTree: [],
      elementList: [],
      selectedElements: [],
      currentEleCatalogId: null,
      currentEleCatalogName: '',
      // 设备选择
      assetsDialogVisible: false,
      assetsCatalogTree: [],
      assetsList: [],
      selectedAssets: [],
      currentAssetsCatalogId: null,
      form: {
        temId: undefined,
        temName: '',
        temCode: '',
        temCatalogId: undefined,
        stepList: []
      },
      rules: {
        temName: [{ required: true, message: '模板名称不能为空', trigger: 'blur' }],
        temCode: [{ required: true, message: '模板编号不能为空', trigger: 'blur' }],
        temCatalogId: [{ required: true, message: '请选择所属目录', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getCatalogOptions()
    this.getEleCatalogTree()
    this.getAssetsCatalogTree()
  },
  methods: {
    catalogNormalizer(node) {
      return {
        id: node.id,
        label: node.label,
        children: node.children
      }
    },
    getCatalogOptions() {
      catalogTree().then(response => {
        this.catalogOptions = response.data || []
      })
    },
    getEleCatalogTree() {
      treeEleCatalog().then(response => {
        this.eleCatalogTree = response.data || []
      })
    },
    getAssetsCatalogTree() {
      assetsCatalogTree().then(response => {
        this.assetsCatalogTree = response.data || []
      })
    },
    open(row, defaultCatalogId) {
      this.reset()
      this.getCatalogOptions()
      if (row && row.temId) {
        this.title = '编辑SOP模板'
        getSopTem(row.temId).then(response => {
          const data = response.data
          const stepList = response.stepList || []
          this.form.temId = data.temId
          this.form.temName = data.temName
          this.form.temCode = data.temCode
          this.form.temCatalogId = data.temCatalogId
          // 转换后端步骤数据
          this.form.stepList = this.transformStepList(stepList)
          this.visible = true
        })
      } else {
        this.title = '新增SOP模板'
        if (defaultCatalogId) {
          this.form.temCatalogId = defaultCatalogId
        }
        this.visible = true
      }
    },
    transformStepList(stepList) {
      return stepList.map(step => {
        const result = {
          stepId: step.stepId,
          stepName: step.stepName || '',
          stepCode: step.stepCode || '',
          eleList: [],
          assetsList: []
        }
        // 转换要素数据 eleArr -> eleList
        if (step.eleArr && step.eleArr.length > 0) {
          step.eleArr.forEach(group => {
            if (group && group.length > 0) {
              const first = group[0]
              result.eleList.push({
                catalogId: first.catalogId,
                catalogName: first.catalogName || '',
                eleContent: group.map(item => ({
                  eleId: item.eleId,
                  eleName: item.eleName,
                  eleCode: item.eleCode,
                  eleType: item.eleType,
                  remark: item.remark || '',
                  eleValue: item.eleValue || ''
                }))
              })
            }
          })
        }
        // 转换设备数据
        if (step.assetsList && step.assetsList.length > 0) {
          result.assetsList = step.assetsList.map(asset => ({
            assetsId: asset.assetsId,
            assetsName: asset.assetsName,
            assetsType: asset.assetsType,
            expAssetsNumber: asset.expAssetsNumber || 1
          }))
        }
        return result
      })
    },
    reset() {
      this.form = {
        temId: undefined,
        temName: '',
        temCode: '',
        temCatalogId: undefined,
        stepList: []
      }
      this.currentStepIndex = null
      this.selectedElements = []
      this.selectedAssets = []
      this.$nextTick(() => {
        this.$refs.form && this.$refs.form.clearValidate()
      })
    },
    cancel() {
      this.visible = false
      this.reset()
    },
    // 步骤操作
    handleAddStep() {
      this.form.stepList.push({
        stepName: '步骤' + (this.form.stepList.length + 1),
        stepCode: '',
        eleList: [],
        assetsList: []
      })
    },
    handleRemoveStep(index) {
      this.$confirm('是否确认删除该步骤？', '提示', { type: 'warning' }).then(() => {
        this.form.stepList.splice(index, 1)
      })
    },
    handleMoveStep(index, direction) {
      const targetIndex = index + direction
      if (targetIndex < 0 || targetIndex >= this.form.stepList.length) return
      const temp = this.form.stepList[index]
      this.$set(this.form.stepList, index, this.form.stepList[targetIndex])
      this.$set(this.form.stepList, targetIndex, temp)
    },
    // 要素操作
    handleAddElement(stepIndex) {
      this.currentStepIndex = stepIndex
      this.selectedElements = []
      this.currentEleCatalogId = null
      this.currentEleCatalogName = ''
      this.elementList = []
      this.elementDialogVisible = true
      this.$nextTick(() => {
        this.$refs.elementTable && this.$refs.elementTable.clearSelection()
      })
    },
    handleEleCatalogClick(data) {
      this.currentEleCatalogId = data.catalogId
      this.currentEleCatalogName = data.catalogName
      listEleTable({ catalogId: data.catalogId, pageNum: 1, pageSize: 1000 }).then(response => {
        this.elementList = response.rows || []
      })
    },
    handleElementSelectionChange(selection) {
      this.selectedElements = selection
    },
    confirmElementSelect() {
      if (this.selectedElements.length === 0) {
        this.$message.warning('请至少选择一个要素')
        return
      }
      if (!this.currentEleCatalogId) {
        this.$message.warning('请先选择要素目录')
        return
      }
      const step = this.form.stepList[this.currentStepIndex]
      const eleContent = this.selectedElements.map(ele => ({
        eleId: ele.eleId,
        eleName: ele.eleName,
        eleCode: ele.eleCode,
        eleType: ele.eleType,
        remark: ele.remark || '',
        eleValue: ele.eleDefaultValue || ''
      }))
      step.eleList.push({
        catalogId: this.currentEleCatalogId,
        catalogName: this.currentEleCatalogName,
        eleContent: eleContent
      })
      this.elementDialogVisible = false
    },
    handleRemoveElement(stepIndex, groupIndex, eleIndex) {
      const step = this.form.stepList[stepIndex]
      step.eleList[groupIndex].eleContent.splice(eleIndex, 1)
      if (step.eleList[groupIndex].eleContent.length === 0) {
        step.eleList.splice(groupIndex, 1)
      }
    },
    handleRemoveEleGroup(stepIndex, groupIndex) {
      this.$confirm('是否确认删除该要素分组？', '提示', { type: 'warning' }).then(() => {
        this.form.stepList[stepIndex].eleList.splice(groupIndex, 1)
      })
    },
    handleMoveEleGroup(stepIndex, groupIndex, direction) {
      const step = this.form.stepList[stepIndex]
      const targetIndex = groupIndex + direction
      if (targetIndex < 0 || targetIndex >= step.eleList.length) return
      const temp = step.eleList[groupIndex]
      this.$set(step.eleList, groupIndex, step.eleList[targetIndex])
      this.$set(step.eleList, targetIndex, temp)
    },
    // 设备操作
    handleAddAssets(stepIndex) {
      this.currentStepIndex = stepIndex
      this.selectedAssets = []
      this.currentAssetsCatalogId = null
      this.assetsList = []
      this.assetsDialogVisible = true
      this.$nextTick(() => {
        this.$refs.assetsTable && this.$refs.assetsTable.clearSelection()
      })
    },
    handleAssetsCatalogClick(data) {
      this.currentAssetsCatalogId = data.id
      allAssets({ assetsCatalogId: data.id }).then(response => {
        this.assetsList = response || []
      })
    },
    handleAssetsSelectionChange(selection) {
      this.selectedAssets = selection
    },
    confirmAssetsSelect() {
      if (this.selectedAssets.length === 0) {
        this.$message.warning('请至少选择一个设备')
        return
      }
      const step = this.form.stepList[this.currentStepIndex]
      this.selectedAssets.forEach(asset => {
        // 避免重复添加
        const exists = step.assetsList.some(a => a.assetsId === asset.assetsId)
        if (!exists) {
          step.assetsList.push({
            assetsId: asset.assetsId,
            assetsName: asset.assetsName,
            assetsType: asset.assetsType,
            expAssetsNumber: 1
          })
        }
      })
      this.assetsDialogVisible = false
    },
    handleRemoveAsset(stepIndex, assetIndex) {
      this.form.stepList[stepIndex].assetsList.splice(assetIndex, 1)
    },
    // 提交
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) return
        if (this.form.stepList.length === 0) {
          this.$message.warning('请至少添加一个步骤')
          return
        }
        // 构建提交数据
        const submitData = {
          temId: this.form.temId,
          temName: this.form.temName,
          temCode: this.form.temCode,
          temCatalogId: this.form.temCatalogId,
          stepList: this.form.stepList.map(step => ({
            stepName: step.stepName,
            stepCode: step.stepCode,
            eleList: step.eleList.map(eleGroup => ({
              catalogId: eleGroup.catalogId,
              eleContent: eleGroup.eleContent.map(ele => ({
                eleId: ele.eleId,
                eleValue: ele.eleValue
              }))
            })),
            assetsList: step.assetsList.map(asset => ({
              assetsId: asset.assetsId,
              expAssetsNumber: asset.expAssetsNumber
            }))
          }))
        }
        if (this.form.temId) {
          updateSopTem(submitData).then(() => {
            this.$message.success('修改成功')
            this.visible = false
            this.$emit('refresh')
          })
        } else {
          addSopTem(submitData).then(() => {
            this.$message.success('新增成功')
            this.visible = false
            this.$emit('refresh')
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.step-section {
  margin-top: 15px;
  border-top: 1px solid #ebeef5;
  padding-top: 15px;
}
.step-section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}
.step-section-title {
  font-size: 14px;
  font-weight: bold;
  color: #303133;
}
.step-card {
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 12px;
  margin-bottom: 12px;
  background: #fafafa;
}
.step-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.step-name-wrap {
  display: flex;
  align-items: center;
  gap: 8px;
}
.step-index {
  font-weight: bold;
  color: #409eff;
}
.step-actions {
  display: flex;
  gap: 8px;
}
.step-content {
  margin-top: 10px;
  padding: 10px;
  background: #f0f5ff;
  border: 1px solid #d9e5ff;
  border-radius: 4px;
}
.step-content + .step-content {
  margin-top: 12px;
}
.content-label {
  font-size: 13px;
  color: #303133;
  margin-bottom: 8px;
  font-weight: bold;
  padding-left: 8px;
  border-left: 3px solid #409eff;
  line-height: 16px;
}
.ele-group {
  margin-bottom: 10px;
}
.ele-group-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
  color: #303133;
  font-weight: bold;
  margin-bottom: 6px;
  padding: 5px 10px;
  background: #e6f0ff;
  border-radius: 3px;
}
.ele-group-actions {
  display: flex;
  gap: 6px;
}
.ele-group-actions .el-button {
  padding: 0;
}
.ele-table,
.assets-table {
  background: #fff;
}
.dialog-tree-title,
.dialog-table-title {
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 8px;
  color: #303133;
}
.dialog-tree {
  max-height: 380px;
  overflow-y: auto;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 8px;
}
::v-deep .el-dialog__body {
  padding: 15px 20px;
}
</style>
