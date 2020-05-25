<template>
  <div style="background-color: white;">
    <el-tag
      class="tag"
      :class="{tagClick: $route.name === tab.name}"
      :style="{width: tab.name==='主页'?'62px':''}"
      ref="tag"
      color="white"
      @click="tabClick(tab)"
      @close="tabRemove(tab)"
      v-for="(tab,index) in tabsName" :key="index" :closable="tab.name !== '主页'">
      <span>{{tab.name}}</span>
    </el-tag>
  </div>
</template>

<script>
  export default {
    name: "Tabs",
    data () {
      return {
        tabsName: [{name: '主页', path: '/dashboard'}],
      }
    },
    methods: {
      // 删除标签
      tabRemove (item) {
        if (item.name !== '主页') {
          this.tabsName = this.tabsName.filter(data => {
            return data.name !== item.name
          });
        // 跳转到前一个标签
        let path = this.tabsName[this.tabsName.length - 1];
        this.$router.push(path);
      }
      },
      // 点击标签头时
      tabClick (item) {
        this.$router.push(item.path);
      },
    },
    computed: {
      routeName () {
        return this.$route.name
      },
    },
    watch: {
      routeName () {
        let routeInfo = {
          name: '',
          path: ''
        };
        let flag = this.tabsName.some(data => {
          return data.name === this.routeName
        });
        // 如果不重复，则添加标签
        if(!flag) {
          routeInfo = {
            name: this.$route.name,
            path: this.$route.path
          };
          this.tabsName.push(routeInfo)
        }
      },
    }
  }
</script>

<style scoped>
  /* .tag 为默认样式  .tagClick 为点击后样式*/
 .tag {
   margin: 10px 0 1px 12px;
   border: #ebebeb 1px solid;
   text-align: center;
   border-radius: 7px 7px 0 0;
   cursor: pointer;
   background-color: #FBFBFB !important;
 }
 .tag span {
   display: inline-block;
   position: relative;
   transition: 0.4s;
 }
 /deep/ .tag:hover {
   background-color: white !important;
 }
 /deep/ .tag:active {
   transform: scale(0.9);
 }

  .tagClick {
    background-color: #409eff !important;
    color: white !important;
  }
 /deep/ .tagClick .el-tag__close {
   color: white !important;
 }
 /*覆盖 .tag:hover 样式*/
 /deep/ .tagClick:hover {
   opacity: 0.8;
   background-color: #409eff !important;
 }
</style>
