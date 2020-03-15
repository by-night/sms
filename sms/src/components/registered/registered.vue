<template>
  <el-form ref="rulesForm" :rules="rules" :model="form" label-width="90px">
    <el-form-item label="用户名:" prop="username">
      <el-input v-model="form.username" clearable maxlength="20"></el-input>
    </el-form-item>
    <el-form-item label="登录密码:" prop="password">
      <el-input v-model="form.password" type="password" maxlength="15" show-password clearable></el-input>
    </el-form-item>
    <el-form-item label="确认密码:" prop="passwordAgain">
      <el-input v-model="form.passwordAgain" type="password" maxlength="15" show-password clearable></el-input>
    </el-form-item>
    <el-form-item label="姓名:" prop="realName">
      <el-input v-model="form.realName" maxlength="15" clearable></el-input>
    </el-form-item>
    <el-form-item label="性别:">
      <el-select v-model="form.sex" style="width: 100%">
        <el-option v-for="item in sexArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="学校:">
      <el-input v-model="form.school" maxlength="15" clearable></el-input>
    </el-form-item>
    <el-form-item label="入学时间:">
      <el-input v-model="form.admission_time" maxlength="15" clearable></el-input>
    </el-form-item>
    <el-form-item label="电子邮箱:" prop="email">
      <el-input v-model="form.email" maxlength="30" clearable></el-input>
    </el-form-item>
    <el-form-item label="专业:">
      <el-input v-model="form.profession" maxlength="15" clearable></el-input>
    </el-form-item>
    <el-form-item label="联系方式:" prop="phone">
      <el-input v-model="form.phone" maxlength="15" clearable></el-input>
    </el-form-item>
    <el-form-item label="验证码:" prop="code">
      <el-input v-model="form.code" maxlength="30" clearable></el-input>
    </el-form-item>
</el-form>
</template>

<script>
    export default {
        name: "registered",
        data() {
          const validPassword=(rule, value, callback) => {
            if(value !== this.form.password){
              callback(new Error('两次密码不一致'))
            }else{
              callback()
            }
          };
          let validEmail = (rule, value, callback) => {
            const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            if (!value) {
              return callback(new Error('邮箱不能为空'))
            } else {
              if (mailReg.test(value)) {
                callback()
              } else {
                callback(new Error('请输入正确的邮箱格式'))
              }
            }
          };
          let validPhone = (rule, value, callback) => {
            const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
            if (!value) {
              return callback(new Error('电话号码不能为空'))
            } else {
              if (!Number.isInteger(+value)) {
                callback(new Error('请输入数字值'))
              } else {
                if (phoneReg.test(value)) {
                  callback()
                } else {
                  callback(new Error('电话号码格式不正确'))
                }
              }
            }
          };
          return {
            sexArr: [{
              value: 0,
              label: '男'
            }, {
              value: 1,
              label: '女'
            }],
            form: {
              username: '',
              password: '',
              passwordAgain: '',
              realName: '',
              sex: '',
              school: '',
              admission_time: '',
              email: '',
              profession: '',
              phone: '',
              code: ''
            },
            rules: {
              username: [
                { required: true, message: '用户名不能为空', trigger: 'blur' },
                { max: 15, message: '用户名不得超过15位', trigger: 'blur' }
              ],
              password: [
                {required: true, message: '密码不能为空', trigger: 'blur'},
                { min: 6, max: 15, message: '密码长度必须为6-15位'}
              ],
              passwordAgain: [
                {required: true, validator: validPassword, trigger: 'blur'},
                { min: 6, max: 15, message: '密码长度必须为6-15位'}
              ],
              realName: [
                {required: true, message: '请输入姓名', trigger: 'blur'}
              ],
              email: [
                {required: true, validator: validEmail, trigger: 'blur'},
              ],
              phone: [
                {required: true, validator: validPhone, trigger: 'blur'},
              ],
              code: [
                {required: true, message: '请输入验证码', trigger: 'blur'}
              ]
            }
          }
        },
      methods: {
        registered (rulesForm) {
          this.$refs[rulesForm].validate((valid) => {
            if (valid) {
              this.axiosHelper.post('/api/user/mis', this.form).then(
                response => {
                  if(response.status === 200) {
                    this.$message.success({
                      message: '注册成功'
                    });
                    this.$router.push('/home');
                  } else {
                    this.$message.error({
                      message: '注册失败'
                    })
                  }
              })
            }
          })
        }
      },
      mounted() {
          // this.$nextTick(() => {
          //   this.$refs.rulesForm.resetFields()
          // })
      }
    }
</script>
<style>
  html, body, #app, .login {
    height: 99%;
    padding: 0;
    margin: 0;
  }
</style>
<style scoped>
</style>
