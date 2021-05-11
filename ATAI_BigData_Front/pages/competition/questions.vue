<template>
  <div id="aCoursesList" class="questions">
    <!-- 赛题描述 开始 -->
    <section class="container">
      <div style="font-size: 29PX; font-weight: 600;">    
        <div>报名</div>   
         <el-row style=" margin-bottom: 20px; margin-left: 120px;">
        <el-button @click="registration()" type="primary">{{baomin}}</el-button>
      </el-row>   
      </div>
     
      <hr/>
      <div style="font-size: 29PX; font-weight: 600;">    
        <div>赛题描述</div>      
      </div>
     <el-form label-width="120px">
      <el-form-item label="比赛名称">
        {{competition.name}}
      </el-form-item>
      <el-form-item label="比赛类型">
        <el-select v-model="competition.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            value使用动态绑定的值，比赛类型由1 2 3代替
          -->
          <el-option :value="1" label="新人赛" />
          <el-option :value="2" label="创新应用大赛" />
          <el-option :value="3" label="算法大赛" />
        </el-select>
        
      </el-form-item>
          <div v-html="competition.description"></div>

      <el-form-item label="比赛简介">
        {{competition.intro}}
      </el-form-item>
      <!-- 富文本 -->
        <header class="comm-title">
      <div style="font-size: 29PX; font-weight: 600;">    
        <div>比赛描述</div>      
      </div>
        
      </header>
    <div v-html="competition.description"></div>
       
        <h2 class="fl tac">
          
         赛题
          
        </h2>
     

   
        <el-form-item label="">
          <el-tag type="info">报名后可以下载赛题</el-tag>
          <el-tag v-if="competition.baomin">
            <i class="el-icon-download" />
            <a :href="competition.cover">点击下载赛题</a>
          </el-tag>
        </el-form-item>   
     </el-form>
      <hr/>

      
    
     
      <!-- <section class="mt30">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">赛题</span>
        </h2>
      </header>
      </section> -->
 
    </section>
    <!-- /讲师介绍 结束 -->
  </div>
</template>
<script>
  //引入调用teacher.js文件
  import competitionApi from '@/api/competition'
      //引入调用js-cookie
  import cookie from 'js-cookie'
  export default {
	name: 'questions',
    data() {
      return {
        baomin : "报名比赛",
        xiazai : "JavaScript：;",
        teamId : "",
        competition : {
          name:'',
          level:1,
          intro:'',
          description: '',
          deadline: '00:00:00',
          baomin :"false",
          cover: '',
          result: '',
          money:0
        }, //v-model双向绑定
        competitionUserInfo : {
            compentitionId: '',
            teamName : ""
        },
      }
    },
    created() {
      const token = cookie.get('ATAI_BigData_token')
       //根据id获取比赛
      this.getCompetitionDetailInfo(this.$route.params.id)
      //根据比赛id，token判断是否报名了比赛
      this.getUserCompetition(this.$route.params.id)
      this.competitionUserInfo.compentitionId=this.$route.params.id
    },
    mounted(){
     
       
    },
    methods: {
      //根据id获取比赛
      getCompetitionDetailInfo(id) { //比赛列表的方法
        competitionApi.getCompetitionDetailInfo(id)
          .then(response => { //请求成功
            //response接口返回的数据
            debugger
            this.competition = response.data.data.competition
            // console.log( this.competition )
            
            const token = cookie.get('ATAI_BigData_token')
            if (token) {
            this.competition.baomin="ture" 
            }
          })
          .catch(error => { //请求失败
            console.log(error)
          })
      },
       //根据用户id，比赛id查询信息
      getUserCompetition(compentitionId){
        competitionApi.getUserCompetition(compentitionId)       
          .then(response=>{//请求成功
              debugger
              // console.log(response.data.data.userCompetition)
              this.teamId=response.data.data.userCompetition.teamId
            if(response.data.data.userCompetition!=null){
              this.baomin="提交结果"
            }else{
              this.baomin="报名比赛"
            }
          })
      },
      registration(){
        debugger
        const token = cookie.get('ATAI_BigData_token')
        // 如果未登录，提示登录
        if (token) {  
          //报名
          if(this.baomin=="报名比赛"){
            //调用接口自动报名
            //弹窗填比赛团队名称,
            this.open()

          }else{
            //提交比赛结果
            this.addRegistration()    
          }
          
        }else{
          debugger
            this.$message({
              type: 'error',
              message: '请先登录️再进行下一步操作'
            });

            //vue路由跳转
            
            this.$router.push({
              path: '/login'
            })
        }
      },
      //跳转比赛
      addRegistration(){
          //用户信息，类似订单的
          //跳转页面
          //vue路由跳转
            this.$router.push({
              path: '/competition/reg/'+this.teamId+'and'+this.competitionUserInfo.compentitionId
            })
      },
      //根据比赛id，团队名称添加团队
      insertUserCompetition(){
        competitionApi.insertUserCompetition(this.competitionUserInfo)
        .then(response=>{//请求成功
              debugger
              // console.log(response.data)
              this.teamId=response.data.data.teamId    
              console.log(this.teamId)  
              console.log(this.competitionUserInfo.compentitionId)   
              this.$router.push({
              path: '/competition/reg/'+this.teamId+'and'+this.competitionUserInfo.compentitionId
            })      
          })
      },
      open() {        
        this.$prompt('请输入队伍名称【队伍存在将自动加入，不存在则创建】', '加入队伍', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(({ value }) => {
          this.competitionUserInfo.teamName=value

          //调用接口插入队伍信息
          this.insertUserCompetition()     
          this.$message({
            type: 'success',
            message: '你的队伍是: ' + value,        
          });   
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });       
        });
      }
      }

     
  };
</script>
