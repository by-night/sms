<template>
  <el-card style="margin: 10px;padding: 15px 10px 10px 10px;height: 78vh">
    <el-form ref="form" :model="form" label-width="80px">
      <el-row v-if="userInfo.level === 0">
        <el-col :span="8">
          <el-form-item label="专业：" prop="profession">
            <el-select v-model="form.professionObj" style="width: 100%" @change="professionChange" value-key="profession">
              <el-option v-for="item in classArr" :key="item.profession" :label="item.profession" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="班级：" prop="grade">
            <el-select v-model="form.grade" @change="gradeChange" style="width: 100%">
              <el-option v-for="item in gradeArr" :key="item" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button type="success" size="small" @click="click" :disabled="this.form.grade === ''">&#12288录入&#12288</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div :style="userInfo.level === 0 ? {} : {marginTop: '50px'}">
      <VmBaseTable
        :setTableHigh="true"
        ref="time_table"
        :data="dataTable"
        :columns="dataColumns"
        :tableHigh="tableHigh"
        :headerStyle="headerStyle"
        :rowStyle="rowStyle"
        stripe
        noPage
        rowLight
      ></VmBaseTable>
    </div>
  </el-card>
</template>

<script>
  import VmBaseTable from '../../base/base-table'
  export default {
    name: "timetable",
    components: {
      VmBaseTable
    },
    data () {
      return {
        courseArr: [],
        gradeArr: [],
        classArr: [],
        professionArr: [],
        form: {
          profession: '',
          grade: '',
          term: '',
          courseName: ''
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
              if (this.userInfo.level === 0) {
                return this.getSelect(h, params.row.monday, (value) => {
                  this.$set(params.row, 'monday', value);
                }, this.courseArr, 'small')
              } else {
                let monday = params.row.monday || '一';
                return h('div', {}, monday)
              }
            }
          } , {
            label: '周二',
            prop: 'tuesday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level === 0) {
                return this.getSelect(h, params.row.tuesday, (value) => {
                  this.$set(params.row, 'tuesday', value);
                }, this.courseArr, 'small')
              } else {
                let tuesday = params.row.tuesday || '一';
                return h('div', {}, tuesday)
              }
            }
          } , {
            label: '周三',
            prop: 'wednesday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level === 0) {
                return this.getSelect(h, params.row.wednesday, (value) => {
                  this.$set(params.row, 'wednesday', value);
                }, this.courseArr, 'small')
              } else {
                let wednesday = params.row.wednesday || '一';
                return h('div', {}, wednesday)
              }
            }
          }, {
            label: '周四',
            prop: 'thursday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level === 0) {
                return this.getSelect(h, params.row.thursday, (value) => {
                  this.$set(params.row, 'thursday', value);
                }, this.courseArr, 'small')
              } else {
                let thursday = params.row.thursday || '一';
                return h('div', {}, thursday)
              }
            }
          } , {
            label: '周五',
            prop: 'friday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level === 0) {
                return this.getSelect(h, params.row.friday, (value) => {
                  this.$set(params.row, 'friday', value);
                }, this.courseArr, 'small')
              } else {
                let friday = params.row.friday || '一';
                return h('div', {}, friday)
              }
            }
          } , {
            label: '周六',
            prop: 'saturday',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.userInfo.level === 0) {
                return this.getSelect(h, params.row.saturday, (value) => {
                  this.$set(params.row, 'saturday', value);
                }, this.courseArr, 'small')
              } else {
                let saturday = params.row.saturday || '一';
                return h('div', {}, saturday)
              }
            }
          } , {
            label: '周日',
            prop: 'sunday',
            style: 'center',
            minWidth: '',
            render: (h, params) => {
              if (this.userInfo.level === 0) {
                return this.getSelect(h, params.row.sunday, (value) => {
                  this.$set(params.row, 'sunday', value);
                }, this.courseArr, 'small')
              } else {
                let sunday = params.row.sunday || '一';
                return h('div', {}, sunday)
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
      // 获取专业
      getProfessionByAdmin () {
        this.axiosHelper.get(
          '/api/sms/teacher/course/getProfessionInfoByAdmin').then(response => {
          this.classArr = response.data;
        }).catch(error => {
          this.$message.error({
            message: '失败'
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
      // 选择班级之后
      gradeChange () {
        let obj = {
          profession: this.form.profession,
          grade: this.form.grade
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
      },
      click () {
        this.addTimetable();
      },
      addTimetable () {
        this.dataTable = this.dataTable.map(data => {
          data.profession = this.form.profession;
          data.grade = this.form.grade;
          return data;
        });
        this.axiosHelper.post(
          '/api/sms/timetable', this.dataTable
        ).then(() => {
          this.$message.success({
            message: '录入课程表成功'
          });
          this.gradeChange();
        }).catch(error => {
          this.$message.error({
            message: '录入课程表失败'
          }, error)
        });
      },
      getTimetableByStudent () {
        let obj = {
          studentName: this.userInfo.id
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
          }
        }).catch(error => {
          this.$message.error({
            message: '获取课程表失败'
          }, error)
        });
      },
      getTimetableByTeacher () {
        let obj = {
          teacherId: this.userInfo.id
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
      }
    },
    computed: {
      tableHigh () {
        if (this.userInfo.level === 0) {
          return '70vh';
        } else {
          return '61vh'
        }
      }
    },
    created () {
      this.userInfo = JSON.parse(localStorage.userInfo);
      if (this.userInfo.level === 0) {
        this.getProfessionByAdmin();
      } else if (this.userInfo.level === 1) {
        this.getTimetableByTeacher();
      } else {
        this.getTimetableByStudent();
      }
    },
    mounted () {
      this.table = this.$refs['time_table'];
      this.search();
    }
  }

</script>
