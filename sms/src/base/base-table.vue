<template>
  <div>
    <el-row>
      <el-col :span="24">
        <template v-if="setTableHigh">
          <el-table
            ref = "refTable"
            :data="data"
            :header-cell-style="headerStyle"
            :row-style="rowStyle"
            :cell-style="{fontSize:'14px'}"
            :highlight-current-row="rowLight"
            :stripe="!stripe"
            @sort-change = "sortChange"
            @selection-change="selectChange"
            @row-click="rowClick"
            style="width:100%;"
            :height="tableHigh">
            <el-table-column v-if="showCheck===true" type="selection" width="50px" align="center" :selectable="selectable">
            </el-table-column>
            <el-table-column v-if="showIndex===true" type="index" label="序号"  width="60" align="center">
            </el-table-column>
            <el-table-column v-for="(col, index) in columns"
                             :key="index"
                             :min-width="col.minWidth"
                             :show-overflow-tooltip="overflow"
                             :prop="col.prop"
                             :align="col.style"
                             :type="col.type"
                             :fixed="col.fixed"
                             :sortable="col.sortable"
                             :label="col.label">
              <template slot-scope="scope">
                <ex-slot v-if="col.render"
                         :render="col.render"
                         :row="scope.row"
                         :index="scope.$index"
                         :column="col">
                </ex-slot>
                <span v-else>
                                {{scope.row[col.prop]}}
                                </span>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <template v-else>
          <el-table
            ref = "refTable"
            :data="data"
            row-key="id"
            :stripe="!stripe"
            default-expand-all
            :tree-props="{children: 'children'}"
            @sort-change = "sortChange"
            @selection-change="selectChange"
            :highlight-current-row="rowLight"
            @row-click="rowClick"
            style="width:100%;"
            :header-cell-style="headerStyle"
            :row-style="rowStyle"
            class="table-base-table">
            <el-table-column v-if="showCheck===true" type="selection" style="width: 50px;" align="center" :selectable="selectable">
            </el-table-column>
            <el-table-column v-if="showIndex===true" type="index" label="序号" width="60" align="center">
            </el-table-column>
            <el-table-column v-for="(col, index) in columns"
                             :key="index"
                             :min-width="col.minWidth"
                             :show-overflow-tooltip="overflow"
                             :prop="col.prop"
                             :align="col.style"
                             :type="col.type"
                             :fixed="col.fixed"
                             :sortable="col.sortable"
                             :label="col.label">
              <template slot-scope="scope">
                <ex-slot v-if="col.render"
                         :render="col.render"
                         :row="scope.row"
                         :index="scope.$index"
                         :column="col">
                </ex-slot>
                <span v-else>
                                {{scope.row[col.prop]}}
                                </span>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <el-row v-show="noPage===false" type="flex" justify="space-between" class="footer">
          <el-col>
            <div class="page">
              <el-pagination
                background
                :small="small"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :highlight-current-row="rowLight"
                :current-page="currentPage"
                :page-sizes="page_sizes"
                :layout="layout"
                :page-size="pageSize"
                :total="total">
              </el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  var exSlot = {
    functional: true,
    props: {
      row: Object,
      render: Function,
      index: Number,
      column: {
        type: Object,
        default: null
      }
    },
    render: (h, data) => {
      const params = {
        row: data.props.row,
        index: data.props.index
      };
      if (data.props.column) params.column = data.props.column
      return data.props.render(h, params)
    }
  };
  export default {
    name: "base-table",
    components: {
      'ex-slot': exSlot
    },
    props: {
      // 表头样式
      headerStyle: {
        type: Object,
        default:  () => {
          return {background:'#EDF1F4'};
        }
      },
      // 表格行的样式
      rowStyle: {
        type: Object,
        default: () => {
          return {};
        }
      },
      stripe: {
        type: Boolean,
        default: false
      },
      // 表格选中框是否可勾选
      selectable: {
        type: Function,
        default: () => {
          return true
        }
      },
      // 表格数据
      data: {
        type: Array,
        default: () => {
          return []
        }
      },
      children:{
        type: Array,
        default: () => {
          return []
        }
      },
      // 表头数据
      columns: {
        type: Array,
        default: () => {
          return []
        }
      },
      // 超出表格隐藏，默认不隐藏
      overflow: {
        type: Boolean,
        default: false
      },
      // 是否分页，默认分页
      noPage: {
        type: Boolean,
        default: false
      },
      // 是否显示checkbox
      showCheck: {
        type: Boolean,
        default: false
      },
      showIndex: {
        type: Boolean,
        default: false
      },
      // 一页显示多少条
      page_sizes: {
        type: Array,
        default: () => {
          return [10, 20, 50, 100]
        }
      },
      setTableHigh: {
        type: Boolean,
        default: false
      },
      tableHigh: {
        type: String,
        default: '70vh'
      },
      tableHeightClass:{
        type: String,
        default: 'default-height'
      },
      layout: {
        type: String,
        default: 'total, sizes, prev, pager, next, jumper'
      },
      small: {
        type: Boolean,
        default: false
      },
      rowLight: {
        type: Boolean,
        default () {
          return false;
        }
      }
    },
    computed: {
      // 计算属性
    },
    methods: {
      handleSizeChange (size) {
        this.pageSize = size;
        this.limit = this.pageSize;
        this.handleCurrentChange(1);
      },
      handleCurrentChange (current) {
        this.currentPage = current;
        this.offset = (this.currentPage - 1) * this.pageSize;
        this.$emit('original-page-change', {'currentPage': this.currentPage, 'pageSize': this.pageSize});
        this.$emit('page-change', {'offset': this.offset, 'limit': this.limit})
      },
      rowClick (row, column, cell, event) {
        // 对外暴露方法与返回值<回调方法>
        this.$emit('on-row-click', {'row': row, 'column': column, 'cell': cell, 'event': event})
      },
      getSelectedOfProp (prop) {
        this.selectPropArr = [];
        for (let i = 0; i < this.selectionArr.length; i++) {
          this.selectPropArr.push(this.selectionArr[i][prop])
        }
        if (this.selectPropArr.length === 0) {
          this.$message.warning({
            message: '没有选择任何数据，请先选择！！'
          });
        }
        return this.selectPropArr
      },
      getSelectedData () {
        if (this.selectionArr.length === 0) {
          this.$message.warning({
            message: '没有选择任何数据，请先选择！！'
          });
        }
        return this.selectionArr
      },
      getIds () {
        return this.getSelectedOfProp('id');
      },
      selectChange (selection) {
        this.selectionArr = selection;
        this.$emit('on-select-change', selection)
      },
      sortChange (column, prop, order) {
        this.$emit('sort-change', column, prop, order)
      },
      toggleSelection (rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.refTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.refTable.clearSelection();
        }
      },
      resize (h) {
        this.$set(this.$refs.refTable, "height", h);
      },
      // 默认选中的表格数据
      getSelection (row, selected) {
        this.$refs['refTable'].toggleRowSelection(row, selected)
      },
      currentPageToOne () {
        this.currentPage = 1;
      },
    },
    data () {
      return {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        offset: 0,
        limit: 0,
        ids: [],
        selectArr: [],
        selectPropArr: [],
        selectionArr: []
      }
    },
    mounted () {
      this.limit = this.pageSize;
      this.offset = (this.currentPage - 1) * this.pageSize;
      this.$nextTick(()=>{
        setTimeout(()=>{
          this.$refs['refTable'].doLayout();
        })
      })
    },
    watch: {
      data () {
        // 解决表格错位问题
        this.$refs.refTable.$el.style.width = '99.99%'
      }
    }
  }
</script>

<style scoped>
  .default-height{
    height: 70vh;
  }
  .auto-height{

  }
  .page {
    margin-top: 10px;
    display: flex;
    float: left;
    align-items: center;
  }
  .el-table {
    border-top: 1px solid #E7E7E7;
    border-right: 1px solid #E7E7E7;
    border-radius: 4px;
    width: 100%;
    margin-top: 5px;
  }
  /* 穿透渲染 */
  .el-table /deep/ td {
    border-bottom:  1px solid #E7E7E7;
    border-left:  1px solid #E7E7E7;
  }
  .el-table /deep/ th {
    border-bottom:  1px solid #E7E7E7;
    border-left:  1px solid #E7E7E7;
  }
  body /deep/ .el-table /deep/ colgroup.gutter{
    display: table-cell!important;
  }
  .el-table /deep/ tr:hover {
    background: #d2d2d2;
  }
  /deep/.el-table__fixed-right {
    /*此处的important表示优先于element.style*/
    height:auto !important;
    /*改为自动高度后，设置与父容器的底部距离，则高度会动态改变*/
    bottom:17px;
  }
  /deep/.el-table__fixed{
    /*此处的important表示优先于element.style*/
    height:auto !important;
    /*改为自动高度后，设置与父容器的底部距离，则高度会动态改变*/
    bottom:17px;
  }
  /deep/ .el-table th, .el-table tr {
    font-family: Serif !important;
  }
</style>

