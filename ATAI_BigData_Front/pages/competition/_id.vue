<template>
<div>
  <el-tabs :tab-position="tabPosition" >
    <el-tab-pane label="赛题与数据"><questions></questions></el-tab-pane>
    <el-tab-pane label="排行榜"><ranking :test="competitionId"></ranking></el-tab-pane>
    <el-tab-pane label="论坛"><forum></forum></el-tab-pane>
  </el-tabs>
</div>

</template>
<script>
  //引入调用competition.js文件
  import competitionApi from '@/api/competition'
  import questions from '@/pages/competition/questions'
  import ranking from '@/pages/competition/ranking'
  import forum from '@/pages/competition/forum'

  export default {
    name: 'tabZujian',
    components:{
        questions:questions,
        ranking:ranking,
        forum:forum
    },
    data() {
      return {
        tabPosition: 'left',
        competitionId: ''
      }
    },
    created() {
        this.competitionId=this.$route.params.id
    },
    methods: {
      //比赛详情的方法

    }
  };
</script>
<style >
.el-tabs__item{
  font-size: 17px;
  height: 50px;
  line-height: 50px;
}
</style>