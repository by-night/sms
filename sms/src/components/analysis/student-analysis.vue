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

    <el-card style="height:470px;width: 98%;margin: 0 0 12px 12px;">
      <div style="margin: 15px 15px 30px 15px">
        <span style="font-size: 14px;color: #606266;">图表选择：</span>
        <el-select size="small" v-model="chartValue" @change="changeChart">
          <el-option v-for="item in chartArr" :key="item.value" :value="item.value" :label="item.label"></el-option>
        </el-select>
      </div>
      <div style="margin-top: -15px;height: 430px;" v-if="showChart">
        <!--饼状图-->
        <ve-pie  v-if="chartValue === 1" :data="pieData"></ve-pie>
        <!--折线图-->
        <ve-line v-if="chartValue === 2" :data="lineData" ref="chart" :width="lineWidth" :height="lineHeight"></ve-line>
        <!--条形图-->
        <ve-histogram  v-if="chartValue === 3" :data="histogramData" ref="chart" :height="lineHeight"></ve-histogram>
      </div>
      <div style="text-align: center;line-height: 335px;" v-else>
        <span style="color: gray">暂无数据</span>
      </div>
    </el-card>
    <el-card class="info">
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
      return {
        chartValue: 1,
        chartArr: [{
          label: '饼状图',
          value: 1
        }, {
          label: '折线图',
          value: 2
        }, {
          label: '柱形图',
          value: 3
        }],
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
        showChart: true,
        lineHeight: '430px',
        lineWidth: '100%',
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
      changeChart () {
        this.click();
      },
      getDefault () {
        // 获取学年和学期的初始值
        this.form.year = 2016;
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
            data = data.filter(item => {
              return item.label !== '未录入'
            });
            this.pieData.rows = data;
            this.showChart = data.some(item => {
              return item.value !== 0;
            });
          }).catch(error => {
          this.$message.error({
            message: '获取饼图信息失败'
          }, error)
        })
      },
      // 获取总学分和总绩点
      getTotal () {
        let obj = {
          studentName: this.userInfo.id,
          level: this.userInfo.level,
        };
        this.axiosHelper.get(
          '/api/sms/score/getUserTotal', {params: obj}).then(
          response => {
            let obj = response.data;
            let current = {
              credits: this.dealTotal(obj.credits),
              point: this.dealTotal(obj.point)
            };
            this.total = {...current};
          })
      }
    },
    computed: {
      collapse() {
        return this.$store.state.collapse;
      }
    },
    watch: {
      collapse() {
        setTimeout(() => {
          this.$refs['chart'].resize()
        }, 150)
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
