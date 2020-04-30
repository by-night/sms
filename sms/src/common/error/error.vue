<template>
    <div class="content">
      <div>
        <div class="numStyle">404</div>
        <div class="englishFont">The page you are looking for from earth</div>
        <div class="chineseFont">啊哦，您访问的页面乘火箭去旅行了~</div>
      </div>
    </div>
</template>

<script>
    export default {
        name: "error"
    }
</script>
<style>
  html, body, #app {
    margin: 0 !important;
    padding: 0 !important;
    height: 100% !important;
  }
</style>
<style scoped>
  .content {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #005fb3;
    margin: 0;
    padding: 0;
  }
  .numStyle {
    text-align: center;
    font-size: 150px;
    color: white;
  }
  .englishFont {
    color: white;
    margin-bottom: 10px;
  }
  .chineseFont {
    color: white;
    font-size: 18px;
  }
</style>
