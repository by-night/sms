<template>
  <el-card style="margin: 10px;padding: 10px">
    <el-row style="margin-bottom: 10px">
      <el-button type="primary" size="small" @click="addMethod">新增</el-button>
      <el-button type="danger" size="small" @click="deleteAll">删除</el-button>
    </el-row>
    <VmBaseTable
      :setTableHigh="true"
      @on-select-change="select"
      ref="account_table"
      :data="dataTable"
      :columns="dataColumns"
      @page-change="pageChange"
      showCheck
      showIndex
      :tableHigh="tableHigh"
    ></VmBaseTable>
    <VmStudent ref="student_model" @search="search"></VmStudent>
  </el-card>
</template>

<script>
  import VmStudent from './model/student-model'
  import VmBaseTable from '../../base/base-table'
  export default {
    name: "student",
    components: {
      VmBaseTable, VmStudent
    },
    data () {
      return {
        right: true,
        table: null,
        searchValue: {
          $limit: 10,
          $offset: 0,
          formCode: ''
        },
        tableHigh: '65vh',
        selectValue: [],
        dataTable: [],
        dataColumns: [
          {
            label: '账号',
            prop: 'username',
            style: 'center',
            minWidth: '120'
          }, {
            label: '密码',
            prop: 'password',
            style: 'center',
            minWidth: '120',
          }, {
            label: '真实姓名',
            prop: 'real_name',
            style: 'center',
            minWidth: '100',
          }, {
            label: '操作',
            style: 'center',
            prop: 'id',
            minWidth: '120',
            render: (h, params) => {
              let btns = [];
              btns.push(this.getOpBtn(h, '编辑', 'primary', () => {
                this.editMethod(params.row)
              }));
              btns.push(this.getOpBtn(h, '删除', 'danger', () => {
                this.deleteSingle(params.row)
              }));
              return h('div', btns)
            }
          }
        ],
        delBtn: true
      }
    },
    methods: {
      search () {
        let that = this;
        this.axiosHelper.get(
          '/api/mis/user/getStudentList',
          {params: that.searchValue}
        ).then(response => {
          this.dataTable = response.data.items;
          this.table.total = response.data.totalCount
        }).catch(error => {
          this.$message.error({
            message: '失败'
          }, error)
        })
      },
      pageChange (page) {
        this.searchValue.$limit = page.limit;
        this.searchValue.$offset = page.offset;
        this.search();
      },
      addMethod () {
        this.search();
        let type = 'add';
        let params = {
          type
        };
        this.$refs['student_model'].init(params);
      },
      editMethod (row) {
        let type = 'edit';
        let params = {
          type: type,
          row: row
        };
        this.$refs['form-model'].init(params);
      },
      select (selection) {
        this.delBtn = selection.length <= 0;
      },
      deleteSingle (obj) {
      },
      deleteAll () {
      },
      deleteMethod (params) {
        let _this = this;
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.axiosHelper.delete(
            '/v0.1/project/flow/' + params
          ).then(response => {
            let status = response.status;
            if (status === 200) {
              // 若删除当前页的最后一条时，加载上一页信息
              if((_this.table.total - params.length) % 10 === 0 && _this.searchValue.$offset !== 0) {
                _this.searchValue.$offset -= 10
              }
              _this.$message.success({
                message: '删除成功'
              });
              _this.search()
            }
          }).catch(error => {
            _this.$message.error({
              message: '删除失败'
            }, error)
          })
        }).catch(() => {
          this.$message.info({
            message: '已取消删除'
          });
        });
      },
    },
    mounted () {
      this.table = this.$refs['account_table'];
      this.search();
    }
  }

</script>
