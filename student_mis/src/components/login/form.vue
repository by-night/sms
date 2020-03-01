<template>
  <div style="margin-top: 40px">
    <el-form ref="form" :model="form" label-width="20px" class="form">
      <!--账号-->
      <el-form-item>
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
      <el-form-item>
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
      <el-form-item>
        <el-link :underline="false" style="float: right">忘记密码？</el-link>
      </el-form-item>
      <el-form-item style="margin-left: 160px">
        <el-button @click="registered">注册</el-button>
        <el-button type="primary" @click="login('form')">登陆</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    export default {
        name: "form",
        props: ['level'],
        data() {
            return {
                form: {
                    username: '',
                    password: '',
                    level: 1
                }
            }
        },
        methods: {
            login (formName) {
              if(this.form.username === '') {
                this.$message({
                  message: '账号不能为空',
                  type: 'warning'
                })
              } else if (this.form.password === '') {
                this.$message({
                  message: '密码不能为空',
                  type: 'warning'
                })
              } else {
                this.checkLogin(formName)
              }
            },
            checkLogin (formName) {
              this.form.level = this.level;
              let _this = this;
              this.$refs[formName].validate(valid => {
                if (valid) {
                  _this.axios.get('/api/mis/user/login', {params: this.form}).then(
                    response => {
                      console.log(response)
                      let data = response.data;
                      if(Object.keys(data).length > 0) {
                        this.$message({
                          message: '登录成功',
                          type: 'success'
                        });
                        // 将数据存入state
                       _this.$store.commit('SAVE_USERINFO', response.data);
                       // 跳转到主页
                        _this.$router.push('/dashboard')
                      } else {
                        this.$message({
                          message: '登录失败，请检查用户名或密码',
                          type: 'error'
                        })
                      }
                    }).catch(() => {
                      this.$message({
                        message: '登录失败',
                        type: 'error'
                      })
                  });
                }
              })
            },
            registered() {
              this.$router.push("/registered")
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
