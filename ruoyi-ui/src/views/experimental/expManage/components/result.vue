<template>
  <el-dialog title="实验结果" :visible.sync="visible" width="850px" append-to-body :close-on-click-modal="false">
    <el-form ref="form" :model="form" label-width="100px">
      <!-- 基础信息 -->
      <div class="section-title">基础信息</div>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="实验名称">
            <span>{{ form.expName }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实验编号">
            <span>{{ form.expCode }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="计划时间">
            <span>{{ form.planStartTime }} ~ {{ form.planEndTime }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注">
            <span>{{ form.remark || '-' }}</span>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 步骤结果 -->
      <div class="section-title">步骤结果</div>
      <div v-for="(step, index) in form.stepList" :key="index" class="step-result-card">
        <div class="step-result-header">
          <span class="step-index">步骤{{ index + 1 }}</span>
          <span class="step-name">{{ step.stepName }}</span>
        </div>

        <!-- 要素信息（只读展示） -->
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

        <!-- 设备信息（只读展示） -->
        <div v-if="step.assetsList && step.assetsList.length > 0" class="step-assets-section">
          <div class="content-label">设备信息</div>
          <el-table :data="step.assetsList" size="mini" :show-header="true" class="assets-table">
            <el-table-column label="资产名称" prop="assetsName" min-width="120" />
            <el-table-column label="资产类型" prop="assetsType" width="100" />
            <el-table-column label="数量" prop="expAssetsNumber" width="80" />
          </el-table>
        </div>

        <el-form-item :label="'步骤结果'" :prop="'stepList.' + index + '.stepResult'">
          <el-input v-model="step.stepResult" type="textarea" :rows="2" placeholder="请输入步骤结果" />
        </el-form-item>
      </div>

      <!-- 实验最终结果 -->
      <div class="section-title">实验最终结果</div>
      <el-form-item label="实验结果" prop="result">
        <el-input v-model="form.result" type="textarea" :rows="4" placeholder="请输入实验最终结果" />
      </el-form-item>

      <!-- 文件上传 -->
      <div class="section-title">附件上传</div>
      <el-form-item label="上传文件">
        <el-upload
          :action="uploadUrl"
          :headers="headers"
          :on-success="handleUploadSuccess"
          :on-remove="handleUploadRemove"
          :before-upload="beforeUpload"
          :file-list="fileList"
          multiple
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">支持格式：doc、docx、xlsx、pdf、png、jpg，单个文件不超过20MB</div>
        </el-upload>
      </el-form-item>

      <!-- 已有文件 -->
      <el-form-item v-if="existingFiles.length > 0" label="已有附件">
        <div class="file-list">
          <div v-for="file in existingFiles" :key="file.fileId" class="file-item">
            <i class="el-icon-document" />
            <span class="file-name">{{ file.fileName }}</span>
            <el-button type="text" size="mini" @click="handleDownload(file)">下载</el-button>
          </div>
        </div>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { resultExp } from '@/api/experimental/experimental'
import { getExpDetail } from '@/api/experimental/experimental'
import { getToken } from '@/utils/auth'

export default {
  name: 'ResultDialog',
  data() {
    return {
      visible: false,
      uploadUrl: process.env.VUE_APP_BASE_API + '/common/upload',
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      form: {
        expId: undefined,
        expName: '',
        expCode: '',
        remark: '',
        planStartTime: '',
        planEndTime: '',
        result: '',
        stepList: [],
        uploadList: []
      },
      fileList: [],
      existingFiles: []
    }
  },
  methods: {
    open(row) {
      this.reset()
      this.form.expId = row.expId
      this.loadDetail(row.expId)
      this.visible = true
    },
    reset() {
      this.form = {
        expId: undefined,
        expName: '',
        expCode: '',
        remark: '',
        planStartTime: '',
        planEndTime: '',
        result: '',
        stepList: [],
        uploadList: []
      }
      this.fileList = []
      this.existingFiles = []
    },
    cancel() {
      this.visible = false
      this.reset()
    },
    loadDetail(expId) {
      getExpDetail(expId).then(response => {
        const data = response.data || {}
        const stepList = response.stepList || []
        const fileData = response.fileData || []

        this.form.expName = data.expName || ''
        this.form.expCode = data.expCode || ''
        this.form.remark = data.remark || ''
        this.form.planStartTime = data.planStartTime || ''
        this.form.planEndTime = data.planEndTime || ''
        this.form.result = data.result || ''

        this.form.stepList = stepList.map(step => {
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

        this.existingFiles = fileData
      })
    },
    beforeUpload(file) {
      const allowedTypes = [
        'application/msword',
        'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
        'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        'application/pdf',
        'image/png',
        'image/jpeg'
      ]
      const allowedExts = ['.doc', '.docx', '.xlsx', '.pdf', '.png', '.jpg', '.jpeg']
      const ext = file.name.substring(file.name.lastIndexOf('.')).toLowerCase()

      if (!allowedTypes.includes(file.type) && !allowedExts.includes(ext)) {
        this.$message.error('不支持该文件格式，请上传 doc/docx/xlsx/pdf/png/jpg 格式文件')
        return false
      }

      const maxSize = 20 * 1024 * 1024
      if (file.size > maxSize) {
        this.$message.error('文件大小不能超过20MB')
        return false
      }
      return true
    },
    handleUploadSuccess(response, file, fileList) {
      if (response.code === 200) {
        this.form.uploadList.push({
          fileName: file.name,
          filePath: response.fileName
        })
        this.fileList = fileList.map(f => {
          if (f.response && f.response.code === 200) {
            return {
              ...f,
              name: f.name,
              url: f.response.url
            }
          }
          return f
        })
        this.$message.success(file.name + ' 上传成功')
      } else {
        this.$message.error(file.name + ' 上传失败：' + response.msg)
      }
    },
    handleUploadRemove(file, fileList) {
      this.fileList = fileList
      // 从 uploadList 中移除对应项（filePath 是服务器路径）
      const serverPath = file.response ? file.response.fileName : file.filePath
      this.form.uploadList = this.form.uploadList.filter(item => item.filePath !== serverPath)
    },
    handleDownload(file) {
      window.open(process.env.VUE_APP_BASE_API + '/common/download/resource?resource=' + encodeURIComponent(file.filePath))
    },
    submitForm() {
      const submitData = {
        expId: this.form.expId,
        result: this.form.result,
        stepList: this.form.stepList.map(step => ({
          stepId: step.stepId,
          stepResult: step.stepResult
        })),
        uploadList: this.form.uploadList
      }
      resultExp(submitData).then(() => {
        this.$message.success('提交成功')
        this.visible = false
        this.$emit('refresh')
      })
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
.step-result-card {
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 12px;
  margin-bottom: 12px;
  background: #fafafa;
}
.step-result-header {
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
.file-list {
  display: flex;
  flex-direction: column;
  gap: 6px;
}
.file-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 10px;
  background: #f5f7fa;
  border-radius: 4px;
}
.file-name {
  flex: 1;
  color: #606266;
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
</style>
