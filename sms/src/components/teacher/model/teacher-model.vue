<template>
  <el-dialog v-dialogDrag :title="title" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="800px">
    <el-form ref="form" :model="form" :rules="rules">
      <el-row>
        <el-form-item label="姓名：" prop="realName" :label-width="formLabelWidth">
          <el-input v-model="form.realName" maxlength="15" clearable></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="学校：" prop="school" :label-width="formLabelWidth">
            <el-input v-model="form.school" maxlength="15" clearable></el-input>
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
      <el-button @click="cancel" size="small" >取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "student-model",
    data () {
      const validEmail = (rule, value, callback) => {
        const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        if (!value) {
          return callback(new Error('电子邮箱不能为空'))
        } else {
          if (mailReg.test(value)) {
            callback()
          } else {
            callback(new Error('请输入正确的邮箱格式'))
          }
        }
      };
      const validPhone = (rule, value, callback) => {
        const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
        if (!value) {
          return callback(new Error('手机号码不能为空'))
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
        doneNum: 0,
        formLabelWidth: '100px',
        dialog: false,
        type: '',
        title: '',
        form: {
          id: '',
          grade: '',
          realName: '',
          sex: '',
          school: '福建工程学院',
          admissionTime: new Date().getFullYear(),
          email: '',
          profession: '',
          phone: '',
        },
        sexArr: [{
          value: 0,
          label: '男'
        }, {
          value: 1,
          label: '女'
        }],
        rules: {
          grade: [
            { required: true, message: '班级不能为空', trigger: 'blur' },
          ],
          realName: [
            {required: true, message: '姓名不能为空', trigger: 'blur'},
          ],
          school: [
            {required: true, message: '学校不能为空', trigger: 'blur'},
          ],
          profession: [
            {required: true, message: '专业不能为空', trigger: 'blur'},
          ],
          email: [
            {required: true, validator: validEmail, trigger: 'blur'},
          ],
          phone: [
            {required: true, validator: validPhone, trigger: 'blur'},
          ],
          admissionTime: [
            {required: true, message: '入学时间不能为空', trigger: 'blur'},
          ],
        }
      }
    },
    methods: {
      init (obj) {
        this.clearForm();
        this.type = obj.type;
        if (obj.type === 'add') {
          this.title = '新增';
        } else if (obj.type === 'edit') {
          this.title = '修改';
          this.form = obj.row
        }
        this.dialog = true;
      },
      clearForm () {
        this.type = '';
        this.form = {
          id: '',
          grade: '',
          realName: '',
          sex: 0,
          school: '福建工程学院',
          admissionTime: new Date().getFullYear(),
          email: '',
          profession: '',
          phone: '',
        };
        if(this.$refs['form'] !== undefined) {
          this.$refs['form'].clearValidate();
        }
      },
      click (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.type === 'add') {
              this.addMethod();
            } else if (this.type === 'edit') {
              this.editMethod();
            }
          } else {
            return false;
          }
        })
      },
      addMethod () {
        ++this.doneNum;
        if (this.doneNum === 1) {
          this.add();
        }
      },
      add () {
        this.axiosHelper.post(
          '/api/sms/user/teacher', this.form).then(() => {
          this.doneNum = 0;
          this.$message.success({
            message: '新增成功'
          });
          this.dialog = false;
          this.$emit('search')
        }).catch(() => {
          this.doneNum = 0;
          this.$message.error({
            message: '新增失败'
          });
          this.dialog = false;
        });
      },
      editMethod () {
        this.axiosHelper.put(
          '/api/sms/user/teacher', this.form).then(() => {
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
