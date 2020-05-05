<template>
  <div id="app" :class="!silent ? '' : 'silent'">
    <router-view v-if="isRouterAlive"></router-view>
  </div>
</template>

<script>
    export default {
        name: 'App',
      provide () {
        return {
          reload: this.reload
        }
      },
      data () {
        return {
          isRouterAlive: true
        }
      },
      methods: {
        reload () {
          this.isRouterAlive = false;
          this.$nextTick(function () {
            this.isRouterAlive = true
          })
        },
        getSilent () {
          this.axiosHelper.get('/api/sms/user/getSilent').then(response => {
            const data = response.data;
            this.$store.commit('SAVE_SILENT', data);
          })
        },
      },
      mounted () {
        this.getSilent();
      },
      computed: {
        silent () {
          return this.$store.state.silent;
        }
      },
    }
</script>
<style>
  .silent {
    -webkit-filter: grayscale(100%);
    -moz-filter: grayscale(100%);
    -ms-filter: grayscale(100%);
    -o-filter: grayscale(100%);
    filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1);
    _filter:none;
  }
</style>
