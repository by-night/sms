<template>
  <div>
    <el-card style="margin: 10px;" class="content">
      <div class="title">
          <i class="el-icon-user-solid"></i> 个人信息
          <div style="float: right">
            <el-link :underline="false" class="edit">修改资料</el-link>
          </div>
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
        let userInfo = this.userInfo;
        let sexName = '';
        if (userInfo.sex === 0) {
          sexName = '男';
        } else {
          sexName = '女';
        }
        this.schoolInfo = sexName + ' | ' + userInfo.school + ' | ' + userInfo.profession + ' | ' + userInfo.admissionTime + '届本科'
        this.myselfInfo = userInfo.phone + ' | ' + userInfo.email
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
  .title {
    font-size: 18px;
    background-color: #f4fcfe;
    border-bottom: 1px solid #dcdcdc;
    height:50px;
    line-height: 50px;
    padding: 0 20px 0 20px;
    color: #0089AB;
  }
  .content:hover {
    background-color: #FAFBFD;
    box-shadow: 5px 8px 6px 0px rgba(70,88,131,0.2);
  }
  .edit:hover {
    transform:  scale(1.1)
  }
</style>
<style>
  #app , body {
    padding: 0;
    margin: 0;
  }
</style>
