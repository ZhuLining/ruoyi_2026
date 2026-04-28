<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['exp:expManage:add']"
        >新增</el-button>
      </el-col>
    </el-row>

    <el-tabs v-model="activeName" @tab-click="handleTabClick">
      <el-tab-pane label="待审核" name="tobeReviewed">
        <TobeReviewed ref="tobeReviewed" />
      </el-tab-pane>
      <el-tab-pane label="待处理" name="unFinish">
        <UnFinishList ref="unFinish" @edit="handleEdit" />
      </el-tab-pane>
      <el-tab-pane label="已完成" name="finish">
        <FinishList ref="finish" />
      </el-tab-pane>
    </el-tabs>

    <!-- 新增/编辑弹窗 -->
    <exp-edit ref="expEdit" @refresh="handleRefresh" />
  </div>
</template>

<script>
import TobeReviewed from './components/list/TobeReviewed'
import UnFinishList from './components/list/UnFinishList'
import FinishList from './components/list/FinishList'
import ExpEdit from './components/expEdit'

export default {
  name: 'ExpManage',
  components: {
    TobeReviewed,
    UnFinishList,
    FinishList,
    ExpEdit
  },
  data() {
    return {
      activeName: 'tobeReviewed'
    }
  },
  methods: {
    handleAdd() {
      this.$refs.expEdit.open()
    },
    handleTabClick() {
      this.handleRefresh()
    },
    handleEdit(row) {
      this.$refs.expEdit.open(row)
    },
    handleRefresh() {
      if (this.activeName === 'tobeReviewed') {
        this.$refs.tobeReviewed && this.$refs.tobeReviewed.getList()
      } else if (this.activeName === 'unFinish') {
        this.$refs.unFinish && this.$refs.unFinish.getList()
      } else if (this.activeName === 'finish') {
        this.$refs.finish && this.$refs.finish.getList()
      }
    }
  }
}
</script>
