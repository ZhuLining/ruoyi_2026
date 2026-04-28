<template>
  <el-dialog title="实验审核" :visible.sync="visible" width="500px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="实验名称">
        <span>{{ form.expName }}</span>
      </el-form-item>
      <el-form-item label="审核结果" prop="checkStatus">
        <el-radio-group v-model="form.checkStatus">
          <el-radio label="1">通过</el-radio>
          <el-radio label="2">驳回</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审核意见" prop="checkRemark">
        <el-input v-model="form.checkRemark" type="textarea" :rows="3" placeholder="请输入审核意见" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { checkExp } from '@/api/experimental/experimental'

export default {
  name: 'CheckDialog',
  data() {
    return {
      visible: false,
      form: {
        expId: undefined,
        expName: undefined,
        checkStatus: '1',
        checkRemark: undefined
      },
      rules: {
        checkStatus: [{ required: true, message: '请选择审核结果', trigger: 'change' }]
      }
    }
  },
  methods: {
    open(row) {
      this.reset()
      this.form.expId = row.expId
      this.form.expName = row.expName
      this.visible = true
    },
    reset() {
      this.form = {
        expId: undefined,
        expName: undefined,
        checkStatus: '1',
        checkRemark: undefined
      }
      this.resetForm('form')
    },
    cancel() {
      this.visible = false
      this.reset()
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const data = {
            expIdList: [this.form.expId],
            expStatus: parseInt(this.form.checkStatus),
            checkRemark: this.form.checkRemark
          }
          checkExp(data).then(() => {
            this.$message.success('审核成功')
            this.visible = false
            this.$emit('refresh')
          })
        }
      })
    }
  }
}
</script>
