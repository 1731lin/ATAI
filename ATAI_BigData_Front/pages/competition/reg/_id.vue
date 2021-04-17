<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container" style="width: 50%">
    <el-divider><i class="el-icon-monitor"></i></el-divider> 
    <el-table           
        :data="base"
        style="width: 100%">
        <el-table-column
          prop="clo1"
          label="ATAI_BigData"
          width="180">
        </el-table-column>
        <el-table-column
          prop="clo2"
          label="比赛信息"
          >
        </el-table-column> 
    </el-table>
     <el-divider><i class="el-icon-monitor"></i></el-divider>
      <el-table            
        :data="duiyou"
        style="width: 100%">
        <el-table-column
          prop="nickname"
          label="团队成员昵称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          width="180">
        </el-table-column>
        <el-table-column
          prop="sign"
          label="实验室认证">
        </el-table-column>
    </el-table>
     <el-divider><i class="el-icon-monitor"></i></el-divider>
    <el-form label-width="120px">
      <!-- 提交到oss，后端 -->
      <el-form-item label="提交比赛结果">  <!-- myUpload唯一标识  -->
        <el-upload ref="myUpload"     
                   :auto-upload="false"  
                   :on-success="fileUploadSuccess" 
                   :on-error="fileUploadError" 
                   :disabled="importBtnDisabled" 
                   :limit="1" 
                   :action="BASE_API+'/atitcompetition/atai-user-competition/saveResult/'+compentitionId+'/'+userId"
                   name="file" 
                   accept="application/vnd.ms-excel"> <!-- 设置上传格式 微软excel -->
          <el-button plain="true" slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button :loading="loading" plain="true" style="margin-left: 10px;" size="small" type="success" @click="submitUpload">
            {{ fileUploadBtnText }}</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    </section>
  </div>

</template>

<script>
  //引入调用competition.js文件
  import competitionApi from '@/api/competition'

  export default {
    data() {
      return {
        BASE_API: "http://localhost:8666", // 接口API地址
        OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
        fileUploadBtnText: '上传到服务器', // 按钮文字
        importBtnDisabled: false, // 按钮是否禁用,
        compentitionId: '',
        loading: false,
        teamId : '',
        userId : '',
        teamName : '',
        competition : '大傻逼',
        score : '',
        duiyou : '',
        teamCompetition: '',
        base: [{
           clo1:"我的团队",
           clo2:this.teamName
          },
          {
           clo1:"分数",
           clo2:this.score
          },
          {
           clo1:"最优成绩提交日期",
           clo2:this.deadline
          }
           ]
      }
    },
    created() {
        var tmp=this.$route.params.id
        var a=tmp.split("and")
        console.log(a);
        this.teamId=a[0]
        this.compentitionId=a[1]
        this.getTeamCompetition();
    },
    mounted () {
       

    },
    methods: {
      //点击上传按钮  上传excel文件到接口种
      submitUpload() {
        if( this.$refs.myUpload.$data.uploadFiles.length>0){
        this.fileUploadBtnText = '正在上传'
        this.importBtnDisabled = true
        this.loading = true
        this.$refs.myUpload.submit()//js:document.getElementById("myUpload:).submit()
      }else {
        this.$message({
          type: 'warning',
          message: "请选择文件"
        })
      }
      },
     
      //上传成功
      fileUploadSuccess(response) {
        //提示信息
        debugger
              if (response.success === true) {
        this.fileUploadBtnText = '导入成功'
        this.loading = false
        this.$message({
          type: 'success',
          message: response.message
        })
        //刷得分日期
        this.getTeamCompetition();
      }
      else{
        this.fileUploadBtnText = '重新上传'
        this.loading = false
        this.importBtnDisabled = false
        this.$message({
          type: 'warning',
          message: response.message
        })
      }
      },

      //上传失败
      fileUploadError() {
        this.fileUploadBtnText = '重新上传'
        this.importBtnDisabled = false
        this.loading = false
        this.$message({
          type: 'error',
          message: '导入文件失败'
        })
      },

    getTeamCompetition(){
      debugger
      competitionApi.getTeamCompetition(this.compentitionId, this.teamId)
      .then(response=>{//请求成功
              debugger
              // console.log(response.data)
              this.teamCompetition=response.data.data.teamCompetition   
              this.teamName=this.teamCompetition.teamName         
              this.score=this.teamCompetition.score
              this.userId=this.teamCompetition.userId
              this.duiyou=this.teamCompetition.friend 
              this.base[0].clo2=this.teamName;
              this.base[1].clo2=this.score;
              this.base[2].clo2=this.teamCompetition.deadline;
          })
    }
       

    }
  }
</script>
<style>
  .el-table td, .el-table th{
    text-align: center !important;
  }
</style>
