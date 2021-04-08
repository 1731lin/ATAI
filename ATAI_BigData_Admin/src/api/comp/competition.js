import request from '@/utils/request'
export default {
  //1.添加比赛信息
  addCompetitionInfo(competitionInfo) {
    return request({
      url: `/atitcompetition/atai-competition/addCompetition`,
      method: 'post',
      data: competitionInfo
    })
  },
  //2.根据比赛id查询比赛基本信息
  getCompetitionById(Id) {
    return request({
      url: `/atitcompetition/atai-competition/getCompetition/${Id}`,
      method: 'get',
    })
  },
  //3.修改比赛信息
  updateCompetition(competitionInfo) {
    return request({
      url: `/atitcompetition/atai-competition/updateCompetition`,
      method: 'post',
      data: competitionInfo
    })
  },
  //4.分页查询比赛的方法
  //current 当前页 limit 每页记录数 
  getPageListCompetition(current,limit){
    return request({
      url: `/atitcompetition/atai-competition/pageCompetition/${current}/${limit}`,
      method: 'get',
    })
  },

  //5.删除比赛
  removeCompetition(Id){
    return request({
      url: `/atitcompetition/atai-competition/${Id}`,
      method: 'delete'
    })
  },

  //6.条件查询带分页的方法
  getCompetitionListPage(current,limit,competitionQuery){
    return request({
      url: `/atitcompetition/atai-competition/pageCompetitionCondition/${current}/${limit}`,
      method: 'post',
      data: competitionQuery
    })
  },
}