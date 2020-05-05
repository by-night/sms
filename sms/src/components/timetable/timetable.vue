<template>
  <el-card style="margin: 10px;padding: 10px 10px 0 10px;height: 80vh;">
    <el-row>
      <el-button @click="change" size="small" style="margin-bottom: 15px" v-if="this.userInfo.level === 0">筛选</el-button>
      <el-button
        @click="edit"
        type="primary"
        size="small"
        style="margin-bottom: 15px"
        v-if="this.userInfo.level === 0">{{showContent ? '编辑' : '取消'}}</el-button>
      <el-button type="success" size="small" @click="click" v-if="this.userInfo.level === 0"
                   :disabled="form.grade === '' || form.year === '' || form.term === ''">录入</el-button>
      <el-button type="danger" size="small" @click="empty" v-if="this.userInfo.level === 0">清空</el-button>

      <el-select v-model="week" size="small" @change="weekChange" v-if="this.userInfo.level !== 2"
                 style="width: 100px;float: right;margin: 3px">
        <el-option v-for="item in weekArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
    </el-row>
    <el-collapse-transition>
      <el-form ref="form" :model="form" label-width="80px" v-if="show" style="height: 50px;overflow: hidden">
        <el-row>
          <el-col :span="6" v-if="userInfo.level === 0">
            <el-form-item label="专业：" prop="profession">
              <el-select v-model="form.professionObj" style="width: 90%" @change="professionChange" value-key="profession">
                <el-option v-for="item in classArr" :key="item.profession" :label="item.profession" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" v-if="userInfo.level === 0">
            <el-form-item label="班级：" prop="grade">
              <el-select v-model="form.grade" @change="gradeChange" style="width: 90%">
                <el-option v-for="item in gradeArr" :key="item" :label="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="学年：">
              <el-select v-model="form.year" style="width: 90%" @change="gradeChange">
                <el-option v-for="item in yearArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="学期：">
              <el-select v-model="form.term" style="width: 90%" @change="gradeChange">
                <el-option v-for="item in termArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-collapse-transition>
    <el-form ref="form" :model="form" label-width="80px" v-if="userInfo.level === 2" style="height: 50px;overflow: hidden">
      <el-row>
        <el-col :span="6">
          <el-form-item label="学年：">
            <el-select v-model="form.year" size="small" style="width: 90%" @change="getTimetableByStudent">
              <el-option v-for="item in yearArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="学期：">
            <el-select v-model="form.term" style="width: 90%" size="small" @change="getTimetableByStudent">
              <el-option v-for="item in termArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-select v-model="week" size="small" @change="weekChange" v-if="this.userInfo.level === 2"
                   style="width: 100px;float: right;margin: 3px">
          <el-option v-for="item in weekArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-row>
    </el-form>
    <div :style="userInfo.level === 1 ? {marginTop: '8px'} : {}">
      <VmBaseTable
        :setTableHigh="true"
        ref="time_table"
        :data="dataTable"
        :columns="dataColumns"
        :tableHigh="tableHigh"
        stripe
        noPage
        rowLight
      :headerStyle="headerStyle"
      :rowStyle="rowStyle"
      ></VmBaseTable>
    </div>
    <VmTimetable ref="timetable_model"></VmTimetable>
  </el-card>
</template>

<script>
  import VmTimetable from './model/timetable-model'
  import VmBaseTable from '../../base/base-table'
  export default {
    name: "timetable",
    components: {
      VmBaseTable, VmTimetable
    },
    data () {
      return {
        week: '',
        weekArr: [],
        showContent: true,
        doneNum: 0,
        show: false,
        courseArr: [],
        gradeArr: [],
        classArr: [],
        termArr: [{
          label: '上',
          value: 1
        },{
          label: '下',
          value: 2
        }],
        professionArr: [],
        form: {
          profession: '',
          grade: '',
          courseName: '',
          year: '',
          term: ''
        },
        table: null,
        searchValue: {
          $limit: 10,
          $offset: 0,
          code: ''
        },
        headerStyle: {backgroundColor: '#67a1ff', color: 'white', opacity: 0.8},
        rowStyle: {backgroundColor: '#eaf2ff'},
        userInfo: {},
        dataTable: [],
        yearArr: [],
        dataColumns: [
          {
            label: '时间',
            prop: 'time',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              let i = params.row.time;
              return h('div', {}, this.section[i])
            }
          } , {
            label: '周一',
            prop: 'monday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level !== 0 || this.showContent) {
                let monday = params.row.monday || '一';
                if (monday === '一') {
                  return h('div', {}, monday)
                } else {
                  return this.getLink(h, monday, () => {
                    this.clickCourse(monday);
                  })
                }
              } else {
                return this.getSelect(h, params.row.monday, (value) => {
                  this.$set(params.row, 'monday', value);
                }, this.courseArr, 'small', '', '', '', true)
              }
            }
          } , {
            label: '周二',
            prop: 'tuesday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level !== 0 || this.showContent) {
                let tuesday = params.row.tuesday || '一';
                if (tuesday === '一') {
                  return h('div', {}, tuesday)
                } else {
                  return this.getLink(h, tuesday, () => {
                    this.clickCourse(tuesday);
                  })
                }
              } else {
                return this.getSelect(h, params.row.tuesday, (value) => {
                  this.$set(params.row, 'tuesday', value);
                }, this.courseArr, 'small', '', '', '', true)
              }
            }
          } , {
            label: '周三',
            prop: 'wednesday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level !== 0 || this.showContent) {
                let wednesday = params.row.wednesday || '一';
                if (wednesday === '一') {
                  return h('div', {}, wednesday)
                } else {
                  return this.getLink(h, wednesday, () => {
                    this.clickCourse(wednesday);
                  })
                }
              } else {
                return this.getSelect(h, params.row.wednesday, (value) => {
                  this.$set(params.row, 'wednesday', value);
                }, this.courseArr, 'small', '', '', '', true)
              }
            }
          }, {
            label: '周四',
            prop: 'thursday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level !== 0 || this.showContent) {
                let thursday = params.row.thursday || '一';
                if (thursday === '一') {
                  return h('div', {}, thursday)
                } else {
                  return this.getLink(h, thursday, () => {
                    this.clickCourse(thursday);
                  })
                }
              } else {
                return this.getSelect(h, params.row.thursday, (value) => {
                  this.$set(params.row, 'thursday', value);
                }, this.courseArr, 'small', '', '', '', true)
              }
            }
          } , {
            label: '周五',
            prop: 'friday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level !== 0 || this.showContent) {
                let friday = params.row.friday || '一';
                if (friday === '一') {
                  return h('div', {}, friday)
                } else {
                  return this.getLink(h, friday, () => {
                    this.clickCourse(friday);
                  })
                }
              } else {
                return this.getSelect(h, params.row.friday, (value) => {
                  this.$set(params.row, 'friday', value);
                }, this.courseArr, 'small', '', '', '', true)
              }
            }
          } , {
            label: '周六',
            prop: 'saturday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level !== 0 || this.showContent) {
                let saturday = params.row.saturday || '一';
                if (saturday === '一') {
                  return h('div', {}, saturday)
                } else {
                  return this.getLink(h, saturday, () => {
                    this.clickCourse(saturday);
                  })
                }
              } else {
                return this.getSelect(h, params.row.saturday, (value) => {
                  this.$set(params.row, 'saturday', value);
                }, this.courseArr, 'small', '', '', '', true)
              }
            }
          } , {
            label: '周日',
            prop: 'sunday',
            style: 'center',
            minWidth: '',
            render: (h, params) => {
              if (this.userInfo.level !== 0 || this.showContent) {
                let sunday = params.row.sunday || '一';
                if (sunday === '一') {
                  return h('div', {}, sunday)
                } else {
                  return this.getLink(h, sunday, () => {
                    this.clickCourse(sunday);
                  })
                }

              } else {
                return this.getSelect(h, params.row.sunday, (value) => {
                  this.$set(params.row, 'sunday', value);
                }, this.courseArr, 'small', '', '', '', true)
              }
            }
          }
        ],
        section: ['第一节', '第二节', '第三节', '第四节', '第五节', '第六节', '第七节', '第八节', '第九节', '第十节'],
      }
    },
    methods: {
      search() {
        let arr = [];
        for (let i = 0; i < this.section.length; i++) {
          arr.push({time: i});
        }
        this.dataTable = arr;
      },
      edit () {
        this.showContent = !this.showContent;
      },
      // 获取专业
      getProfessionByAdmin () {
        this.axiosHelper.get(
          '/api/sms/teacher/course/getProfessionInfoByAdmin').then(response => {
          this.classArr = response.data;
        }).catch(error => {
          this.$message.error({
            message: '获取专业失败'
          }, error)
        })
      },
      // 选择专业之后
      professionChange (data) {
        this.form.grade = '';
        this.form.courseName = '';
        this.form.profession = data.profession;
        this.gradeArr = data.grade;
        this.courseArr = data.course.map(item => {
          return {label: item, value: item}
        });
      },
      gradeChange () {
        if (this.form.grade !== '') {
          let obj = {
            profession: this.form.profession,
            grade: this.form.grade,
            year: this.form.year,
            term: this.form.term,
            week: this.week
          };
          this.axiosHelper.get(
            '/api/sms/timetable/getTimetable', {params: obj}
          ).then(response => {
            let data = response.data;
            for (let i = 0; i < this.section.length; i++) {
              data[i].time = i;
            }
            this.dataTable = data;
          }).catch(error => {
            this.$message.error({
              message: '获取课程表失败'
            }, error)
          });
        }
      },
      click () {
        ++this.doneNum;
        if (this.doneNum === 1) {
          this.addTimetable();
        }
        this.showContent = true;
      },
      change () {
        this.show = !this.show;
      },
      empty () {
        this.showContent = false;
        this.search();
      },
      clickCourse (data) {
        this.$refs.timetable_model.init(data, this.form);
      },
      addTimetable () {
        this.dataTable = this.dataTable.map(data => {
          data.profession = this.form.profession;
          data.grade = this.form.grade;
          data.year = this.form.year;
          data.term = this.form.term;
          return data;
        });
        this.axiosHelper.post(
          '/api/sms/timetable', this.dataTable
        ).then(() => {
          this.doneNum = 0;
          this.$message.success({
            message: '录入课程表成功'
          });
          this.gradeChange();
        }).catch(error => {
          this.doneNum = 0;
          this.$message.error({
            message: '录入课程表失败'
          }, error)
        });
      },
      getTimetableByStudent () {
        let obj = {
          studentName: this.userInfo.id,
          year: this.form.year,
          term: this.form.term,
          week: this.week
        };
        this.axiosHelper.get(
          '/api/sms/timetable/getTimetableByStudent', {params: obj}
        ).then(response => {
          let data = response.data;
          if (data.length > 0) {
            for (let i = 0; i < this.section.length; i++) {
              data[i].time = i;
            }
            this.dataTable = data;
          } else {
            this.search();
          }
        }).catch(error => {
          this.$message.error({
            message: '获取课程表失败'
          }, error)
        });
      },
      getTimetableByTeacher () {
        let obj = {
          teacherId: this.userInfo.id,
          week: this.week
        };
        this.axiosHelper.get(
          '/api/sms/timetable/getTimetableByTeacher', {params: obj}
        ).then(response => {
          let data = response.data;
          if (data.length > 0) {
            for (let i = 0; i < this.section.length; i++) {
              data[i].time = i;
            }
            this.dataTable = data;
          }
        }).catch(error => {
          this.$message.error({
            message: '获取课程表失败'
          }, error)
        });
      },
      getYearArrByAdmin () {
        let now = new Date().getFullYear();
        let old = 2016;
        for (let i = old; i < now + 1; i++) {
          this.yearArr.push({label: i, value: i});
        }
        this.getDefault();
      },
      getYearArr () {
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
      weekChange () {
        this.userInfo = JSON.parse(localStorage.userInfo);
        if (this.userInfo.level === 0) {
          this.gradeChange();
        } else if (this.userInfo.level === 1) {
          this.getTimetableByTeacher();
        } else {
          this.getTimetableByStudent();
        }
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
      }
    },
    computed: {
      tableHigh () {
        if (this.userInfo.level === 0) {
          return this.show ? '63vh' : '72vh';
        } else {
          return '72vh'
        }
      }
    },
    created () {
      for (let i = 1; i < 31; i++) {
        this.weekArr.push({
          label: '第 ' + i + ' 周',
          value: i
        })
      }
      this.week = this.weekArr[0].value;
      this.userInfo = JSON.parse(localStorage.userInfo);
      if (this.userInfo.level === 0) {
        this.getProfessionByAdmin();
        this.getYearArrByAdmin();
      } else if (this.userInfo.level === 1) {
        this.getTimetableByTeacher();
        this.getYearArrByAdmin();
      } else {
        this.getYearArr();
        this.getTimetableByStudent();
      }
    },
    mounted () {
      this.table = this.$refs['time_table'];
      this.search();
    }
  }

</script>
