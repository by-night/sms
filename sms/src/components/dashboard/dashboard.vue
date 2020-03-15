<template>
  <div>
    <el-card class="content">
      <div class="title">
          <i class="el-icon-user-solid"></i> 个人信息
          <div style="float: right">
            <el-link :underline="false" class="edit" @click="editInfo">修改资料</el-link>
          </div>
      </div>
      <el-row style="color: #666666;padding: 20px">
        <el-col :span="3" style="margin-left: 10px">
          <div class="header">
            <i class="el-icon-user-solid"></i>
          </div>
        </el-col>
        <el-col :span="10">
          <h2 class="distance">{{userInfo.realName}}</h2>
          <div class="distance">
            {{sexName + '&#12288' + '|' + '&#12288' + userInfo.school +  '&#12288' + ' | ' + '&#12288' +  userInfo.profession}}
          </div>
          <div class="distance">
            {{userInfo.phone + '&#12288' + '|' + '&#12288' + userInfo.email}}
          </div>
        </el-col>
      </el-row>
    </el-card>
    <el-row>
      <el-col :span="6" class="divBlock block_a">
        <p class="view-title">成绩查询</p>
        <img class="view-icon" src="../../assets/project_icon_a.png" alt="">
      </el-col>
      <el-col :span="6" class="divBlock block_b">
        <p class="view-title">绩点查询</p>
        <img class="view-icon" src="../../assets/project_icon_b.png" alt="">
      </el-col>
      <el-col :span="6" class="divBlock block_c">
        <p class="view-title">成绩查询</p>
        <img class="view-icon" src="../../assets/project_icon_c.png" alt="">
      </el-col>
      <el-col :span="6" class="divBlock block_d">
        <p class="view-title">成绩查询</p>
        <img class="view-icon" src="../../assets/project_icon_d.png" alt="">
      </el-col>
    </el-row>
  </div>
</template>

<script>
import registered from '../registered/registered'
export default {
    name: "dashboard",
    data () {
        return {
          userInfo: {},
          sexName: '男',
          schoolInfo: '',
          myselfInfo: ''
        }
    },
    methods: {
      getSchoolInfo () {
        let userInfo = this.userInfo;
        if (userInfo.sex === 0) {
          this.sexName = '男';
        } else {
          this.sexName = '女';
        }
        // if (userInfo.level === 1) {
        //   this.schoolInfo = sexName + ' | ' + userInfo.school + ' | ' + userInfo.profession
        // } else {
        //   this.schoolInfo = sexName +  ' | ' + userInfo.school + ' | ' + userInfo.profession + ' | ' + userInfo.admissionTime + '届本科'
        // }
        // this.myselfInfo = userInfo.phone + ' | ' + userInfo.email
      },
      editInfo () {

      }
    },
    mounted() {
      this.userInfo = JSON.parse(localStorage.userinfo);
      this.getSchoolInfo()
    },
    components: {
      registered
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
  .content {
    margin: 10px;
    height: 230px;
  }
  .content:hover {
    background-color: #FAFBFD;
    box-shadow: 5px 8px 6px 0px rgba(70,88,131,0.2);
  }
  .edit:hover {
    transform:  scale(1.1)
  }
  .divBlock {
    padding-right: 30px;
    transition: all 0.1s;
    width: 280px;
    height: 160px;
    border-radius: 15px;
    margin: 21px;
    cursor: pointer;
  }
  .divBlock:hover {
    transform: scale(1.03);
  }
  .block_a {
    background: url('../../assets/bg_a.png') center no-repeat;
    background-size: cover;
  }
  .block_b {
    background: url('../../assets/bg_b.png') center no-repeat;
    background-size: cover;
  }
  .block_c {
    background: url('../../assets/bg_c.png') center no-repeat;
    background-size: cover;
  }
  .block_d {
    background: url('../../assets/bg_d.png') center no-repeat;
    background-size: cover;
  }
  .view-icon {
    display: inline-block;
    float: right;
    margin-top: 60px;
  }
  .view-title {
    margin: 62px 30px 10px 60px;
    display: inline-block;
    color: #fff;
    font-size: 22px;
  }
</style>
<style>
  #app , body {
    padding: 0;
    margin: 0;
  }
</style>
