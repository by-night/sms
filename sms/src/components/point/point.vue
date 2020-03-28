<!--&lt;!&ndash;<template>&ndash;&gt;-->
<!--&lt;!&ndash;<div>point</div>&ndash;&gt;-->
<!--&lt;!&ndash;</template>&ndash;&gt;-->

<!--&lt;!&ndash;<script>&ndash;&gt;-->
<!--&lt;!&ndash;export default {&ndash;&gt;-->
<!--&lt;!&ndash;name: "point"&ndash;&gt;-->
<!--&lt;!&ndash;}&ndash;&gt;-->
<!--&lt;!&ndash;</script>&ndash;&gt;-->

<!--&lt;!&ndash;<style scoped>&ndash;&gt;-->

<!--&lt;!&ndash;</style>&ndash;&gt;-->
<!--<template>-->
  <!--<div class="ValidCode disabled-select" :style="`width:${width}; height:${height}`" @click="refreshCode">-->
    <!--<span v-for="(item, index) in codeList" :key="index" :style="getStyle(item)">{{item.code}}</span>-->
  <!--</div>-->
<!--</template>-->

<!--<script>-->
  <!--export default {-->
    <!--name: 'validCode',-->
    <!--props: {-->
      <!--width: {-->
        <!--type: String,-->
        <!--default: '100px'-->
      <!--},-->
      <!--height: {-->
        <!--type: String,-->
        <!--default: '40px'-->
      <!--},-->
      <!--length: {-->
        <!--type: Number,-->
        <!--default: 4-->
      <!--}-->
    <!--},-->
    <!--data () {-->
      <!--return {-->
        <!--codeList: []-->
      <!--}-->
    <!--},-->
    <!--mounted () {-->
      <!--this.createdCode()-->
    <!--},-->
    <!--methods: {-->
      <!--refreshCode () {-->
        <!--this.createdCode()-->
      <!--},-->
      <!--createdCode () {-->
        <!--let len = this.length,-->
          <!--codeList = [],-->
          <!--chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789',-->
          <!--charsLen = chars.length-->
        <!--// 生成-->
        <!--for (let i = 0; i < len; i++) {-->
          <!--let rgb = [Math.round(Math.random() * 220), Math.round(Math.random() * 240), Math.round(Math.random() * 200)]-->
          <!--codeList.push({-->
            <!--code: chars.charAt(Math.floor(Math.random() * charsLen)),-->
            <!--color: `rgb(${rgb})`,-->
            <!--fontSize: `1${[Math.floor(Math.random() * 10)]}px`,-->
            <!--padding: `${[Math.floor(Math.random() * 10)]}px`,-->
            <!--transform: `rotate(${Math.floor(Math.random() * 90) - Math.floor(Math.random() * 90)}deg)`-->
          <!--})-->
        <!--}-->
        <!--// 指向-->
        <!--this.codeList = codeList-->
        <!--// 将当前数据派发出去-->
        <!--this.$emit('update:value', codeList.map(item => item.code).join(''))-->
      <!--},-->
      <!--getStyle (data) {-->
        <!--return `color: ${data.color}; font-size: ${data.fontSize}; padding: ${data.padding}; transform: ${data.transform}`-->
      <!--}-->
    <!--}-->
  <!--}-->
<!--</script>-->

<!--<style scoped lang="scss">-->
  <!--.ValidCode{-->
    <!--display: flex;-->
    <!--justify-content: center;-->
    <!--align-items: center;-->
    <!--cursor: pointer;-->
    <!--span{-->
      <!--display: inline-block;-->
    <!--}-->
  <!--}-->
<!--</style>-->
