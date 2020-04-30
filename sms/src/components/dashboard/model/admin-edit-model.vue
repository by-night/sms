<template>
  <el-dialog v-dialogDrag :title="title" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="800px">
    <el-form ref="form" :rules="rules" :model="form" label-width="90px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="编号：" :label-width="formLabelWidth">
            <el-input v-model="form.id" maxlength="15" disabled clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名：" prop="realName" :label-width="formLabelWidth">
            <el-input v-model="form.realName" maxlength="15" clearable></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="学校：" prop="school" :label-width="formLabelWidth">
            <el-input v-model="form.school" disabled maxlength="15" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别：" :label-width="formLabelWidth">
            <el-select v-model="form.sex" style="width: 100%">
              <el-option v-for="item in sexArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="电子邮箱：" prop="email" :label-width="formLabelWidth">
            <el-input v-model="form.email" maxlength="30" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机：" prop="phone" :label-width="formLabelWidth">
            <el-input v-model="form.phone" maxlength="15" clearable></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer">
      <el-button type="primary" @click="click('form')" size="small">确定</el-button>
      <el-button @click="cancel" size="small">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "editInfo-model",
    data() {
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
        formLabelWidth: '100px',
        dialog: false,
        title: '修改信息',
        sexArr: [{
          value: 0,
          label: '男'
        }, {
          value: 1,
          label: '女'
        }],
        form: {
          username: '',
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
      init (obj) {
        this.form = obj;
        this.dialog = true;
      },
      click (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.editMethod();
          } else {
            return false;
          }
        })
      },
      cancel () {
        this.dialog = false;
      },
      editMethod () {
        this.axiosHelper.put(
          '/api/sms/user/student', this.form).then(() => {
          this.$message.success({
            message: '修改成功'
          });
          this.$store.commit('SAVE_USERINFO', this.form);
          this.$emit('refresh');
          this.dialog = false;
        }).catch(() => {
          this.$message.error({
            message: '修改失败'
          });
          this.dialog = false;
        });
      }
    }
  }
</script>

<style scoped>

</style>
