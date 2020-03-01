<template>
  <div>
    <el-card>
      <div style="font-size: 18px;background-color: #f4fcfe;border-bottom: 1px solid #dcdcdc;height: 50px;line-height: 50px;padding: 0 20px 0 20px">
          <i class="el-icon-user-solid"></i> 个人信息
          <div style="float: right"><el-link :underline="false">修改资料</el-link></div>
      </div>
      <el-row style="color: #666666">
        <el-col :span="3" style="margin-left: 10px">
          <div class="header">
            <i class="el-icon-user-solid"></i>
          </div>
        </el-col>
        <el-col :span="10">
          <h2 class="distance">{{userInfo.realName}}</h2>
          <div class="distance">{{schoolInfo}}</div>
          <div class="distance">{{myselfInfo}}</div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
    name: "dashboard",
    data () {
        return {
          userInfo: {},
          schoolInfo: '',
          myselfInfo: ''
        }
    },
    methods: {
      getSchoolInfo () {
        let userinfo = this.userInfo;
        this.schoolInfo = userinfo.sex + ' | ' + userinfo.school + ' | ' + userinfo.profession + ' | ' + userinfo.admissionTime + '届'
        this.myselfInfo = userinfo.phone + ' | ' + userinfo.email
      }
    },
    mounted() {
      this.userInfo = JSON.parse(localStorage.userinfo);
      this.getSchoolInfo()
    }
}
</script>

<style scoped>
  .header {
    background-color: gray;
    opacity: 0.1;
    font-size:50px;
    color: white;
    border-radius: 50px;
    width: 80px;
    height: 80px;
    line-height: 80px;
    text-align: center;
    margin: 30px 20px
  }
  .distance {
    margin: 15px 0px;
  }
</style>
<style>
  #app , body {
    padding: 0;
    margin: 0;
  }
</style>
