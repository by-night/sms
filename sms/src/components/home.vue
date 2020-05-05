<template>
  <div class="home">
    <el-container>
      <el-header class="headColor">
        <Header @click_collapse="clickCollapse"></Header>
      </el-header>
      <el-container class="header">
        <el-aside class="asideWidth">
          <Aside @collapse="isCollapse" ref="aside"></Aside>
        </el-aside>
        <el-main style="background-color: #ededed; padding: 0; height: 100%">
          <Tabs></Tabs>
          <!--内容区-->
          <router-view />
          <div class="copyright">
            Copyright @2020 庄江辉
          </div>
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
        width: '240px',
        collapse: false
      }
    },
    methods: {
      clickCollapse () {
        this.$refs.aside.isCollapse();
      },
      isCollapse (collapse) {
        this.collapse = collapse
      },
    },
    mounted() {
      // 初始化导航栏宽度
      let collapseWidth = document.getElementsByClassName("asideWidth")[0];
      collapseWidth.style.width = '240px';
      let collapseInfo = {
        state: this.collapse,
        width: collapseWidth.style.width
      };
      this.$store.commit('SAVE_COLLAPSE', collapseInfo);
    },
    components: {
        Aside, Header, Tabs
    },
    watch: {
      collapse () {
        this.width = '240px';
        let collapseWidth = document.getElementsByClassName("asideWidth")[0];
        // 点击导航栏收缩
        if(!this.collapse) {
          collapseWidth.style.width = '240px';
        } else {
          collapseWidth.style.width = '58px'
        }
        let collapseInfo = {
          state: this.collapse,
          width: collapseWidth.style.width
        };
        this.width = collapseWidth.style.width;
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
    /*background-image: linear-gradient(rgba(90,139,255,1), white);*/
    border-right: 1px solid #e6e6e6;
    width: 240px;
    transition: 0.2s;
    height: 100%
  }
  /deep/ .el-card__body {
    padding: 0;
  }
  .copyright {
    position: absolute;
    left: 0;
    bottom: 0;
    height: 24px;
    line-height: 20px;
    text-align: center;
    width: 100%;
    font-size: 12px;
    color: #666;
  }
</style>
