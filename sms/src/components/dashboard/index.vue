<template>
  <div>
    <studentBoard v-if="userInfo.level === 2"></studentBoard>
    <teacherBoard v-if="userInfo.level === 1"></teacherBoard>
    <admin-board v-if="userInfo.level === 0"></admin-board>
  </div>
</template>

<script>
import studentBoard from './student-dashboard'
import teacherBoard from './teacher-dashboard'
import adminBoard from './admin-dashboard'
export default {
  name: "index",
  components: {
    studentBoard, teacherBoard, adminBoard
  },
  data () {
    return {
      userInfo: {}
    }
  },
  created () {
    this.userInfo = JSON.parse(localStorage.userInfo);
  }
}
</script>

<style scoped>
</style>
