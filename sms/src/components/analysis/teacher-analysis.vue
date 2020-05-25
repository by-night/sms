<template>
  <div>
    <el-card style="height:70px;width: 98%;margin: 10px 0 12px 12px;padding-top: 15px;box-sizing: border-box">
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="专业：" prop="profession">
              <el-select v-model="form.professionObj" style="width: 90%" @change="professionChange" value-key="profession">
                <el-option v-for="item in classArr" :key="item.profession" :label="item.profession" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="班级：" prop="grade">
              <el-select v-model="form.grade" style="width: 90%">
                <el-option v-for="item in gradeArr" :key="item" :label="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="课程：" prop="course">
              <el-select v-model="form.course" style="width: 90%" @change="courseChange">
                <el-option v-for="item in courseArr" :key="item" :label="item" :value="item"></el-option>
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
      <el-row>
        <el-col :span="6">最高分：{{total.max}}</el-col>
        <el-col :span="6">最低分：{{total.min}}</el-col>
        <el-col :span="6">平均分：{{total.average}}</el-col>
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
        courseArr: [],
        gradeArr: [],
        classArr: [],
        form: {
          professionObj: {},
          grade: '',
          profession: '',
          course: ''
        },
        total: {},
        userInfo: {},
        showChart: false,
        lineHeight: '430px',
        lineWidth: '100%',
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
      getProfessionInfo () {
        this.axiosHelper.get(
          '/api/sms/teacher/course/getProfessionInfoByTeacher/' + this.userInfo.id
        ).then(response => {
          if (response.data.length > 0) {
            this.classArr = response.data;
            this.form = {
              profession: this.classArr[0].profession,
              grade: this.classArr[0].grade[0],
              professionObj: this.classArr[0],
              course: this.classArr[0].course[0],
            };
            for (let obj of this.classArr) {
              if (obj.profession === '电子信息工程') {
                this.form = {
                  profession: obj.profession,
                  grade: obj.grade[0],
                  professionObj: obj,
                  course: obj.course[0],
                };
                break;
              }
            }
            this.gradeArr = this.form.professionObj.grade;
            this.courseArr = this.classArr[0].course;
            this.click();
          }
        }).catch(error => {
          this.$message.error({
            message: '获取专业信息失败'
          }, error)
        })
      },
      professionChange (data) {
        this.total = {};
        this.showChart = false;
        this.form.grade = '';
        this.form.profession = data.profession;
        this.gradeArr = data.grade;

        this.form.course = '';
        this.courseArr = data.course;
      },
      changeChart () {
        this.click();
      },
      courseChange () {
        this.click();
      },
      click () {
        let userInfo = JSON.parse(localStorage.userInfo);
        let obj = {
          username: userInfo.username,
          level: userInfo.level,
          profession: this.form.profession || '',
          grade: this.form.grade || '',
          courseName: this.form.course || ''
        };
        this.getChartData(obj);
        this.getPieData(obj);
        this.getTotal();
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
      getPieData (data) {
        this.axiosHelper.get(
          '/api/sms/score/getUserNum', {params: data}).then(
          response => {
            let data = response.data;
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
      getTotal () {
        let obj = {
          username: this.userInfo.username,
          profession: this.form.profession || '',
          grade: this.form.grade || '',
          courseName: this.form.course || '',
          level: this.userInfo.level
        };
        this.axiosHelper.get(
          '/api/sms/score/getUserTotal', {params: obj}).then(
          response => {
            let obj = response.data;
            let current = {
              max: this.dealTotal(obj.max || 0),
              min: this.dealTotal(obj.min || 0),
              average: this.dealTotal(obj.average)
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
      this.getProfessionInfo();
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
