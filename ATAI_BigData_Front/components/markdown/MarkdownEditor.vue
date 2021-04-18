<template>
<div>
    <mavon-editor
    class="me-editor"
     style="padding-left:20px"
    ref="md"
    v-model="editor.value"
    @imgAdd="imgAdd"
    v-bind="editor">
  </mavon-editor>
</div>

</template>


<script>
  
  import blogApi from '@/api/blog'
  export default {
    name: 'MarkdownEditor',
    props: {
      editor: Object
    },
    data() {
      return {}
    },
    mounted() {
      this.$set(this.editor, 'ref', this.$refs.md)
    },
    methods: {
      imgAdd(pos, $file) {
        debugger
        let that = this
        let formdata = new FormData()
        formdata.append('file', $file)
        blogApi.upload(formdata).then(response => {
          debugger
          // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
          if (response.data.code == 20000) {

            that.$refs.md.$img2Url(pos, response.data.data.url);
          } else {
            that.$message({message: response.data.msg, type: 'error', showClose: true})
          }

        }).catch(err => {
          that.$message({message: err, type: 'error', showClose: true});
        })
      }
    }
  }
</script>
<style scoped>
  .me-editor {
    z-index: 6 !important;
  }

  .v-note-wrapper.fullscreen {
    top: 60px !important
  }
</style>
