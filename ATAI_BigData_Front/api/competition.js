import request from '@/utils/request'
export default {
  //分页查询比赛的方法
  getCompetitionPageList(page,limit,compObj) {
    return request({
      url: `/atitcompetition/compfront/getCompetitionPageList/${page}/${limit}`,
      method: 'post',
      data: compObj
    })
  },
  //查询所有分类的方法
  getAllSubject() {
    return request({
      url: '/atitcompetition/compfront/getAllSubject',
      method: 'get'
    })
  },
  //比赛详情的方法
  getCompetitionDetailInfo(Id) {
    return request({
      url: `/atitcompetition/atai-competition/getCompetition/${Id}`,
      method: 'get',
    })
  },

  //根据(用户id)token，比赛id查询信息
  getUserCompetition(compentitionId) {
    return request({
      url: `/atitcompetition/atai-user-competition/getUserCompetition/${compentitionId}`,
      method: 'get',
    })
  },

  //根据比赛id，团队名称添加团队
  insertUserCompetition(competitionUserInfo){
    return request({
      url: `/atitcompetition/atai-user-competition/insertUserCompetition`,
      method: 'post',
      data: competitionUserInfo
    })
  },

  //根据比赛id，团队id查询信息
  getTeamCompetition(compentitionId, teamId){
    return request({
      url: `/atitcompetition/atai-user-competition/getUserCompetition/${compentitionId}/${teamId}`,
      method: 'get',
    })
  },

  //根据比赛id，查询排名信息
  getRanking(compentitionId){
    return request({
      url: `/atitcompetition/atai-user-competition/getRanking/${compentitionId}`,
      method: 'get',
    })
  },

  //查询当前用户的比赛列表
  getMyCompetitionList(){
    return request({
      url: `/atitcompetition/atai-user-competition/getMyCompetitionList`,
      method: 'get',
    })
  }

  
}
