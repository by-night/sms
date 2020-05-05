<template>
  <el-dialog v-dialogDrag title="任课信息" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="800px">
    <el-button type="primary" size="small" @click="add" style="margin-bottom: 10px" v-if="userInfo.level === 0">新增</el-button>
    <VmBaseTable
      :setTableHigh="true"
      ref="teacher_table"
      :data="dataTable"
      :columns="dataColumns"
      showIndex
      noPage
      :tableHigh="tableHigh"
    ></VmBaseTable>
    <div slot="footer" v-if="userInfo.level === 0">
      <el-button type="primary" @click="click" size="small">确定</el-button>
      <el-button @click="cancel" size="small">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import VmBaseTable from '../../../base/base-table'
export default {
  name: "courseInfo-model",
  components: {
    VmBaseTable
  },
    data () {
      return {
        doneNum: 0,
        userInfo: {},
        courseArr: [],
        classArr: [],
        gradeArr: [],
        termArr: [{
          label: '上',
          value: 1
        },{
          label: '下',
          value: 2
        }],
        professionArr: [],
        teacherInfo: {},
        showInput: '',
        tableHigh: '40vh',
        dialog: false,
        dataTable: [],
        dataColumns: [
          {
            label: '专业',
            prop: 'profession',
            style: 'center',
            minWidth: '100',
            render: (h, params) => {
              if (this.showInput === params.row.id) {
                return this.getSelect(h, params.row.profession, (value) => {
                  this.$set(params.row, 'profession', value);
                }, this.professionArr, 'small', false, (value) => {
                  params.row.grade = '';
                  params.row.name = '';
                  this.getCourse(value, params.row.term);

                  // 直接接口返回对应的数据会好一点
                  for(let arr of this.classArr) {
                    if (arr.label === value) {
                      this.gradeArr = arr.children.map(item => {
                        return {label: item.label, value: item.label}
                      });
                      break;
                    }
                  }
                })
              } else {
                return h('div', {}, params.row.profession)
              }
            }
          } , {
            label: '班级',
            prop: 'grade',
            style: 'center',
            minWidth: '60',
            render: (h, params) => {
              if (this.showInput === params.row.id) {
                return this.getSelect(h, params.row.grade, (value) => {
                  this.$set(params.row, 'grade', value);
                }, this.gradeArr, 'small')
              } else {
                return h('div', {}, params.row.grade)
              }
            }
          } , {
            label: '学期',
            prop: 'term',
            style: 'center',
            minWidth: '50',
            render: (h, params) => {
              if (this.showInput === params.row.id) {
                return this.getSelect(h, params.row.term, (value) => {
                  this.$set(params.row, 'term', value);
                }, this.termArr, 'small', false, (value) => {
                  params.row.name = '';
                  let profession = params.row.profession;
                  if (profession !== '') {
                    this.getCourse(params.row.profession, value);
                  }
                })
              } else {
                let label = '';
                for (let i = 0; i < this.termArr.length; i++) {
                  if (params.row.term.toString() === this.termArr[i].value.toString()) {
                    label = this.termArr[i].label
                  }
                }
                return h('div', {}, label)
              }
            }
          } , {
            label: '课程',
            prop: 'courseId',
            style: 'center',
            minWidth: '100',
            render: (h, params) => {
              if (this.showInput === params.row.id) {
                return this.getSelect(h, params.row.name, (value) => {
                  this.$set(params.row, 'courseId', value);
                }, this.courseArr, 'small', false, (value) => {
                  const obj = this.courseArr.find(data => {
                    return data.value === value
                  });
                  params.row.name = obj.label;
                })
              } else {
                return h('div', {}, params.row.name)
              }
            }
          }, {
            label: '操作',
            style: 'center',
            minWidth: '100',
            render: (h, params) => {
              let btns = [];
              if (this.showInput === params.row.id) {
                btns.push(this.getOpBtn(h, '确定', '', () => {
                  this.editMethod(params.row);
                }));
                btns.push(this.getOpBtn(h, '删除', 'danger', () => {
                  this.deleteSingle(params.row)
                }));
              } else {
                btns.push(this.getOpBtn(h, '编辑', 'primary', () => {
                  this.editMethod(params.row);
                }));
                btns.push(this.getOpBtn(h, '删除', 'danger', () => {
                  this.deleteSingle(params.row)
                }));
              }
              return h('div', btns)
            }
          }
        ],
      }
    },
    methods: {
      init (data) {
        this.showInput = '';
        this.courseArr = [];
        this.classArr = [];
        this.gradeArr = [];
        this.getClass();
        this.getProfession();
        this.teacherInfo = data;
        this.dialog = true;
        this.getInfo(data.id);
      },
      getClass () {
        this.axiosHelper.get(
          '/api/sms/user/getTree'
        ).then(response => {
          this.classArr = response.data[0].children;
        }).catch(error => {
          this.$message.error({
            message: '失败'
          }, error)
        })
      },
      getCourse (profession, term) {
        let obj = {
          profession,
          term
        };
        this.axiosHelper.get('/api/sms/course/getCourseByMap',  {params: obj}).then(
          response => {
            let course = response.data;
            this.courseArr = course.map(data => {
              return {label: data.name, value: data.id}
            });
          });
      },
      getInfo (id) {
        this.axiosHelper.get('/api/sms/teacher/course/getCourseListById/' + id).then(
          response => {
            this.dataTable = response.data;
          })
      },
      click () {
        const flag = this.dataTable.every(data => {
          return data.courseId !== '' && data.profession !== '' && data.grade !== ''
        });
        if (flag) {
          ++this.doneNum;
          if (this.doneNum === 1) {
            this.clickMethod();
          }
        } else {
          this.$message.warning({
            message: '存在未填项'
          });
        }
      },
      clickMethod () {
        this.axiosHelper.post('/api/sms/teacher/course', this.dataTable).then(
          () => {
            this.doneNum = 0;
            this.$message.success({
              message: '任课信息录入成功'
            });
            this.dialog = false;
          }).catch(() => {
          this.doneNum = 0;
          this.$message.error({
            message: '任课信息录入失败'
          });
          this.dialog = false;
        });
      },
      cancel () {
        this.dialog = false;
      },
      editMethod (data) {
        this.showInput = this.showInput ? '' : data.id;
        let flag = data.profession !== '';
        // 点击编辑时，请求课程数据
        if (this.showInput !== '' && flag) {
          this.getCourse(data.profession, data.term);
          for(let arr of this.classArr) {
            if (arr.label === data.profession) {
              this.gradeArr = arr.children.map(item => {
                return {label: item.label, value: item.label}
              });
              break;
            }
          }
        }
      },
      deleteSingle (data) {
        if (data.id === -1) {
          this.dataTable = this.dataTable.filter(item => {
            return item.index !== data.index
          })
        } else {
          this.axiosHelper.delete('/api/sms/teacher/course/' + data.id).then(
            () => {
              this.$message.success({
                message: '任课信息删除成功'
              });
              this.getInfo(this.teacherInfo.id)
            });
        }
      },
      add () {
        this.courseArr = [];
        this.gradeArr = [];
        const flag = this.dataTable.every(data => {
          return data.name !== '' && data.profession !== '' && data.grade !== '' && data.term !== ''
        });
        if (flag) {
          this.showInput = -1;
          let obj = {
            id: -1,
            teacherId: this.teacherInfo.id,
            username: this.teacherInfo.username,
            name: '',
            profession: '',
            grade: '',
            index: '',
            term: 1
          };
          this.dataTable.push(obj);
          for (let i = 0; i < this.dataTable.length; i++) {
            this.dataTable[i].index = i;
          }
        } else {
          this.$message.warning({
            message: '存在未填项'
          });
        }
      },
      getProfession () {
        this.axiosHelper.get('/api/sms/profession/getProfessionList').then(response => {
          let data = response.data;
          this.professionArr = data.map(item => {
            return {label: item.name,value: item.name}
          });
        })
      }
    },
    created () {
      this.userInfo = JSON.parse(localStorage.userInfo);
      if (this.userInfo.level === 1) {
        this.dataColumns = this.dataColumns.filter(data => {
          return data.label !== '操作'
        })
      }
    }
  }
</script>

<style scoped>

</style>
