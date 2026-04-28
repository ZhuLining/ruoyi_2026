<template>
  <el-dialog :title="title" :visible.sync="visible" width="800px" append-to-body>
    <div v-if="isImage" class="preview-image">
      <el-image :src="fileUrl" fit="contain" style="max-width: 100%; max-height: 500px" />
    </div>
    <div v-else-if="isPdf" class="preview-pdf">
      <iframe :src="fileUrl" width="100%" height="500px" frameborder="0" />
    </div>
    <div v-else class="preview-other">
      <el-result icon="info" title="暂不支持在线预览" subTitle="请下载后查看">
        <template slot="extra">
          <el-button type="primary" size="medium" @click="handleDownload">下 载</el-button>
        </template>
      </el-result>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="visible = false">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'FilePreviewDialog',
  data() {
  return {
    visible: false,
    title: '文件预览',
    fileUrl: '',
    fileName: ''
  }
  },
  computed: {
    isImage() {
      return /\.(jpg|jpeg|png|gif|bmp)$/i.test(this.fileName)
    },
    isPdf() {
      return /\.pdf$/i.test(this.fileName)
    }
  },
  methods: {
    open(fileUrl, fileName) {
      this.fileUrl = fileUrl
      this.fileName = fileName || ''
      this.title = '文件预览 - ' + this.fileName
      this.visible = true
    },
    handleDownload() {
      this.download(this.fileUrl)
    }
  }
}
</script>