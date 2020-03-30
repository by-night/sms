<template>
  <el-dialog v-dialogDrag :title="title" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="800px">
    <el-form ref="form" :rules="rules" :model="form" label-width="90px">
      <el-form-item label="课程名:" prop="name">
        <el-input v-model="form.name" clearable maxlength="30"></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="学分:" prop="credits">
            <el-input v-model.number="form.credits" maxlength="15" clearable oninput="value=value.replace(/[^\d.]/g,'')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="绩点:" prop="point">
            <el-input v-model="form.point" maxlength="15" clearable oninput="value=value.replace(/[^\d.]/g,'')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="满分:" prop="score">
            <el-input v-model="form.score" maxlength="15" clearable oninput="value=value.replace(/[^\d]/g,'')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="课时:" prop="number">
            <el-input v-model="form.number" maxlength="15" clearable oninput="value=value.replace(/[^\d]/g,'')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="届时:" prop="year">
            <el-select v-model="form.year" style="width: 100%">
              <el-option v-for="item in yearArr" :key="item" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="学期:" prop="term">
            <el-select v-model="form.term" style="width: 100%">
              <el-option v-for="item in termArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="专业:" prop="profession">
            <el-input v-model="form.profession" maxlength="30" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
        <el-form-item label="类型:" prop="type">
          <el-select v-model="form.type" style="width: 100%">
            <el-option v-for="item in typeArr" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer">
      <el-button type="primary" @click="click('form')">确定</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: "course-model",
    data() {
      return {
        title: '',
        termArr: [{
          value: 1,
          label: '上学期'
        }, {
          value: 2,
          label: '下学期'
        }],
        yearArr: [],
        formLabelWidth: '100px',
        dialog: false,
        typeArr: [{
          value: 1,
          label: '必修'
        }, {
          value: 2,
          label: '选修'
        }],
        form: {
          name: '',
          credits: '',
          point: '',
          score: '',
          number: '',
          year: '',
          type: 1,
          term: 1,
          profession: ''
        },
        rules: {
          name: [
            { required: true, message: '课程名不能为空', trigger: 'blur' },
          ],
          credits: [
            { required: true, message: '学分不能为空', trigger: 'blur' },
          ],
          point: [
            { required: true, message: '绩点不能为空', trigger: 'blur' },
          ],
          score: [
            { required: true, message: '满分项不能为空', trigger: 'blur' },
          ],
          number: [
            { required: true, message: '课时不能为空', trigger: 'blur' },
          ],
          profession: [
            { required: true, message: '专业名不能为空', trigger: 'blur' },
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
        let year = new Date().getFullYear();
        this.form = {
          name: '',
          credits: '',
          point: '',
          score: '',
          number: '',
          year: year,
          type: 1,
          term: 1,
          profession: ''
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
        this.axiosHelper.post(
          '/api/sms/course', this.form).then(() => {
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
      editMethod () {
        this.axiosHelper.put(
          '/api/sms/course', this.form).then(() => {
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
      },
      registered (rulesForm) {
        this.$refs[rulesForm].validate((valid) => {
          if (valid) {
            this.axiosHelper.post('/api/student/mis', this.form).then(
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
    created () {
      let today = new Date();
      let year = today.getFullYear();
      for (let i = 2010; i < year + 2; i++) {
        this.yearArr.push(i);
      }
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
