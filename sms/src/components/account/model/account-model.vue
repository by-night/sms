<template>
  <el-dialog v-dialogDrag title="修改" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="500px">
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item label="用户名：" prop="username" :label-width="formLabelWidth">
        <el-input v-model="form.username" maxlength="15" clearable></el-input>
      </el-form-item>
      <el-form-item label="新密码：" prop="password" :label-width="formLabelWidth">
        <el-input type="password" show-password v-model="form.password" maxlength="15" clearable></el-input>
      </el-form-item>
      <el-form-item label="真实姓名：" prop="realName" :label-width="formLabelWidth">
        <el-input v-model="form.realName" maxlength="15" clearable></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button type="primary" @click="click('form')" size="small">确定</el-button>
      <el-button @click="cancel" size="small">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "student-model",
    data () {
      return {
        formLabelWidth: '100px',
        dialog: false,
        identity: '',
        type: '',
        form: {
          username: '',
          password: '',
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
          realName: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
          ],
        }
      }
    },
    methods: {
      init (obj) {
        this.clearForm();
        this.identity = obj.identity;
        this.type = obj.type;
        this.form = obj.row;
        this.dialog = true;
      },
      clearForm () {
        this.type = '';
        this.form = {
          username: '',
          password: '',
          realName: ''
        }
      },
      click (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.identity === '学生') {
              let src = '/api/sms/user/student';
              this.clickMethod(src);
            } else if (this.identity === '教师') {
              let src = '/api/sms/user/teacher';
              this.clickMethod(src);
            } else if (this.identity === '管理员') {
              let src = '/api/sms/user/admin';
              this.clickMethod(src);
            }
          } else {
            return false;
          }
        })
      },
      clickMethod (src) {
        this.editMethod(src);
      },
      editMethod (src) {
        this.axiosHelper.put(
          src, this.form).then(() => {
          this.$message.success({
            message: '修改成功'
          });
          this.dialog = false;
          this.$emit('search')
        }).catch(() => {
          this.$message.error({
            message: '修改失败'
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
