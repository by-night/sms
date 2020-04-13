<template>
  <div>
    <el-card style="height:70px;width: 98%;margin: 10px 0 12px 12px;padding-top: 15px;box-sizing: border-box">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="学年：">
              <el-select v-model="form.year" style="width: 90%" @change="click">
                <el-option v-for="item in yearArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="学期：">
              <el-select v-model="form.term" style="width: 90%" @change="click">
                <el-option v-for="item in termArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!--饼状图-->
    <!--<div style="margin-top: -15px" v-if="showPie">-->
      <!--<ve-pie :data="pieData" :settings="pieSetting"></ve-pie>-->
    <!--</div>-->
    <!--<div v-else style="text-align: center;line-height: 200px">-->
      <!--<span style="color: gray">暂无数据</span>-->
    <!--</div>-->

    <!--折线图-->
    <el-card style="height:400px;width: 98%;margin: 0 0 12px 12px;">
      <el-button v-if="showChart" title="切换图表" @click="changeMethod" class="changeChartStyle" type="text" icon="el-icon-refresh"></el-button>
      <div style="margin-top: -15px" v-if="showChart">
        <ve-line v-if="changeChart" :data="lineData" :width="lineWidth" :height="lineHeight"></ve-line>
        <ve-histogram :data="histogramData" :height="lineHeight" v-else></ve-histogram>
      </div>
      <div style="text-align: center;line-height: 354px;" v-else>
        <span style="color: gray">暂无数据</span>
      </div>
      <el-row style="padding-left: 30px;font-weight: bold;">
        <el-col :span="6">最高分：</el-col>
        <el-col :span="6">平均分：</el-col>
      </el-row>
    </el-card>
    <el-card class="info" v-if="this.userInfo.level === 2">
      <el-row style="">
        <el-col :span="6">总绩点：{{total.point}}</el-col>
        <el-col :span="6">总学分：{{total.credits}}</el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
  export default {
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
        yearArr: [],
        form: {
          term: '',
          year: ''
        },
        termArr: [{
          label: '上',
          value: 1
        },{
          label: '下',
          value: 2
        }],
        total: {
          credits: '',
          point: ''
        },
        userInfo: {},
        showChart: false,
        showPie: false,
        lineHeight: '330px',
        lineWidth: '100%',
        changeChart: true,
        pieData: {
          columns: ['label', 'value'],
          rows: [],
        },
        histogramData: {
          columns: ['courseName', '分数'],
          rows: []
        },
        lineData: {
          columns: ['courseName', '分数'],
          rows: []
        },
      }
    },
    methods: {
      getYear () {
        let now = new Date().getFullYear();
        let old = parseInt(this.userInfo.admissionTime);
        for (let i = old; i < now; i++) {
          if (this.yearArr.length < 4) {
            let num = i - old;
            let obj = {};
            switch (num) {
              case 0:
                obj = {label: '大一', value: i};
                break;
              case 1:
                obj = {label: '大二', value: i};
                break;
              case 2:
                obj = {label: '大三', value: i};
                break;
              case 3:
                obj = {label: '大四', value: i};
                break;
            }
            this.yearArr.push(obj);
          }
        }
        this.getDefault();
      },
      getDefault () {
        // 获取学年和学期的初始值
        this.form.year = this.yearArr[this.yearArr.length-1].value;
        let month = new Date().getMonth()+1;
        if (month > 2 && month < 6) {
          // 上学期
          this.form.term = this.termArr[0].value;
        } else {
          // 下学期
          this.form.term = this.termArr[1].value;
        }
        this.click();
      },
      click () {
        let userInfo = JSON.parse(localStorage.userInfo);
        let obj = {
          studentName: userInfo.username,
          level: userInfo.level,
          year: this.form.year || '',
          term: this.form.term || ''
        };
        this.getChartData(obj);
        this.getPieData(obj);
      },
      changeMethod () {
        this.changeChart = !this.changeChart;
      },
      getChartData (data) {
        this.axiosHelper.get(
          '/api/sms/score/export',
          {params: data}).then(response => {
          let data = response.data;
          this.lineData.rows = data.map(item => {
            let obj = {
              courseName: item.name,
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
          studentName: userInfo.username,
          level: userInfo.level,
          profession: data.profession || '',
          grade: data.grade || ''
        };
        this.getChartData(obj);
        this.getPieData(obj);
      },
      dealTotal (value) {
        let str = value.toString();
        // 是否存在小数
        let len = str.lastIndexOf(".");
        // num: 不存在小数则设为0，存在则等于小数点后面位数
        let num = len === -1 ? 0 : str.substring(len+1).length;
        switch (num) {
          case 0:
            str = str + '.00';
            break;
          case 1:
            str = str + '0';
            break;
          case 2:
            break;
          default:
            str = str.slice(0,4)
        }
        return str;
      },
      getTotal () {
        let obj = {
          studentName: this.userInfo.id
        };
        this.axiosHelper.get(
          '/api/sms/score/getUserTotal', {params: obj}).then(
          response => {
            let obj = response.data;
            let current = {
              credits: this.dealTotal(obj.point),
              point: this.dealTotal(obj.point)
            };
            this.total = {...current};
          })
      }
    },
    mounted() {
      this.userInfo = JSON.parse(localStorage.userInfo);
      this.getYear();
      this.getTotal();
    },
  }
</script>

<style scoped>
  .content {
    height: 300px;
    float: left;
  }
  .content:hover {
    background-color: #F5FAF5;
    box-shadow: 5px 8px 6px 0px rgba(70,88,131,0.2);
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
  .info {
    font-weight: bold;
    height: 50px;
    line-height: 50px;
    padding-left: 30px;
    box-sizing: border-box;
    width: 98%;
    margin: 0 0 0 12px;
  }
</style>
<style>
  #app , body {
    padding: 0;
    margin: 0;
  }
</style>
