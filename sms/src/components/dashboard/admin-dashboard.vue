<template>
  <div style="">
    <!--个人信息-->
    <el-card class="content" style="width: 60%;margin: 12px">
      <div class="title">
        <i class="el-icon-user-solid"></i> 个人信息
        <div style="float: right">
          <el-link :underline="false" class="edit" @click="editInfo">修改资料</el-link>
        </div>
      </div>
      <el-row style="color: #666666;padding: 20px">
        <el-col :span="6" style="margin-left: 10px">
          <div class="header">
            <i class="el-icon-user-solid"></i>
          </div>
        </el-col>
        <el-col :span="12">
          <h2 class="distance" style="margin-bottom: 35px">{{userInfo.realName}}</h2>
          <div class="distance">
            <i class="el-icon-male" v-if="userInfo.sex === 0" title="性别"> {{sexName}}</i>
            <i class="el-icon-female" v-else title="性别"> {{sexName}}</i>{{'&#12288'}}
            <i class="el-icon-office-building" title="学校"> {{userInfo.school}}</i>
          </div>
          <div class="distance">
            <i class="el-icon-phone-outline" title="联系方式"> {{userInfo.phone}}</i> {{'&#12288&#12288'}}
            <i class="el-icon-message" title="邮箱"> {{userInfo.email}}</i>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <!--饼状图-->
    <el-card class="content" style="width: 36.8%;margin: 12px 12px 12px 0">
      <el-button type="text" icon="el-icon-s-tools" @click="setting" class="settingStyle" title="选择班级"></el-button>
      <div style="margin-top: -15px" v-if="showPie">
        <ve-pie :data="pieData" :settings="pieSetting"></ve-pie>
      </div>
      <div v-else style="text-align: center;line-height: 200px">
        <span style="color: gray">暂无数据</span>
      </div>
    </el-card>
    <!--折线图-->
    <el-card class="content" style="width: 98%;margin: 0 0 12px 12px">
      <el-button v-if="showChart" title="切换图表" @click="changeMethod" class="changeChartStyle" type="text" icon="el-icon-refresh"></el-button>
      <div style="margin-top: -15px" v-if="showChart">
        <ve-line v-if="changeChart" :data="lineData" :width="lineWidth" :height="lineHeight"></ve-line>
        <ve-histogram :data="histogramData" :height="lineHeight" v-else></ve-histogram>
      </div>
      <div style="text-align: center;line-height: 290px" v-else>
        <span style="color: gray">暂无数据</span>
      </div>
    </el-card>
    <div>
      <!--<div style="float: right;margin: 20px 150px 0 0">-->
      <!--<div style="background-color: #5f91ff;width: 300px;height: 150px">-->
      <!--<p class="view-title">绩点查询</p>-->
      <!--<img class="view-icon" src="../../assets/project_icon_b.png" alt="">-->
      <!--</div>-->
      <!--</div>-->
      <!--<el-row>-->
      <!--<el-col :span="6" class="divBlock block_b">-->
      <!--<p class="view-title">绩点查询</p>-->
      <!--<img class="view-icon" src="../../assets/project_icon_b.png" alt="">-->
      <!--</el-col>-->
      <!--<el-col :span="6" class="divBlock block_c">-->
      <!--<p class="view-title">成绩查询</p>-->
      <!--<img class="view-icon" src="../../assets/project_icon_c.png" alt="">-->
      <!--</el-col>-->
      <!--<el-col :span="6" class="divBlock block_d">-->
      <!--<p class="view-title">成绩查询</p>-->
      <!--<img class="view-icon" src="../../assets/project_icon_d.png" alt="">-->
      <!--</el-col>-->
      <!--</el-row>-->
    </div>
    <editInfo ref="editInfo_model" @refresh="refresh"></editInfo>
    <adminSetting ref="setting_model" @professionInfo="professionInfo"></adminSetting>
  </div>
</template>

<script>
  import adminSetting from './model/admin-setting-model'
  import editInfo from './model/admin-edit-model'
  export default {
    inject:['reload'],
    name: "dashboard",
    data () {
      this.pieSetting = {
        offsetY: 150,
        radius: 90,
        labelLine: {
          length: 8,
          smooth: false
        },
      };
      return {
        showChart: false,
        showPie: false,
        lineHeight: '330px',
        lineWidth: '100%',
        changeChart: true,
        userInfo: {},
        sexName: '男',
        schoolInfo: '',
        myselfInfo: '',
        pieData: {
          columns: ['label', 'value'],
          rows: [],
        },
        histogramData: {
          columns: ['realName', '分数'],
          rows: []
        },
        lineData: {
          columns: ['realName', '分数'],
          rows: []
        }
      }
    },
    methods: {
      getSchoolInfo () {
        let userInfo = this.userInfo;
        this.sexName = userInfo.sex === 0 ? '男' : '女';
      },
      editInfo () {
        let userInfo = Object.assign({}, this.userInfo);
        this.$refs.editInfo_model.init(userInfo);
      },
      refresh () {
        // 刷新页面
        this.reload();
      },
      getChartData (data) {
        this.axiosHelper.get(
          '/api/sms/score/export',
          {params: data}).then(response => {
          let data = response.data;
          this.lineData.rows = data.map(item => {
            let obj = {
              realName: item.realName,
              '分数': item.scoreByUser || 0
            };
            return obj
          });
          this.showChart = data.some(item => {
            return item.scoreByUser !== null
          });
          this.histogramData = this.lineData;
        }).catch(error => {
          this.$message.error({
            message: '获取成绩失败'
          }, error)
        })
      },
      setting () {
        this.$refs.setting_model.init();
      },
      changeMethod () {
        this.changeChart = !this.changeChart;
      },
      getPieData (data) {
        this.axiosHelper.get(
          '/api/sms/score/getUserNum', {params: data}).then(
          response => {
            let data = response.data;
            this.pieData.rows = data;
            this.showPie = data.some(item => {
              return item.value !== 0;
            });
          }).catch(error => {
          this.$message.error({
            message: '获取饼图信息失败'
          }, error)
        })
      },
      professionInfo (data) {
        let userInfo = JSON.parse(localStorage.userInfo);
        let obj = {
          username: userInfo.username,
          level: userInfo.level,
          profession: data.profession || '',
          grade: data.grade || '',
          courseName: data.course || ''
        };
        this.getChartData(obj);
        this.getPieData(obj);
      },
    },
    mounted() {
      this.userInfo = JSON.parse(localStorage.userInfo);
      this.getSchoolInfo();
    },
    components: {
      editInfo, adminSetting
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
    margin: 15px 0;
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
    height: 300px;
    float: left;
  }
  .content:hover {
    background-color: #F5FAF5;
    box-shadow: 5px 8px 6px 0px rgba(70,88,131,0.2);
  }
  .edit:hover {
    transform:  scale(1.1)
  }
  .settingStyle {
    color: gray;
    margin-left: 450px;
  }
  .settingStyle:hover {
    transform: scale(1.2);
    color: #409EFF;
  }
  .changeChartStyle {
    margin: -4px 0 0 97.5%;
    color: gray;
    font-size: 16px;
  }
  .changeChartStyle:hover {
    transform: scale(1.2);
    color: #409EFF;
  }
</style>
<style>
  #app , body {
    padding: 0;
    margin: 0;
  }
</style>
