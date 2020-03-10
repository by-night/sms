<template>
  <div style="background-color: white;">
    <el-tag class="tag" :style="{width: tab.name==='主页'?'62px':''}" ref="tag" color="white" @click="tabClick(tab)" @close="tabRemove(tab)" v-for="(tab,index) in tabsName" :key="index" :closable="tab.name !== '主页'">
      <span>{{tab.name}}</span>
    </el-tag>
  </div>
</template>

<script>
  export default {
    name: "tabs",
    data () {
      return {
        tabsName: [{name: '主页', path: '/dashboard'}]
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
        this.tabState(item);
        this.$router.push(item.path)
      },
      tabState (item) {
        // 选中时颜色
        // for (let i = 0; i < this.tabsName.length; i++) {
        //   // 已点击的标签
        //   let doc = document.getElementsByClassName('tag')[i];
        //   // 已点击的标签关闭按钮
        //   let tag = document.getElementsByClassName('el-tag__close')[i];
        //   if (this.tabsName[i].name === item.name) {
        //     doc.style.backgroundColor = '#409eff';
        //     doc.style.opacity = '0.8';
        //     doc.style.color = 'white';
        //     tag.style.color = 'white'
        //   } else {
        //     doc.style.backgroundColor = 'white';
        //     doc.style.color = '#409eff';
        //     tag.style.color = '#409eff'
        //   }
        // }
      }
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
        this.tabState(this.$route);
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
        } else {
          return true
        }
      },
    }
  }
</script>

<style scoped>
 .tag {
   margin: 10px 0 1px 12px;
   border: #ebebeb 1px solid;
   text-align: center;
 }
 /deep/ .tag:hover {
   cursor: pointer;
   color: #409eff !important;
   background-color: #ecf5ff !important;
 }
 /deep/ .tag:active {
   transform: scale(0.95);
 }
</style>
