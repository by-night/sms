<template>
  <div style="background-color: white;margin-bottom: 10px;">
    <el-tag class="tag" color="white" @click="tabClick(tab)" @close="tabRemove(tab)" v-for="(tab,index) in tabsName" :key="index" closable >
      {{tab.name}}
    </el-tag>
  </div>
</template>

<script>
  export default {
    name: "tabs",
    data () {
      return {
        tabsName: [{name: '第一', path: '/list'}]
      }
    },
    methods: {
      // 删除标签
      tabRemove (item) {
        this.tabsName = this.tabsName.filter(data => {
          return data.name !== item.name
        })
      },
      // 点击标签头时
      tabClick (item) {
        this.tabState(item)
        this.$router.push(item.path)
      },
      tabState (item) {
        for (let i = 0; i < this.tabsName.length; i++) {
          // // 已点击的标签
          // let doc = document.getElementsByClassName('tag')[i];
          // // 已点击的标签关闭按钮
          // let tag = document.getElementsByClassName('el-tag__close')[i];
          // if (this.tabsName[i].name === item.name) {
          //   doc.style.backgroundColor = '#409eff';
          //   doc.style.opacity = '0.8';
          //   doc.style.color = 'white';
          //   tag.style.color = 'white'
          // } else {
          //   doc.style.backgroundColor = 'white';
          //   doc.style.color = '#409eff';
          //   tag.style.color = '#409eff'
          // }
        }
      }
    },
    computed: {
      routeName () {
        return this.$route.name
      }
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
      }
    }
  }
</script>

<style scoped>
 .tag {
   cursor: pointer;
   margin: 15px 0 -2px 12px;
   border: #ebebeb 1px solid;
 }
</style>
