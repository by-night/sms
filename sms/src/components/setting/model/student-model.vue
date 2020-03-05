<template>
  <el-dialog v-dialogDrag title="新增" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="500px">
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item label="用户名：" prop="username" :label-width="formLabelWidth">
        <el-input v-model="form.username" maxlength="15" clearable></el-input>
      </el-form-item>
      <el-form-item label="新密码：" prop="password" :label-width="formLabelWidth">
        <el-input type="password" show-password v-model="form.password" maxlength="15" clearable></el-input>
      </el-form-item>
      <el-form-item label="确认密码：" prop="passwordAgain" :label-width="formLabelWidth">
        <el-input  type="password" show-password v-model="form.passwordAgain" maxlength="15" clearable></el-input>
      </el-form-item>
      <el-form-item label="真实姓名：" prop="realName" :label-width="formLabelWidth">
        <el-input v-model="form.realName" maxlength="15" clearable></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button type="primary" @click="click">确定</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "student-model",
    data () {
      const validPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('确认密码不能为空'))
        } else {
          if (value !== this.form.passwordAgain) {
            callback(new Error('两次密码不一致'))
          } else {
            callback()
          }
        }
      };
      return {
        formLabelWidth: '100px',
        dialog: false,
        form: {
          username: '',
          password: '',
          passwordAgain: '',
          realName: ''
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
            {required: true, message: '姓名不能为空', trigger: 'blur'},
          ],
        }
      }
    },
    methods: {
      init () {
        this.dialog = true;
      },
      click () {
        this.axiosHelper.post(
          '/api/mis/user/student', this.form).then(() => {
          this.$message.success({
            message: '新增成功'
          });
          this.dialog = false;
          this.$emit('search')
        }).catch(() => {
          this.$message.error({
            message: '新增失败'
          });
          this.dialog = false;
        });
      },
      cancel () {
        this.dialog = false;
      }
    },
  }
</script>

<style scoped>

</style>
