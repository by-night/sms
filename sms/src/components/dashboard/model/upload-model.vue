<template>
  <el-dialog
    v-dialogDrag title="预览"
    :visible.sync="dialog"
    :close-on-click-modal=false
    append-to-body
    width="270px">
    <div class="content">
      <el-image
        style="width: 140px; height: 160px"
        :src="imageUrl">
      </el-image>
    </div>
    <div slot="footer">
      <el-button type="primary" @click="click" size="small">上传</el-button>
      <el-button @click="cancel" size="small">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "upload",
  data () {
    return {
      imageUrl: '',
      dialog: false,
      form: null
    }
  },
  methods: {
    init (url, form) {
      this.imageUrl = url;
      this.form = form;
      this.dialog = true;
    },
    click () {
      this.uploadMethod(this.form)
    },
    uploadMethod (form) {
      this.axiosHelper.post(
        '/api/sms/upload/headImg',
        form,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
      ).then(response => {
        const url = response.data;
        this.$message.success({
          message: '头像上传成功'
        });
        this.$emit('propUrl', url);
        this.dialog = false;
      }).catch(() => {
        this.$message.error({
          message: '头像上传失败'
        });
        this.dialog = false;
      })
    },
    cancel () {
      this.dialog = false;
    },
  }
}
</script>

<style scoped>
  .content {
    height: 200px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
