<template>
  <div style="margin-top: 40px">
    <el-form :rules="rules" ref="form" :model="form" label-width="20px" class="form">
      <!--账号-->
      <el-form-item prop="username">
        <el-row>
          <el-col :span="2">
            <i class="el-icon-user"></i>
          </el-col>
          <el-col :span="22">
            <el-input v-model="form.username" maxlength="15" @keyup.enter.native="checkLogin('form')" clearable autofocus
                      placeholder="请输入账号"></el-input>
          </el-col>
        </el-row>
      </el-form-item>
      <!--密码-->
      <el-form-item prop="password">
        <el-row>
          <el-col :span="2">
            <a class="el-icon-lock"></a>
          </el-col>
          <el-col :span="22">
            <el-input type="password" @keyup.enter.native="checkLogin('form')" v-model="form.password" maxlength="30"
                      clearable placeholder="请输入密码" show-password></el-input>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item style="margin-left: 150px">
        <el-button type="primary" @click="checkLogin('form')">登陆</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    export default {
        name: "form",
        data() {
            return {
                form: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入账号', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 5, max: 20, message: '长度必须在5-20之间', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            checkLogin (formName) {
              this.$refs[formName].validate(valid => {
                if (valid) {
                  let _this = this
                  this.axios.get('/api/mis/user/login',
                    {params: this.form}
                  ).then(response => {
                    console.log(response)
                    // // 把当前用户数据存入state
                    // console.log(response)
                    // _this.$store.commit('SAVE_USERINFO', response.data[0])
                    // if (response.data) {
                    //   _this.$message({
                    //     message: '登录成功',
                    //     type: 'success'
                    //   })
                    //   _this.$router.push('/index')
                    // } else {
                    //   _this.$alert('请检查用户名或密码！', '登录失败')
                    // }
                  }).catch(error => {
                    console.log(error)
                  })
                } else {
                  console.log('error')
                }
              })
            },
            reset() {
                this.form = {}
            }
        }
    }
</script>

<style>
  /*登录表单的顶部*/
  .el-tabs--border-card > .el-tabs__header .el-tabs__item {
    height: 60px;
    width: 100px;
    font-size: 22px;
    line-height: 60px;
    font-weight: bold;
    text-align: center;
  }

  /*.el-tabs, .el-tabs__header {*/
  /*border-radius: 18px;*/
  /*}*/
</style>
<style scoped>
  /deep/ .form {
    margin-right: 20px;
  }
</style>
