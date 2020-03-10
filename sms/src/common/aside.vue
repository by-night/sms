<template>
  <div>
    <el-header style="background-color: #4777e7 !important">
      <img src="../assets/header.png" alt="" width="40" height="40" style="margin: 10px 30px" />
      <i class="el-icon-menu collapse" @click="isCollapse" style="margin-right: 20px"></i>
    </el-header>
    <el-menu
      :collapse="collapse"
      class="aside"
      router
      >
      <el-menu-item index="dashboard">
        <i class="el-icon-s-home"></i>主页
      </el-menu-item>

      <el-menu-item index="score">
        <i class="el-icon-s-unfold"></i>成绩查询
      </el-menu-item>

      <el-menu-item index="point">
        <i class="el-icon-s-opportunity"></i>绩点查询
      </el-menu-item>

      <!--<el-submenu index="1">-->
        <!--<template slot="title">-->
          <!--<i class="el-icon-location"></i>-->
          <!--<span>导航一</span>-->
        <!--</template>-->
        <!--<el-menu-item-group>-->
          <!--<el-menu-item index="list">选项1</el-menu-item>-->
        <!--</el-menu-item-group>-->
      <!--</el-submenu>-->

      <el-submenu index="setting" v-if="level === 0">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>账号管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="student">学生账号</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group>
          <el-menu-item index="teacher">教师账号</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
    export default {
        name: "aside",
        data () {
          return {
            collapse: false,
            level: -1,
          }
        },
        methods: {
          isCollapse () {
            this.collapse = !this.collapse
            this.$emit('collapse', this.collapse)
          }
        },
        mounted() {
          this.userInfo = JSON.parse(localStorage.userinfo);
          this.level = this.userInfo.level;
        }
    }
</script>
<style>
  // 导航栏颜色
  .el-aside {
    background-color:  #CCCCCC;
  }
</style>
<style scoped>
  .el-submenu .el-menu-item {
    margin-left: 61px
  }
  /* 旋转的关闭按钮*/
  .collapse {
    font-size: 25px;
    float: right;
    line-height: 60px;
    transition: 0.2s;
    transform: rotate(-180deg);
    cursor: pointer;
  }
  /* 旋转的关闭按钮*/
  .collapse:hover{
    transition: 0.2s;
    transform: rotate(180deg)
  }
  /*导航栏时的样式*/
  /deep/ .el-submenu .el-menu-item {
    min-width: 80px !important;
    width: 164px;
    height: 40px;
    margin-bottom: 5px !important;
    margin-left: 40px !important;
    line-height: 40px;
    text-align: center;
    border-radius: 55px;
  }
  /deep/ .el-menu-item.is-active {
    background-color: #409EFF;
    color: white;
  }
  /deep/ .el-menu-item-group__title {
    padding: 0;
  }
</style>
