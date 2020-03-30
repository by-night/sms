<template>
  <el-dialog v-dialogDrag title="任课信息" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="800px">
    <el-button type="primary" size="small" @click="add" style="margin-bottom: 10px">新增</el-button>
    <VmBaseTable
      :setTableHigh="true"
      ref="teacher_table"
      :data="dataTable"
      :columns="dataColumns"
      showIndex
      noPage
      :tableHigh="tableHigh"
    ></VmBaseTable>
    <div slot="footer">
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
            minWidth: '80',
            render: (h, params) => {
              if (this.showInput === params.row.id) {
                return h('el-input', {
                  props: {
                    value: params.row.profession,
                    size: 'small',
                  },
                  on: {
                    input(value) {
                      params.row.profession = value;
                    },
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
            minWidth: '70',
            render: (h, params) => {
              if (this.showInput === params.row.id) {
                return h('el-input', {
                  props: {
                    value: params.row.grade,
                    size: 'small',
                  },
                  on: {
                    input(value) {
                      params.row.grade = value;
                    },
                  }
                })
              } else {
                return h('div', {}, params.row.grade)
              }
            }
          } , {
            label: '课程',
            prop: 'name',
            style: 'center',
            minWidth: '80',
            render: (h, params) => {
              if (this.showInput === params.row.id) {
                return h('el-input', {
                  props: {
                    value: params.row.name,
                    size: 'small',
                  },
                  on: {
                    input(value) {
                      params.row.name = value;
                    },
                  }
                })
              } else {
                return h('div', {}, params.row.name)
              }
            }
          }, {
            label: '操作',
            style: 'center',
            minWidth: '150',
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
        this.teacherInfo = data;
        this.dialog = true;
        this.getInfo(data.id);
      },
      getInfo (id) {
        this.axiosHelper.get('/api/sms/teacher/course/getCourseListById/' + id).then(
          response => {
            this.dataTable = response.data;
          })
      },
      click () {
        const flag = this.dataTable.every(data => {
          return data.name !== '' && data.profession !== '' && data.grade !== ''
        });
        if (flag) {
          this.axiosHelper.post('/api/sms/teacher/course', this.dataTable).then(
            () => {
              this.$message.success({
                message: '任课信息录入成功'
              });
              this.dialog = false;
            });
        } else {
          this.$message.warning({
            message: '存在未填项'
          });
        }
      },
      cancel () {
        this.dialog = false;
      },
      editMethod (data) {
        this.showInput = this.showInput ? '' : data.id;
      },
      deleteSingle (data) {
        if (data.id === -1) {
          console.log(12)
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
        const flag = this.dataTable.every(data => {
          return data.name !== '' && data.profession !== '' && data.grade !== ''
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
            index: ''
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
      }
    }
  }
</script>

<style scoped>

</style>
