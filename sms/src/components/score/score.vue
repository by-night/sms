<template>
  <div>
    <el-card class="cardStyle">
      <el-button @click="change" size="small" style="margin-bottom: 15px">筛选</el-button>
      <el-button @click="batchMethod" type="info" size="small" :disabled="dataTable.length <= 0" style="margin-bottom: 15px">批量编辑</el-button>
      <el-button type="primary" size="small" :disabled="dataTable.length <= 0" style="margin-bottom: 15px">成绩录入</el-button>

      <transition>
        <div v-if="show" style="background-color: white;height: 90px;;box-sizing: border-box">
          <el-form ref="form" :model="form" label-width="80px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="专业：" prop="profession">
                  <el-input v-model="form.profession" maxlength="15" clearable style="width: 90%"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="班级：" prop="grade">
                  <el-input v-model="form.grade" maxlength="15" clearable style="width: 90%"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="课程名：" prop="professional">
                  <el-input v-model="form.courseName" maxlength="15" clearable style="width: 90%"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div style="float: right;margin: -5px 40px 10px 0">
            <el-button type="primary" @click="clickAndClose" size="small" style="margin-right: 10px">确定并关闭</el-button>
            <el-button type="primary" @click="click" size="small" style="margin-right: 10px">确定</el-button>
            <el-button size="small" @click="change">取消</el-button>
          </div>
        </div>
      </transition>
      <VmBaseTable
        :setTableHigh="true"
        ref="score_table"
        :data="dataTable"
        :columns="dataColumns"
        @page-change="pageChange"
        showIndex
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
      let that = this;
      return {
        batch: false,
        showInput: '',
        userInfo: '',
        show: false,
        form: {
          profession: '',
          grade: '',
          term: '',
        },
        table: null,
        searchValue: {
          $limit: 10,
          $offset: 0,
          code: ''
        },
        selectValue: [],
        dataTable: [],
        dataColumns: [
          {
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
                    size: 'small'
                  },
                  on: {
                    input(value) {
                      value = value.replace(/[^\d]/g, '') && value.slice(0, 5);
                      params.row.scoreByUser = value>params.row.score?params.row.score:value;
                    },
                    blur() {
                      params.row.creditsByUser = params.row.scoreByUser>=params.row.score*0.6?params.row.credits:0;
                      params.row.pointByUser = params.row.scoreByUser>0?(params.row.scoreByUser/10-5).toFixed(1):0;
                      that.showInput = '';
                    }
                  }
                })
              } else {
                return h('div', {}, params.row.scoreByUser)
              }
            }
          }, {
            label: '学分',
            prop: 'creditsByUser',
            style: 'center',
            minWidth: '60',
            render (h, params) {
              if (params.row.creditsByUser % 1 === 0) {
                return h('div', {}, `${params.row.creditsByUser}.0`)
              } else {
                return h('div', {}, params.row.creditsByUser)
              }
            }
          }, {
            label: '绩点',
            prop: 'pointByUser',
            style: 'center',
            minWidth: '60',
            render (h, params) {
              if (params.row.pointByUser % 1 === 0) {
                return h('div', {}, `${params.row.pointByUser}.0`)
              } else {
                return h('div', {}, params.row.pointByUser)
              }
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
            prop: 'professional',
            style: 'center',
            minWidth: '120',
          }, {
            label: '届时',
            prop: 'year',
            style: 'center',
            minWidth: '80',
            render (h, params) {
              return h('div', {}, `${params.row.year}届`)
            }
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
      change () {
        this.form = {
          profession: '',
          grade: '',
          term: '',
        };
        this.show = !this.show
      },
      clickAndClose () {
        this.click();
        this.change();
      },
      click () {
        let userInfo = JSON.parse(localStorage.userinfo);
        let obj = {
          $limit: 10,
          $offset: 0,
          profession: this.form.profession,
          grade: this.form.grade,
          username: userInfo.username,
          courseName: this.form.courseName
        };
        this.axiosHelper.get(
          '/api/sms/score/getCourseList',
          {params: obj}).then(response => {
            let obj1 = {
              scoreByUser: '',
              creditsByUser: 0,
              pointByUser: 0
            };
            let data = response.data.items;
            this.dataTable = data.map(item => {
              return {...item, ...obj1}
            });
            this.table.total = response.data.totalCount;
        }).catch(error => {
          this.$message.error({
            message: '失败'
          }, error)
        })
      },
      pageChange(page) {
        this.searchValue.$limit = page.limit;
        this.searchValue.$offset = page.offset;
        this.search();
      },
      editMethod(data) {
        this.showInput = this.showInput === data.no ? '' : data.no ;
        this.batch = false;
      },
      batchMethod () {
        this.batch = true;
      }
    },
    mounted () {
      this.table = this.$refs['score_table'];
    },
    computed: {
      tableHigh () {
        return this.show ? '53vh' : '66vh'
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
