<template>
  <div class="app-container">
    <div style="font-size: 19PX; font-weight: 600; margin-bottom: 20px; margin-left: 20px;">
      <div v-if="this.$route.params && this.$route.params.id">修改公告</div>
      <div v-else>添加公告</div>
    </div>

    <el-form label-width="120px">
      <el-form-item label="标题">
        <el-input v-model="notice.title" />
      </el-form-item>     
      <el-form-item label="内容">
        <el-input v-model="notice.content" :rows="5" type="textarea" />
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" plain="true" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  //引入调用notice.js文件
  import noticeApi from '@/api/edu/notice'

  export default {
    data() {
      return {
        notice: {
          title:'',
          content:''
        }, //v-model双向绑定
        saveBtnDisabled: false //保存按钮是否禁用
      }
    },
    created() { //页面渲染前执行
      this.init()
    },
    watch: {  //vue的监听
        $route(to, from) {  //路由变化方式，路由一发生变化 就执行方法
          this.init()
        }
      },
    methods: {
     
      init(){
        //判断路径有id值  修改操作
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id
          this.getInfo(id)
        }else{ //判断路径没有id值  添加操作
          //清空表单即清空notice
          this.notice = {
          title:'',          
          content:''
          }//v-model双向绑定
        }
      },
      //根据公告id查到公告信息 回显操作
      getInfo(id) {
        noticeApi.getNoticeById(id)
          .then(response => {
            this.notice = response.data.data
          })
          .catch((response) => {
            this.$message({
              type: 'error',
              message: '获取数据失败'
            })
          })
      },

      //保存按钮调用的方法
      saveOrUpdate() {
        //判断修改或添加 notice是否有id
        if(!this.notice.id){
          //添加
          this.addnotice()
        }else{
          //修改
          this.updatenotice()
        }
      },

      //添加公告的方法
      addnotice() {
        noticeApi.addNotice(this.notice)
          .then(response => { //添加成功
            //提示成功
            this.$message({
              type: 'success',
              message: '添加成功！ 😄'
            });
            //回到公告列表页面
            //vue路由跳转
            this.$router.push({
              path: '/notice/table'
            })
          })
      },

      //修改公告的方法
      updatenotice() {
        noticeApi.updateNotice(this.notice)
          .then(response => { //修改成功
            //提示成功
            this.$message({
              type: 'success',
              message: '修改成功！ 🧙‍♂️'
            });
            //回到公告列表页面
            //vue路由跳转
            this.$router.push({
              path: '/notice/table'
            })
          })
      }
    }
  }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar-uploader img {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
