<template>
  <div>
    <el-upload
      :action="uploadUrl"
      :headers="headers"
      :on-success="handleSuccess"
      :on-error="handleError"
      :on-remove="handleRemove"
      :file-list="fileList"
      :multiple="multiple"
      :limit="limit"
      :before-upload="beforeUpload"
    >
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">{{ tip }}</div>
    </el-upload>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'

export default {
  name: 'FileUpload',
  props: {
    value: {
      type: Array,
      default: () => []
    },
    multiple: {
      type: Boolean,
      default: true
    },
    limit: {
      type: Number,
      default: 5
    },
    tip: {
      type: String,
      default: '支持上传多个文件'
    }
  },
  data() {
    return {
      uploadUrl: process.env.VUE_APP_BASE_API + '/common/upload',
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      fileList: this.value
    }
  },
  watch: {
    value(val) {
      this.fileList = val
    }
  },
  methods: {
    handleSuccess(response, file, fileList) {
      this.fileList = fileList
      this.$emit('input', fileList)
      this.$message.success('上传成功')
    },
    handleError(err, file) {
      this.$message.error('上传失败：' + err)
    },
    handleRemove(file, fileList) {
      this.fileList = fileList
      this.$emit('input', fileList)
    },
    beforeUpload(file) {
      const maxSize = 10 * 1024 * 1024
      if (file.size > maxSize) {
        this.$message.error('文件大小不能超过10MB')
        return false
      }
      return true
    }
  }
}
</script>