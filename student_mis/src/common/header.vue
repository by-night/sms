<template>
  <div style="color: white">
    <el-row>
      <el-col :span="15">
        <h2>学生成绩管理系统</h2>
      </el-col>
      <el-col :span="3">
        <p class="font">{{date}}</p>
      </el-col>
      <el-col :span="4">
        <p class="font">{{week}}</p>
      </el-col>
      <el-col :span="1">
        <img src="../assets/header.jpg" width="50" height="50" style="border-radius: 50%">
      </el-col>
      <el-col :span="1">
        <p class="font">渣渣辉</p>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    export default {
        name: "header",
        data() {
            return {
                date: '',
                week: ''
            }
        },
        created() {
            let today = new Date();
            let weekday = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
            this.date = today.getFullYear() + '年' + (today.getMonth() + 1) + '月' + today.getDate() + '日';
            this.week = weekday[today.getDay()]
        },
    }
</script>

<style scoped>
  .font {
    font-size: 18px;
  }
</style>
