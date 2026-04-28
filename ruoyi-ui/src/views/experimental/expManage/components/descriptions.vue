<template>
  <el-dialog title="实验详情" :visible.sync="visible" width="900px" append-to-body :close-on-click-modal="false">
    <!-- 基础信息 -->
    <div class="section-title">基础信息</div>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="实验编号">{{ detail.expCode }}</el-descriptions-item>
      <el-descriptions-item label="实验名称">{{ detail.expName }}</el-descriptions-item>
      <el-descriptions-item label="计划开始">{{ detail.planStartTime }}</el-descriptions-item>
      <el-descriptions-item label="计划结束">{{ detail.planEndTime }}</el-descriptions-item>
      <el-descriptions-item label="创建人">{{ detail.createStaffName }}</el-descriptions-item>
      <el-descriptions-item label="完成时间">{{ detail.finishDate }}</el-descriptions-item>
      <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
    </el-descriptions>

    <!-- 步骤详情 -->
    <div class="section-title">步骤详情</div>
    <div v-for="(step, index) in stepList" :key="index" class="step-detail-card">
      <div class="step-detail-header">
        <span class="step-index">步骤{{ index + 1 }}</span>
        <span class="step-name">{{ step.stepName }}</span>
      </div>

      <!-- 要素信息 -->
      <div v-if="step.eleArr && step.eleArr.length > 0" class="step-ele-section">
        <div class="content-label">要素信息</div>
        <div v-for="(group, gIdx) in step.eleArr" :key="gIdx" class="ele-group-readonly">
          <div class="ele-group-title">{{ group[0] && group[0].catalogName || '要素分组' }}</div>
          <el-table :data="group" size="mini" :show-header="true" class="ele-table">
            <el-table-column label="要素名称" prop="eleName" min-width="120" />
            <el-table-column label="要素备注" prop="remark" min-width="120" />
            <el-table-column label="输入值" prop="eleValue" min-width="120" />
          </el-table>
        </div>
      </div>

      <!-- 设备信息 -->
      <div v-if="step.assetsList && step.assetsList.length > 0" class="step-assets-section">
        <div class="content-label">设备信息</div>
        <el-table :data="step.assetsList" size="mini" :show-header="true" class="assets-table">
          <el-table-column label="资产名称" prop="assetsName" min-width="120" />
          <el-table-column label="资产类型" prop="assetsType" width="100" />
          <el-table-column label="数量" prop="expAssetsNumber" width="80" />
        </el-table>
      </div>

      <!-- 步骤结果 -->
      <div v-if="showResult" class="step-result-box">
        <div class="content-label">步骤结果</div>
        <div class="result-text">{{ step.stepResult || '暂无' }}</div>
      </div>
    </div>

    <!-- 实验最终结果 -->
    <template v-if="showResult">
      <div class="section-title">实验最终结果</div>
      <div class="result-box">
        {{ detail.result || '暂无' }}
      </div>
    </template>

    <!-- 附件信息 -->
    <template v-if="showResult">
      <div class="section-title">附件信息</div>
      <div v-if="fileList.length > 0" class="file-section">
        <div v-for="file in fileList" :key="file.fileId" class="file-item">
          <i class="el-icon-document" />
          <span class="file-name">{{ file.fileName }}</span>
          <el-button type="text" size="mini" @click="handlePreview(file)">预览</el-button>
          <el-button type="text" size="mini" @click="handleDownload(file)">下载</el-button>
        </div>
      </div>
      <div v-else class="empty-text">暂无附件</div>
    </template>

    <!-- 图片预览弹窗 -->
    <el-dialog :visible.sync="previewVisible" append-to-body :modal-append-to-body="false">
      <img :src="previewUrl" style="width: 100%; display: block;" />
    </el-dialog>

    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getExpDetail } from '@/api/experimental/experimental'
import request from '@/utils/request'

export default {
  name: 'DescriptionsDialog',
  props: {
    showResult: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      visible: false,
      previewVisible: false,
      previewUrl: '',
      detail: {},
      stepList: [],
      fileList: []
    }
  },
  methods: {
    open(row) {
      this.detail = {}
      this.stepList = []
      this.fileList = []
      getExpDetail(row.expId).then(response => {
        const data = response.data || {}
        const stepList = response.stepList || []
        const fileData = response.fileData || []

        this.detail = data
        this.fileList = fileData

        this.stepList = stepList.map(step => {
          const eleArr = (step.eleArr || []).map(group => {
            return (group || []).map(item => ({
              eleName: item.eleName || '',
              remark: item.remark || '',
              eleValue: item.eleValue || '',
              catalogName: item.catalogName || ''
            }))
          })
          const assetsList = (step.assetsList || []).map(asset => ({
            assetsName: asset.assetsName || '',
            assetsType: asset.assetsType || '',
            expAssetsNumber: asset.expAssetsNumber || 1
          }))
          return {
            stepId: step.stepId,
            stepName: step.stepName || '',
            stepCode: step.stepCode || '',
            stepResult: step.stepResult || '',
            eleArr: eleArr,
            assetsList: assetsList
          }
        })

        this.visible = true
      })
    },
    isImage(fileName) {
      if (!fileName) return false
      const ext = fileName.substring(fileName.lastIndexOf('.')).toLowerCase()
      return ['.png', '.jpg', '.jpeg'].includes(ext)
    },
    handlePreview(file) {
      const url = '/common/download/resource?resource=' + encodeURIComponent(file.filePath)
      if (this.isImage(file.fileName)) {
        request({
          url: url,
          method: 'get',
          responseType: 'blob'
        }).then(blob => {
          this.previewUrl = URL.createObjectURL(blob)
          this.previewVisible = true
        }).catch(() => {
          this.$message.error('预览失败')
        })
      } else {
        window.open(process.env.VUE_APP_BASE_API + url, '_blank')
      }
    },
    handleDownload(file) {
      window.open(process.env.VUE_APP_BASE_API + '/common/download/resource?resource=' + encodeURIComponent(file.filePath))
    }
  }
}
</script>

<style scoped>
.section-title {
  font-size: 14px;
  font-weight: bold;
  color: #303133;
  margin: 16px 0 12px;
  padding-left: 10px;
  border-left: 3px solid #409eff;
}
.step-detail-card {
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 12px;
  margin-bottom: 12px;
  background: #fafafa;
}
.step-detail-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 10px;
}
.step-index {
  font-weight: bold;
  color: #409eff;
}
.step-name {
  font-weight: bold;
  color: #303133;
}
.step-ele-section {
  margin-bottom: 12px;
  padding: 10px;
  background: #f0f5ff;
  border: 1px solid #d9e5ff;
  border-radius: 4px;
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
.ele-group-readonly {
  margin-bottom: 10px;
}
.ele-group-title {
  font-size: 13px;
  color: #303133;
  font-weight: bold;
  margin-bottom: 6px;
  padding: 5px 10px;
  background: #e6f0ff;
  border-radius: 3px;
}
.ele-table {
  background: #fff;
}
.step-assets-section {
  margin-bottom: 12px;
  padding: 10px;
  background: #f0f9ff;
  border: 1px solid #d9ecff;
  border-radius: 4px;
}
.assets-table {
  background: #fff;
}
.step-result-box {
  padding: 10px;
  background: #fff;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}
.result-text {
  color: #606266;
  line-height: 1.6;
  white-space: pre-wrap;
}
.result-box {
  padding: 12px;
  background: #f5f7fa;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  color: #606266;
  line-height: 1.6;
  white-space: pre-wrap;
}
.file-section {
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.file-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 12px;
  background: #f5f7fa;
  border-radius: 4px;
}
.file-name {
  flex: 1;
  color: #606266;
}
.empty-text {
  color: #909399;
  text-align: center;
  padding: 20px 0;
}
</style>
