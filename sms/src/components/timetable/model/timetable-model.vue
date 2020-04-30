<template>
  <el-dialog v-dialogDrag title="详细信息" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="350px">
    <div>
      <el-row class="info">
        <el-col :span="6" class="title">课程：</el-col>
        <el-col :span="18" class="content">{{form.courseName}}</el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">学分：</el-col>
        <el-col :span="18" class="content">{{courseInfo.credits}}</el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">类型：</el-col>
        <el-col :span="18" class="content">{{courseInfo.type === 1 ? '必修' : '选修'}}</el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">周数：</el-col>
        <el-col :span="18" class="content">{{form.courseName}}</el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">课时：</el-col>
        <el-col :span="18" class="content">{{courseInfo.number}}</el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">任课老师：</el-col>
        <el-col :span="18" class="content">{{courseInfo.realName}}</el-col>
      </el-row>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "timetable-model",
  data () {
    return {
      dialog: false,
      userInfo: {},
      form: {
        profession: '',
        grade: '',
        courseName: '',
        teacherId: ''
      },
      courseInfo: {}
    }
  },
  methods: {
    init (data) {
      this.dialog = true;
      this.form.courseName = data;
      if (this.userInfo.level === 1) {
        this.getCourseInfoByTeacher();
      } else {
        this.getCourseInfoByStudent();
      }
    },
    getCourseInfoByTeacher () {
      this.form.teacherId = this.userInfo.id;
      this.axiosHelper.get('/api/sms/teacher/course/getCourseInfo',
        {params: this.form}).then(response => {
        this.courseInfo = response.data;
      })
    },
    getCourseInfoByStudent () {
      this.form.profession = this.userInfo.profession;
      this.form.grade = this.userInfo.grade;
      this.axiosHelper.get('/api/sms/teacher/course/getCourseInfo',
        {params: this.form}).then(response => {
        this.courseInfo = response.data;
      })
    }
  },
  created () {
    this.userInfo = JSON.parse(localStorage.userInfo);
  },
}
</script>

<style scoped>
  .info {
    margin-bottom: 10px
  }
  .title {
    text-align: center;
    font-weight: bold;
  }
  .content {
    text-align: center;
  }
</style>
