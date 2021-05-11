<template>
  <article class="col-7 fl">
    <div class="u-r-cont">
      <section>
        <div>
          <section class="c-infor-tabTitle c-tab-title">

            <a href="javascript: void(0)" title="我的比赛" class="current">
              比赛列表
            </a>
          </section>
        </div>
        <div class="mt40">
          <section v-if="competitionList.length === 0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam" >您还没有参加比赛哦！</span>
          </section>

          <!-- 表格 -->
          <el-table
            v-if="competitionList.length>0"
            :data="competitionList"
            bcompetition
            fit
            highlight-current-row>
            <el-table-column  label="比赛类型"  width="150" align="center" >
                       <el-col   slot-scope="scope"  >             
                            <el-button type="success" v-if="scope.row.level===1" size="mini">新人赛</el-button>
                            <el-button type="success" v-if="scope.row.level===2" size="mini">创新应用大赛</el-button>
                            <el-button type="success" v-if="scope.row.level===3" size="mini">算法大赛</el-button>                              
                      </el-col>
            </el-table-column>
            <el-table-column label="比赛信息" align="center" >
              <template slot-scope="scope">
                <div class="info" >                 
                  <div class="title">
                    <a @click="view(scope.row.id)" >{{ scope.row.name }}</a>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="队伍名称" width="100" align="center">
              <template slot-scope="scope">
                {{ scope.row.teamName }}
              </template>
            </el-table-column>
            <el-table-column label="分数" width="100" align="center" >
              <template slot-scope="scope">
                {{ scope.row.score }}
              </template>
            </el-table-column>
            <el-table-column label="截至时间" width="250" align="center">
              <template slot-scope="scope">
                {{ scope.row.deadline }}
              </template>
            </el-table-column>
           

           
          </el-table>
        </div>
      </section>
    </div>
  </article>
</template>

<script>
  //引入调用competition.js文件
  import competitionApi from '@/api/competition'

  export default {
    data(){
      return{
        competitionList:[],
      }
    },

    created() {
      this.fetchcompetitionList()
    },

    methods:{
      fetchcompetitionList(){
        competitionApi.getMyCompetitionList().then(response => {
          debugger
          
          this.competitionList = response.data.data.data
        })
      },
      view(id) {
        this.$router.push({path: `/competition/${id}`})
      }
    },
  }
</script>
