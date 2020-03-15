<template>
  <div>
    <el-menu
      :collapse="collapse"
      class="aside"
      router
      >
      <el-menu-item index="dashboard">
        <i class="el-icon-s-home"></i>&#12288主页
      </el-menu-item>

      <el-menu-item index="score">
        <i class="el-icon-s-unfold"></i>&#12288成绩查询
      </el-menu-item>

      <el-menu-item index="point">
        <i class="el-icon-s-opportunity"></i>&#12288绩点查询
      </el-menu-item>

      <el-menu-item index="course" v-if="level === 0">
        <i class="el-icon-s-opportunity"></i>&#12288课程录入
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
          <span>&#12288账号管理</span>
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
        name: "Aside",
        data () {
          return {
            collapse: false,
            level: -1,
          }
        },
        methods: {
          isCollapse () {
            this.collapse = !this.collapse;
            this.$emit('collapse', this.collapse)
          }
        },
        mounted() {
          this.userInfo = JSON.parse(localStorage.userinfo);
          this.level = this.userInfo.level;
        }
    }
</script>
<style scoped>
  .el-submenu .el-menu-item {
    margin-left: 61px;
  }
    /*子导航栏时的样式*/
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
  .el-menu {
    border: 0;
    /*background-image: linear-gradient(#4777e7, rgba(90,139,255,0.68));*/
  }
</style>
