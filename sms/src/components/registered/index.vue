<template>
  <div>
    <div class="card">
      <div class="header">———  注册  ———</div>
      <el-card style="padding: 10px 20px">
        <registered ref="registered_form"></registered>
      </el-card>
      <div>
        <el-button type="primary" @click="click('rulesForm')" style="width: 300px;margin:20px 0 0 40px">立即注册</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  import registered from './registered'
  export default {
    name: "index",
    components: {
      registered
    },
    methods: {
      click (rulesForm) {
        this.$refs.registered_form.registered(rulesForm);
      }
    },
  }
</script>

<style scoped>

</style>
