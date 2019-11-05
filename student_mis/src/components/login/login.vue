<template>
  <div class="login" :style="note">
    <div class="title">
      <h2>
        <i class="el-icon-eleme"></i>学生成绩管理系统
      </h2>
    </div>
    <div class="loginForm">
      <el-tabs type="border-card" v-model="tabName" @tab-click="tabClick">
        <el-tab-pane label="学生" name="student">
          <Form class="flip"></Form>
        </el-tab-pane>
        <el-tab-pane label="教师" name="teacher">
          <Form></Form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
    import Form from './form'

    export default {
        data() {
            return {
                tabName: 'student',
                loading: false,
                note: {
                    backgroundImage: 'url(' + require('../../assets/login.jpg') + ')',
                    backgroundRepeat: 'no-repeat',
                    backgroundSize: '100%'
                },
            }
        },
        methods: {
            tabClick() {
                let flip = document.querySelector('.el-tabs')
                flip.style.transition = '0.3s'
                // 翻转90度
                flip.style.transform = 'rotateY(90deg)'
                setTimeout(() => {
                    flip.style.transition = '0.2s'
                    flip.style.transform = 'rotateY(0)'
                }, 240)
            }
        },
        components: {
            Form
        }
    }
</script>
<style>
  * {
    margin: 0;
    padding: 0;
  }

  html, body, #app, .login {
    height: 100%;
  }
</style>
<style scoped>
  /deep/ .el-tabs {
    /*3d效果*/
    transform-style: preserve-3d;
    -webkit-perspective: 1000px;
    transition: 0.8s
  }

  .loginForm {
    width: 400px;
    margin: 220px 580px;
  }

  .title {
    padding: 10px 10px;
  }
</style>
