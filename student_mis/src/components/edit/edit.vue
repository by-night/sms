<template>
  <div>
    <el-button @click="click">点击</el-button>
    <el-dialog :visible.sync="dialog">
      <div  v-for="item in arr" :key="item" style="display: inline-block">
        <div class="circle">
          <span style="font-size: 20px">{{item.word}}</span>
        </div>
        <div style="display: inline-block">
          <i :class="item.right" style="font-size: 40px"></i>
        </div>
       </div>
      <div style="display: inline-block">
        <el-button circle @click="add" style="font-size: 38px;border: 3px #64666a solid">
          <i class="el-icon-plus"></i>
        </el-button>
      </div>
    </el-dialog>
    <add ref="add_model"></add>
  </div>
</template>

<script>
  import add from './add-application-model'
    export default {
        name: "edit",
        data () {
          return {
            data: [{id: 1, label: 'sa', },{id: 2, label: 'sa', },{id: 3, label: 'sa', },{id: 4, label: 'sa', }],
            right: '',
            arr: [],
            dialog: false
          }
        },
        methods: {
          click () {
            let type = 'add';
            let params = {
              type: type
            };
            this.$refs['add_model'].init(params);
          },
          add () {
            if(this.arr.length > 3) {
              let obj = {
                word: '1',
                right: ''
              };
              this.arr.push(obj)
            } else {
              let obj = {
                word: 'a',
                right: 'el-icon-right'
              }
              this.arr.push(obj)
            }
            }
        },
        components: {
          add
        }
    }
</script>

<style scoped>
  #app{
    padding: 0;
  }
  .circle {
    width: 60px;
    height: 60px;
    border: 3px #64666a solid;
    border-radius: 80px;
    line-height: 60px;
    text-align: center;
    display: inline-block;
  }
</style>
