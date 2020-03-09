<template>
  <el-card style="margin: 10px;padding: 10px">
    <el-row style="margin-bottom: 10px">
      <el-button type="primary" size="small" @click="addMethod">新增</el-button>
      <el-button type="danger" size="small" @click="deleteSelect">删除</el-button>
      <el-input style="float:right;width:300px;" size="small" v-model="searchValue.code" placeholder="请输入账号或姓名" clearable @keyup.enter.native="filterData">
        <el-button slot="append" @click="filterData" type="primary">过滤</el-button>
      </el-input>
    </el-row>
    <VmBaseTable
      :setTableHigh="true"
      @on-select-change="select"
      ref="teacher_table"
      :data="dataTable"
      :columns="dataColumns"
      @page-change="pageChange"
      showCheck
      showIndex
      :tableHigh="tableHigh"
    ></VmBaseTable>
    <VmTeacher ref="teacher_model" @search="search"></VmTeacher>
  </el-card>
</template>

<script>
  import VmBaseTable from '../../base/base-table'
  import VmTeacher from './model/teacher-model'
  export default {
    name: "teacher",
    components: {
      VmBaseTable, VmTeacher
    },
    data() {
      return {
        right: true,
        table: null,
        searchValue: {
          $limit: 10,
          $offset: 0,
          code: ''
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
            prop: 'realName',
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
      search() {
        let that = this;
        this.axiosHelper.get(
          '/api/mis/user/teacher/getTeacherList',
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
      filterData() {
        this.searchValue.$offset = 0;
        // 跳转到第一页
        if (this.$refs['teacher_table'] !== undefined) {
          this.$refs['teacher_table'].currentPageToOne();
        }
        this.search()
      },
      pageChange(page) {
        this.searchValue.$limit = page.limit;
        this.searchValue.$offset = page.offset;
        this.search();
      },
      addMethod() {
        this.search();
        let type = 'add';
        let params = {
          type
        };
        this.$refs['teacher_model'].init(params);
      },
      editMethod(row) {
        let type = 'edit';
        let params = {
          type,
          row
        };
        this.$refs['teacher_model'].init(params);
      },
      select(selection) {
        this.delBtn = selection.length <= 0;
      },
      deleteSelect() {
        let ids = this.table.getIds();
        this.deleteTable(ids);
      },
      deleteSingle(obj) {
        let ids = [obj.id];
        this.deleteTable(ids);
      },
      deleteTable(ids) {
        let _this = this;
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteMethod(ids, _this);
        }).catch(() => {
          this.$message.info({
            message: '已取消删除'
          });
        });
      },
      deleteMethod(params, _this) {
        _this.axiosHelper.delete(
          '/api/mis/user/teacher/' + params
        ).then(response => {
          let status = response.status;
          if (status === 200) {
            // 若删除当前页的最后一条时，加载上一页信息
            if ((_this.table.total - params.length) % 10 === 0 && _this.searchValue.$offset !== 0) {
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
      },
    },
    mounted() {
      this.table = this.$refs['teacher_table'];
      this.search();
    }
  }

</script>
