<template>
  <div>
    <el-card class="cardStyle" v-loading="loading">
      <el-button @click="change" size="small" style="margin-bottom: 15px">筛选</el-button>
      <el-button @click="batchMethod" type="info" size="small" :disabled="dataTable.length <= 0" style="margin-bottom: 15px" v-if="userInfo.level !== 2">批量编辑</el-button>
      <el-button @click="addEntry" type="primary" size="small" :disabled="dataTable.length <= 0" style="margin-bottom: 15px" v-if="userInfo.level !== 2">成绩录入</el-button>
      <el-button @click="exportMethod" type="success" :disabled="dataTable.length <= 0" size="small" style="margin-bottom: 15px">导出</el-button>
      <!--过滤框-->
      <el-collapse-transition>
        <div v-if="show" style="background-color: white;height: 100px;;box-sizing: border-box">
          <el-form ref="form" :model="form" label-width="80px">
            <el-row v-if="userInfo.level === 2">
              <el-col :span="8">
                <el-form-item label="学年：">
                  <el-select v-model="form.year" style="width: 90%">
                    <el-option v-for="item in yearArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="学期：">
                  <el-select v-model="form.term" style="width: 90%">
                    <el-option v-for="item in termArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row v-else>
              <el-col :span="8">
                <el-form-item label="专业：" prop="profession">
                  <el-select v-model="form.professionObj" style="width: 90%" @change="professionChange" value-key="profession">
                    <el-option v-for="item in classArr" :key="item.profession" :label="item.profession" :value="item"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="班级：" prop="grade">
                  <el-select v-model="form.grade" style="width: 90%">
                    <el-option v-for="item in gradeArr" :key="item" :label="item" :value="item"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="课程名：" prop="profession">
                  <el-select v-model="form.courseName" style="width: 90%">
                    <el-option v-for="item in courseArr" :key="item" :label="item" :value="item"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div style="margin: -5px 0px 10px 77%">
            <el-button type="primary" @click="clickAndClose" size="small" style="margin-right: 10px">确定并关闭</el-button>
            <el-button type="primary" @click="clickSure" size="small" style="margin-right: 10px">确定</el-button>
            <el-button size="small" @click="cancel">取消</el-button>
          </div>
        </div>
      </el-collapse-transition>
      <VmBaseTable
        :setTableHigh="true"
        ref="score_table"
        :data="dataTable"
        :columns="dataColumns"
        @page-change="pageChange"
        @on-select-change="select"
        showCheck
        :tableHigh="tableHigh"
      ></VmBaseTable>
    </el-card>
  </div>
</template>

<script>
  import VmBaseTable from '../../base/base-table'
  export default {
    name: "score",
    components: {
      VmBaseTable
    },
    data () {
      return {
        loading: false,
        courseArr: [],
        classArr: [],
        professionArr: [],
        gradeArr: [],
        professionObj: {},
        batch: false,
        showInput: '',
        userInfo: '',
        show: false,
        form: {
          profession: '',
          grade: '',
          term: '',
          courseName: '',
          year: '',
        },
        yearArr: [],
        termArr: [{
          label: '上',
          value: 1
        },{
          label: '下',
          value: 2
        }],
        table: null,
        searchValue: {
          $limit: 10,
          $offset: 0,
        },
        selectValue: [],
        selection: [],
        dataTable: [],
        dataColumns: [
          {
            label: '学号',
            prop: 'no',
            style: 'center',
            minWidth: '70',
          }, {
            label: '姓名',
            prop: 'realName',
            style: 'center',
            minWidth: '70',
          }, {
            label: '课程名',
            prop: 'name',
            style: 'center',
            minWidth: '120'
          }, {
            label: '得分',
            prop: 'scoreByUser',
            style: 'center',
            minWidth: '60',
            render: (h, params) => {
              if (this.batch || this.showInput === params.row.no) {
                return h('el-input', {
                  props: {
                    value: params.row.scoreByUser,
                    size: 'small',
                  },
                  on: {
                    input(value) {
                      value = value.replace(/[^\d]/g, '') && value.slice(0, 5);
                      params.row.scoreByUser = value>params.row.score?params.row.score:value;
                    },
                  }
                })
              } else {
                let colorValue = params.row.scoreByUser>59?'':'red';
                let scoreValue = params.row.scoreByUser === null ? '未录入' : params.row.scoreByUser;
                return h('div', {style: {color: colorValue}}, scoreValue)
              }
            }
          }, {
            label: '学分',
            prop: 'creditsByUser',
            style: 'center',
            minWidth: '60',
            render (h, params) {
              let colorValue = params.row.creditsByUser==='0.00'?'red':'';
              let creditsValue = params.row.creditsByUser === null ? 0 : params.row.creditsByUser;
              return h('div', {style: {color: colorValue}}, creditsValue)
            }
          }, {
            label: '绩点',
            prop: 'pointByUser',
            style: 'center',
            minWidth: '60',
            render (h, params) {
              let colorValue = params.row.pointByUser==='0.00'?'red':'';
              let pointValue = params.row.pointByUser === null ? 0 : params.row.pointByUser;
              return h('div', {style: {color: colorValue}}, pointValue)
            }
          }, {
            label: '类型',
            prop: 'type',
            style: 'center',
            minWidth: '70',
            render (h, params) {
              if (params.row.type === 1) {
                return h('div', {}, '必修')
              } else if (params.row.type === 2) {
                return h('div', {}, '选修')
              }
            }
          }, {
            label: '专业',
            prop: 'profession',
            style: 'center',
            minWidth: '120',
          }, {
            label: '班级',
            prop: 'grade',
            style: 'center',
            minWidth: '80',
          }, {
            label: '操作',
            style: 'center',
            prop: 'id',
            minWidth: '120',
            render: (h, params) => {
              let btns = [];
              if (this.showInput === params.row.no) {
                btns.push(this.getOpBtn(h, '取消', '', () => {
                  this.editMethod(params.row);
                }));
              } else {
                btns.push(this.getOpBtn(h, '编辑', 'primary', () => {
                  this.editMethod(params.row);
                }));
              }
              return h('div', btns)
            }
          }
        ],
      }
    },
    methods : {
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
        this.form.year = 2016;
        let month = new Date().getMonth()+1;
        if (month > 2 && month < 6) {
          // 上学期
          this.form.term = this.termArr[0].value;
        } else {
          // 下学期
          this.form.term = this.termArr[1].value;
        }
        this.click(this.searchValue);
      },
      cancel () {
        this.form = {
          profession: '',
          grade: '',
          term: '',
          courseName: ''
        };
        this.show = !this.show;
      },
      change () {
        this.show = !this.show
      },
      clickSure (data) {
        this.searchValue.$offset = 0;
        // 跳转到第一页
        if (this.$refs['score_table'] !== undefined) {
          this.$refs['score_table'].currentPageToOne();
        }
        this.click(data);
      },
      clickAndClose () {
        this.clickSure(this.searchValue);
        this.show = !this.show
      },
      professionChange (data) {
        this.form.grade = '';
        this.form.courseName = '';
        this.form.profession = data.profession;
        this.gradeArr = data.grade;
        this.courseArr = data.course;
      },
      clickMethod (obj) {
        this.axiosHelper.get(
          '/api/sms/score/getCourseList',
          {params: obj}).then(response => {
          this.dataTable = response.data.items;
          this.table.total = response.data.totalCount;
        }).catch(error => {
          this.$message.error({
            message: '获取成绩失败'
          }, error)
        })
      },
      click (page) {
        let userInfo = JSON.parse(localStorage.userInfo);
        let obj = {
          $limit: page.$limit,
          $offset: page.$offset,
          profession: this.form.profession,
          grade: this.form.grade,
          username: userInfo.level === 1 ? userInfo.username : '',
          courseName: this.form.courseName,
          studentName: userInfo.level === 2 ? userInfo.username : '',
          level: userInfo.level,
          year: this.form.year,
          term: this.form.term
        };
        this.clickMethod(obj);
      },
      pageChange(page) {
        this.searchValue.$limit = page.limit;
        this.searchValue.$offset = page.offset;
        this.click(this.searchValue)
      },
      editMethod(data) {
        this.showInput = this.showInput === data.no ? '' : data.no ;
        this.batch = false;
      },
      batchMethod () {
        this.batch = true;
      },
      select (selection) {
        this.selection = selection;
      },
      addEntry () {
        let flag = this.selection.every(data => {
          return data.scoreByUser !== null && data.scoreByUser !== ''
        });
        if (this.selection.length > 0) {
          if (flag) {
            this.loading = true;
            this.axiosHelper.post('/api/sms/score', this.selection).then(() => {
              this.$message.success({
                message: '录入成绩成功'
              });
              this.loading = false;
              this.click(this.searchValue);
              this.batch = false;
              this.showInput = false;
            }).catch(() => {
              this.$message.warning({
                message: '录入成绩失败'
              });
              this.loading = false;
            })
          } else {
            this.$message.warning({
              message: '存在未录入成绩人员'
            })
          }
        } else {
          this.$message.warning({
            message: '请先选择人员'
          })
        }
      },
      exportMethod () {
        let userInfo = JSON.parse(localStorage.userInfo);
        let obj = {
          profession: this.form.profession,
          grade: this.form.grade,
          username: userInfo.level !== 2 ? userInfo.username : '',
          courseName: this.form.courseName,
          studentName: userInfo.level === 2 ? userInfo.username : '',
          level: userInfo.level
        };
        this.axiosHelper.get('/api/sms/score/export', {params: obj}).then(response => {
          const list = response.data;  //把data里的tableData存到list
          list.map(data => {
            if (data.type === 1) {
              data.type = '必修'
            } else if (data.type === 2) {
              data.type = '选修'
            }
            return data
          });
          this.exportExcel(list, userInfo);
        });
      },
      //导出的方法
      exportExcel(list, userInfo) {
        require.ensure([], () => {
          const { export_json_to_excel } = require('../../vendor/Export2Excel');
          const tHeader = [];
          const filterVal = [];
          for(let data of this.dataColumns) {
            if (data.label === "操作") {
              continue;
            }
            tHeader.push(data.label);
            filterVal.push(data.prop);
          }
          const data = this.formatJson(filterVal, list);
          const title = userInfo.level === 2 ? `${userInfo.realName}成绩列表` : '学生成绩列表';
          export_json_to_excel(tHeader, data, title);
        })
      },
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      getProfessionByAdmin () {
        this.axiosHelper.get(
          '/api/sms/teacher/course/getProfessionInfoByAdmin').then(response => {
          this.classArr = response.data;
        }).catch(error => {
          this.$message.error({
            message: '获取班级列表'
          }, error)
        })
      },
      getProfessionByTeacher () {
        this.axiosHelper.get(
          '/api/sms/teacher/course/getProfessionInfoByTeacher/' + this.userInfo.id
        ).then(response => {
          this.classArr = response.data;
        }).catch(error => {
          this.$message.error({
            message: '获取班级列表'
          }, error)
        })
      },
    },
    mounted () {
      this.table = this.$refs['score_table'];
      if (this.userInfo.level !== 0) {
        if (this.userInfo.level === 2) {
          this.dataColumns = this.dataColumns.filter(data => {
            return (data.label !== '操作' && data.label !== '学号' && data.label !== '姓名')
          });
          this.getYear();
        }
      }
      this.click(this.searchValue);
    },
    created () {
      this.userInfo = JSON.parse(localStorage.userInfo);
      if (this.userInfo.level === 0) {
        this.getProfessionByAdmin();
      } else if (this.userInfo.level === 1) {
        this.getProfessionByTeacher();
      }
    },
    computed: {
      tableHigh () {
        if (this.userInfo.level === 2) {
          return '72vh'
        } else {
          return this.show ? '52vh' : '66vh'
        }
      }
    }
  }
</script>

<style scoped>
  .cardStyle {
    height: 78vh;
    margin: 10px;
    padding: 15px 10px 10px 10px;
  }
</style>
