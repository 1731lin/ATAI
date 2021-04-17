<template>
    <div class="ranking">

  <el-table
    ref="singleTable"
    :data="ranking"
    highlight-current-row
    @current-change="handleCurrentChange"
    style="width: 100%;min-height:500px">
   
    <el-table-column
      type="index"
      width="50">
    </el-table-column>
   
    <el-table-column
      property="teamName"
      label="参与者"
      width="120">
    </el-table-column>
    <el-table-column
      property="score"
      label="得分">
    </el-table-column>
     <el-table-column
      property="deadline"
      label="最优成绩提交日期"
      width="320">
    </el-table-column>
  </el-table>

    </div>
</template>

<script>
  //引入调用competition.js文件
  import competitionApi from '@/api/competition'
    export default {
    // props: ['test'],
     props: {
        test:{
                type:String,
                default:null
            }
      },
    data() {
        
      return {
          name:"ranking",
        ranking: "",
        currentRow: null
      }
    },
   created() {
    
    },
    mounted () {
      // debugger
      // alert(this.test)
      this.getRanking(this.test);
    },

    methods: {
      getRanking(compentitionId){
        debugger
        competitionApi.getRanking(compentitionId)
        .then(response=>{//请求成功
                debugger
                // console.log(response.data)
                this.ranking=response.data.data.ranking   
                
            })
      }
    }
  }
</script>