<template>
  <div style="margin-top: 40px;">
    <el-form ref="form" :model="form" label-width="20px" class="form">
      <!--账号-->
      <el-form-item style="margin-top: 60px">
        <el-row>
          <el-col :span="2">
            <i class="el-icon-user"></i>
          </el-col>
          <el-col :span="22">
            <el-dropdown trigger="click" v-if="dealCookie.length > 0">
              <el-input v-model="form.username" maxlength="15" @keyup.enter.native="loginDone('form')"
                style="width: 300px" clearable autofocus placeholder="请输入账号"></el-input>
              <!--用户登录过的账号-->
               <el-dropdown-menu :style="{width: '300px', marginLeft: '50px', height: dealCookie.length < 3 ? `${dealCookie.length*50}px`:'100px'}" slot="dropdown">
                <el-scrollbar style="height:100%;" wrapStyle="overflow-x:hidden;padding-right:12px;" viewStyle="">
                  <el-dropdown-item v-for="cookies in dealCookie" :key="cookies.username" @click.native="clickCookies(cookies)" style="height: 50px">
                    <div>
                      <!--用户账号icon-->
                      <div class="userIcon el-icon-user-solid"></div>
                      <!--显示cookies账号密码-->
                      <div>
                        <div>{{cookies.username}}</div>
                        <div>
                          <div class="circle" v-for="(item, index) in circleArr" :key="index">{{item}}</div>
                        </div>
                      </div>
                      <!--删除账号信息按钮-->
                      <div title="删除账号信息" class="deleteCook el-icon-close" @click.stop="deleteCookies(cookies)"></div>
                    </div>
                   </el-dropdown-item>
                </el-scrollbar>
               </el-dropdown-menu>
            </el-dropdown>

            <el-input v-else v-model="form.username" maxlength="15" @keyup.enter.native="loginDone('form')"
                      style="width: 300px" clearable autofocus placeholder="请输入账号"></el-input>
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
            <el-input type="password" @keyup.enter.native="loginDone('form')" v-model="form.password" maxlength="30"
                      clearable placeholder="请输入密码" show-password></el-input>
          </el-col>
        </el-row>
      </el-form-item>
      <!--&lt;!&ndash;验证码&ndash;&gt;-->
      <!--<el-form-item>-->
        <!--<el-row>-->
          <!--<el-col :span="14">-->
            <!--<el-input type="password" @keyup.enter.native="loginDone('form')" v-model="form.password" maxlength="30"-->
                      <!--clearable placeholder="请输入密码" show-password></el-input>-->
          <!--</el-col>-->
          <!--<el-col :span="8">-->
            <!--<verify></verify>-->
          <!--</el-col>-->
        <!--</el-row>-->
      <!--</el-form-item>-->

      <el-form-item>
        <div style="float: right;margin-top: -20px">
          <el-checkbox v-model="isKeep" style="height: 50px;">记住密码</el-checkbox>
        </div>
      </el-form-item>

      <el-form-item>
        <el-button v-if="loadingBtn === ''" type="primary" @click="login('form')" class="loginMain" :disabled="loginBtn">
          <span>
            登&#12288陆
          </span>
        </el-button>
        <el-button v-else @click="login('form')" type="primary"  class="loginMain" :disabled="loginBtn">
          {{loadingBtn}}
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import verify from '../../common/verify'
  import ElSelectDropdown from "element-ui/packages/select/src/select-dropdown";
    export default {
        name: "Form",
        components: {
          ElSelectDropdown,
          verify
        },
        data() {
            return {
                // 按钮内容
                loadingBtn: '',
                circleArr: ['','','','','',''],
                isKeep: true,
                errNum: 0,
                // 按钮只点击一次
                loginState: 0,
                form: {
                    username: '',
                    password: '',
                    level: 2
                },
                time: '',
                // 是否禁用按钮
                loginBtn: false,
                dealCookie: [],
            }
        },
        methods: {
            clearForm (level) {
              this.form =  {
                username: '',
                password: '',
                level
              };
            },
            clickCookies (data) {
              this.form = {
                username: data.username,
                password: data.password,
                level: data.level
              }
            },
            loginDone (formName) {
              // 登陆按钮可点击时才可enter
              if (!this.loginBtn) {
                this.login(formName);
              }
            },
            login (formName) {
              // 登陆判断
              if(this.form.username === '') {
                this.$message.warning({
                  message: '账号不能为空'
                })
              } else if (this.form.password === '') {
                this.$message.warning({
                  message: '密码不能为空'
                })
              } else {
                this.loginState++;
                if (this.loginState === 1) {
                  this.checkLogin(formName)
                }
              }
            },
            checkLogin (formName) {
              let _this = this;
              this.$refs[formName].validate(valid => {
                if (valid) {
                  this.loadingBtn = '登陆中 ...';
                  this.loginBtn = true;
                  this.$nextTick(() => {
                    _this.axiosHelper.get('/api/sms/user/login', {params: this.form}).then(
                      response => {
                        this.loginBtn = false;
                        this.loginState = 0;
                        let data = response.data;
                        this.click(_this, data);
                      }).catch(() => {
                      this.loadingBtn = '';
                      this.loginBtn = false;
                      this.loginState = 0;
                      this.$message.error({
                        message: '登录失败，请检查用户名或密码'
                      });
                    })
                  })
                }
              })
            },
            click (_this, data) {
              if(Object.keys(data).length > 0) {
                this.loadingBtn = '登陆成功';
                this.$message.success({
                  message: '登录成功'
                });
                _this.errNum = 0;
                // 将数据存入state
                this.$store.commit('SAVE_USERINFO', data);
                let obj = {
                  username: data.username,
                  password: data.password,
                  level: data.level
                };
                obj.password = this.isKeep ? obj.password : '';
                // 设置cookies
                this.$cookies.set(`sms_${data.username}`, obj, 60*60*24*3);
                localStorage.setItem('userToken', data.token);
                localStorage.setItem('refreshToken', data.refreshToken);
                localStorage.setItem('cookiesName', `sms_${data.username}`);
                // 跳转到主页
                _this.$router.push('/dashboard');
              } else {
                this.$message.error({
                  message: '登录失败，请检查用户名或密码'
                });
                this.loadingBtn = '';
                this.errNum++;
                this.errDone();
              }
            },
            errDone () {
              // todo 后端做
              // 错误5次以上禁止5s
              if (this.errNum > 5) {
                this.time = setTimeout(() => {
                  this.errNum = 0;
                  this.loginBtn = false;
                }, 5000);
                this.loginBtn = true;
                this.$message.warning({
                  message: '连续错误5次，请5秒后重试'
                });
              }
            },
            // 获取本项目的所有cookies
            getCookies() {
              this.dealCookie = [];
              // 获取所有cookies
              let cookieArr = this.$cookies.keys();
              // 筛选本项目的cookies的key
              cookieArr.forEach(data => {
                if (data.indexOf('sms_') !== -1) {
                  // 根据key获取cookies的值
                  this.dealCookie.push(this.$cookies.get(data));
                }
              });
              this.dealCookie = this.dealCookie.filter(data => {
                return data.level === this.form.level
              });
            },
            deleteCookies (data) {
              let keyName = `sms_${data.username}`;
              this.$cookies.remove(keyName);
              this.getCookies();
            }
        },
      mounted () {
          this.getCookies();
      },
      created () {
          this.loadingBtn = '';
          // 登录界面时，清除身份信息
          localStorage.removeItem('cookiesName');
          localStorage.removeItem('userInfo');
          localStorage.removeItem('userToken');
          localStorage.removeItem('refreshToken');
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
  /deep/ .loginMain {
    color: white;
    font-size: 18px;
    width: 200px;
    border-radius: 100px;
    margin-left: 70px;
    margin-top: -10px;
  }
  .loginMain span {
     cursor: pointer;
     display: inline-block;
     position: relative;
     transition: 0.4s;
  }
  .loginMain span:after {
    font-size: 28px;
    /*  \00bb: ">>"符号  */
    content: '\00bb';
    position: absolute;
    opacity: 0;
    top: -8px;
    right: -35px;
    transition: 0.4s;
  }
  .loginMain:hover span {
    padding-right: 35px;
  }
  .loginMain:hover span:after {
    opacity: 1;
    right: 0;
  }
  /deep/ .el-tabs {
    border-radius: 8px !important;
  }
  .deleteCook {
    float: right;
    height: 10px;
    width: 10px;
    margin-top: -18px;
  }
  .deleteCook:hover {
    transform: scale(1.2);
    font-weight: bold;
  }
  .circle {
    width: 4px;
    height: 4px;
    border-radius: 50%;
    background-color: gray;
    float: left;
    margin: 1px;
  }
  .userIcon {
    font-size: 18px;
    float: left;
    height: 50px;
    width: 35px;
    line-height: 50px;
  }
</style>
