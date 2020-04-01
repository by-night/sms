<template>
    <el-row>
      <el-col :span="5">
        <el-card style="margin: 10px 0 10px 10px;padding: 15px 10px 10px 10px;height: 78vh">
          <div style="height: 78vh">
            <el-scrollbar style="height:100%;" wrapStyle="overflow-x:hidden;" viewStyle="">
              <el-tree :data="treeData" :props="defaultProps" @node-click="nodeClick"></el-tree>
            </el-scrollbar>
          </div>
        </el-card>
      </el-col>
      <el-col :span="19">
        <el-card style="margin: 10px;padding: 15px 10px 10px 10px">
          <el-row style="margin-bottom: 10px">
            <!--<el-button type="primary" size="small" @click="addMethod">新增</el-button>-->
            <!--<el-button type="danger" size="small" @click="deleteSelect">删除</el-button>-->
            <el-input style="float:right;width:300px;" size="small" v-model="searchValue.code" placeholder="请输入账号或姓名" clearable @keyup.enter.native="filterData" maxlength="15">
              <el-button slot="append" @click="filterData" type="primary">过滤</el-button>
            </el-input>
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
          <VmAccount ref="account_model" @search="search"></VmAccount>
        </el-card>
      </el-col>
    </el-row>
</template>

<script>
  import VmAccount from './model/account-model'
  import VmBaseTable from '../../base/base-table'
  export default {
    name: "account",
    components: {
      VmBaseTable, VmAccount
    },
    data () {
      return {
        lastNode: '',
        treeData: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        right: true,
        table: null,
        searchValue: {
          $limit: 10,
          $offset: 0,
          code: '',
          profession: '',
          grade: ''
        },
        tableHigh: '66vh',
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
      getTree () {
        this.axiosHelper.get(
          '/api/mis/user/getTree'
        ).then(response => {
          this.treeData = response.data;
        }).catch(error => {
          this.$message.error({
            message: '失败'
          }, error)
        })
      },
      search() {
        if (this.lastNode !== '') {
          switch (this.lastNode) {
            case "学生":
              this.getStudentList(this.searchValue);
              break;
            case "教师":
              this.getTeacherList(this.searchValue);
              break;
            case "管理员":
              this.getAdminList(this.searchValue);
              break;
          }
        }
      },
      nodeClick (data, node) {
        if (data.children === undefined) {
          this.searchValue.code = '';
          this.searchValue.profession = node.parent.data.label || '';
          // 获取最后一个节点
          this.lastNode = data.label !== '学生' ? data.label: node.parent.parent.data.label;
          switch (this.lastNode) {
            case "学生":
              this.searchValue.grade = data.label;
              this.getStudentList(this.searchValue);
              break;
            case "教师":
              this.getTeacherList(this.searchValue);
              break;
            case "管理员":
              this.getAdminList(this.searchValue);
              break;
          }
        }
      },
      getStudentList (obj) {
        this.axiosHelper.get(
          '/api/mis/user/student/getStudentList',
          {params: obj}
        ).then(response => {
          this.dataTable = response.data.items;
          this.table.total = response.data.totalCount
        }).catch(error => {
          this.$message.error({
            message: '失败'
          }, error)
        })
      },
      getTeacherList (obj) {
        this.axiosHelper.get(
          '/api/mis/user/teacher/getTeacherList',
          {params: obj}
        ).then(response => {
          this.dataTable = response.data.items;
          this.table.total = response.data.totalCount
        }).catch(error => {
          this.$message.error({
            message: '失败'
          }, error)
        })
      },
      getAdminList (obj) {
        this.axiosHelper.get(
          '/api/mis/user/admin/getAdminList',
          {params: obj}
        ).then(response => {
          this.dataTable = response.data.items;
          this.table.total = response.data.totalCount
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
      filterData() {
        this.searchValue.$offset = 0;
        // 跳转到第一页
        if (this.$refs['account_table'] !== undefined) {
          this.$refs['account_table'].currentPageToOne();
        }
        this.search()
      },
      // addMethod() {
      //   this.search();
      //   let type = 'add';
      //   let params = {
      //     type,
      //     identity: this.lastNode
      //   };
      //   this.$refs['account_model'].init(params);
      // },
      editMethod(row) {
        let type = 'edit';
        let params = {
          type,
          row,
          identity: this.lastNode
        };
        this.$refs['account_model'].init(params);
      },
      select(selection) {
        this.delBtn = selection.length <= 0;
      },
      deleteSingle (obj) {
        let ids = [obj.id];
        this.deleteTable(ids);
      },
      // deleteSelect () {
      //   let ids = this.table.getIds();
      //   if (ids.length > 0) {
      //     this.deleteTable(ids);
      //   }
      // },
      deleteTable(ids) {
        let _this = this;
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.lastNode === '学生') {
            let src = '/api/mis/user/student/';
            this.deleteMethod(src, ids, _this);
          } else if (this.lastNode === '教师') {
            let src = '/api/mis/user/teacher/';
            this.deleteMethod(src, ids, _this);
          }
        }).catch(() => {
          this.$message.info({
            message: '已取消删除'
          });
        });
      },
      deleteMethod(src, params, _this) {
        _this.axiosHelper.delete(
          src + params
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
      }
    },
    mounted () {
      this.table = this.$refs['account_table'];
      // this.search();
      this.getTree();
    }
  }

</script>
