<template>
  <el-dialog v-dialogDrag title="详细信息" :visible.sync="dialog" :close-on-click-modal=false append-to-body width="350px">
    <div>
      <el-row class="info">
        <el-col :span="6" class="title">课程：</el-col>
        <el-col :span="18" class="content">{{form.courseName}}</el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">教室：</el-col>
        <el-col :span="18" class="content">
          <el-input v-model="course.room" size="small" clearable v-if="userInfo.level === 0"></el-input>
          <div v-else>{{course.room}}</div>
        </el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">周数：</el-col>
        <el-col :span="8" class="content">
          <el-input-number v-if="userInfo.level === 0" v-model="course.start" controls-position="right" :min="1" :max="30" style="width: 90%;float: left" size="small"></el-input-number>
          <div v-else style="text-align: center">{{course.start}}</div>
        </el-col>
        <el-col :span="2" style="text-align: center"> 至 </el-col>
        <el-col :span="8" style="padding-left: 6px">
          <el-input-number v-if="userInfo.level === 0" v-model="course.end" controls-position="right" :min="1" :max="30" style="width: 90%;float: right"  size="small"></el-input-number>
          <div v-else style="text-align: center">{{course.end}}</div>
        </el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">类型：</el-col>
        <el-col :span="18" class="content">{{courseObj.type === 1 ? '必修' : courseObj.type === 0 ? '选修' : ''}}</el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">学分：</el-col>
        <el-col :span="18" class="content">{{courseObj.credits}}</el-col>
      </el-row>
      <el-row class="info">
        <el-col :span="6" class="title">任课老师：</el-col>
        <el-col :span="18" class="content">{{courseObj.realName}}</el-col>
      </el-row>
    </div>
    <div slot="footer" style="margin-top: -10px" v-if="userInfo.level === 0">
      <el-button type="primary" @click="click" size="small">保存</el-button>
      <el-button @click="cancel" size="small" >取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "timetable-model",
  data () {
    return {
      course: {
        room: '',
        start: 1,
        end: 25,
        courseId: '',
        profession: '',
        grade: ''
      },
      courseObj: {},
      options: [],
      courseArr: [],
      dialog: false,
      userInfo: {},
      form: {
        profession: '',
        grade: '',
        courseName: '',
        teacherId: ''
      },
      propForm: {},
      courseInfo: {}
    }
  },
  methods: {
    init (data, form) {
      this.dialog = true;
      this.courseArr = [];
      this.form.courseName = data;
      this.propForm = form;
      this.course.profession = this.propForm.profession;
      this.course.grade = this.propForm.grade;
      const level = this.userInfo.level;
      switch (level) {
        case 0:
          this.getCourseInfoByAdmin();
          break;
        case 1:
          this.getCourseInfoByTeacher();
          break;
        case 2:
          this.getCourseInfoByStudent();
          break;
      }
    },
    dealCourse (value) {
      const arr = this.courseArr.filter(data => {
        return data.name === value
      });
      this.courseObj = arr[0];
      this.course.courseId = this.courseObj.id;
      this.course.start = this.courseObj.start;
      this.course.end = this.courseObj.end;
      this.course.room = this.courseObj.room;
    },
    click () {
      this.axiosHelper.post('/api/sms/timetable/updateCourseInfo', this.course)
        .then(() => {
          this.$message.success({
            message: '更改课程详情成功'
          })
        }).catch(() => {
        this.$message.error({
          message: '更改课程详情失败'
        })
      });
      this.dialog = false;
    },
    cancel () {
      this.dialog = false;
    },
    getCourseInfoByTeacher () {
      this.form.teacherId = this.userInfo.id;
      this.axiosHelper.get('/api/sms/teacher/course/getCourseInfo',
        {params: this.form}).then(response => {
        this.courseArr.push(response.data);
        this.dealCourse(this.form.courseName);
      })
    },
    getCourseInfoByStudent () {
      this.form.profession = this.userInfo.profession;
      this.form.grade = this.userInfo.grade;
      this.axiosHelper.get('/api/sms/teacher/course/getCourseInfo',
        {params: this.form}).then(response => {
        this.courseArr.push(response.data);
        this.dealCourse(this.form.courseName);
      })
    },
    getCourseInfoByAdmin () {
      const obj = {
        profession: this.propForm.profession,
        grade: this.propForm.grade
      };
      this.axiosHelper.get('/api/sms/course/getCourseByMap',  {params: obj}).then(
        response => {
          this.courseArr = response.data;
          this.dealCourse(this.form.courseName);
        });
    },
  },
  created () {
    this.userInfo = JSON.parse(localStorage.userInfo);
  },
}
</script>

<style scoped>
  .info {
    height: 25px;
    margin-bottom: 12px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .title {
    text-align: center;
    font-weight: bold;
  }
  .content {
    text-align: center;
  }
</style>
