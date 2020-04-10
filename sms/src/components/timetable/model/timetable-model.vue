<template>
  <el-dialog v-dialogDrag title="详细信息" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="350px">
    <div>
      <p class="info">课程：&#12288{{form.courseName}}</p>
      <p class="info">学分：&#12288{{courseInfo.credits}}</p>
      <p class="info">类型：&#12288{{courseInfo.type === 1 ? '必修' : '选修'}}</p>
      <p class="info">课时：&#12288{{courseInfo.number}}</p>
      <p class="info">任课老师：&#12288{{courseInfo.realName}}</p>
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
</style>
