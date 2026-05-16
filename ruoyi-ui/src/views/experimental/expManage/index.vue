<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleTabClick">
      <el-tab-pane label="待处理" name="unFinish">
        <UnFinishList ref="unFinish" @add="handleAdd" @edit="handleEdit" />
      </el-tab-pane>
      <el-tab-pane v-if="hasCheckPerm" label="待审核" name="tobeReviewed">
        <TobeReviewed ref="tobeReviewed" @add="handleAdd" />
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
  computed: {
    hasCheckPerm() {
      const perms = this.$store.getters.permissions || []
      return perms.includes('*:*:*') || perms.includes('exp:expManage:check')
    }
  },
  data() {
    const perms = this.$store.getters.permissions || []
    return {
      activeName: perms.includes('*:*:*') || perms.includes('exp:expManage:check') ? 'tobeReviewed' : 'unFinish'
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

<style scoped lang="scss">
.app-container ::v-deep .el-tabs__header {
  margin-bottom: 18px;
}

.app-container ::v-deep .el-tabs__nav-wrap::after {
  height: 1px;
  background-color: #e4e7ed;
}

.app-container ::v-deep .el-tabs__item {
  font-size: 15px;
  font-weight: 500;
  color: #606266;
  padding: 0 20px;
  height: 42px;
  line-height: 42px;
  transition: all 0.3s ease;
}

.app-container ::v-deep .el-tabs__item.is-active {
  color: #409eff;
  font-weight: 600;
}

.app-container ::v-deep .el-tabs__active-bar {
  height: 3px;
  border-radius: 2px;
  background-color: #409eff;
}

.app-container ::v-deep .el-tabs__item:hover {
  color: #409eff;
}
</style>
