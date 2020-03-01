<template>
  <div class="home">
    <el-container>
      <el-aside class="asideWidth">
        <Aside @collapse="isCollapse"></Aside>
      </el-aside>
      <el-container class="header">
        <el-header class="headColor">
          <Header></Header>
        </el-header>
        <el-main style="background-color: #ededed; padding: 0">
          <Tabs></Tabs>
          <!--内容区-->
          <el-card style="height: 94%; margin-left: 10px;">
            <router-view />
          </el-card>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Tabs from '../common/tabs'
import Aside from '../common/aside'
import Header from '../common/header'

export default {
    name: "home",
    data () {
      return {
        collapse: false
      }
    },
    methods: {
      isCollapse (collapse) {
        this.collapse = collapse
      }
    },
    mounted() {
      // 初始化导航栏宽度
      let collapseWidth = document.getElementsByClassName("asideWidth")[0];
      collapseWidth.style.width = '240px';
    },
    components: {
        Aside, Header, Tabs
    },
    watch: {
      collapse () {
        let collapseWidth = document.getElementsByClassName("asideWidth")[0];
        // 点击导航栏收缩
        if(!this.collapse) {
          collapseWidth.style.width = '240px';
        } else {
          collapseWidth.style.width = '64px'
        }
        let collapseInfo = {
          state: this.collapse,
          width: collapseWidth.style.width
        }
        // 把collapse信息传入vuex
        this.$store.commit('SAVE_COLLAPSE', collapseInfo)
      }
    }
}
</script>

<style>
  html, body, #app, .home, .el-container {
    margin: 0 !important;
    padding: 0 !important;
    height: 100% !important;
  }
</style>
<style scoped>
  .headColor {
    height: 70px;
    background-color: #5a8bff;
    padding: 0;
    margin: 0;
  }
  .asideWidth {
    transition: 0.2s
  }
  /deep/ .el-card__body {
    padding: 0;
  }
</style>
