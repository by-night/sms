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
  .header {
    background-color: #94c7fe;
    height: 50px;
    line-height: 50px;
    color: white;
    font-size: 20px;
    padding-left: 50px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    text-align: center;
  }
  .card {
    width: 600px;
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    -moz-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    -o-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
  }
</style>
