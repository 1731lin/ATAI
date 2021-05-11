import request from '@/utils/request'
export default {
  //1.添加比赛分类信息
  addCompLevelInfo(compLevelInfo) {
    return request({
      url: `/atitcompetition/atai-comp-level/addCompLevel`,
      method: 'post',
      data: compLevelInfo
    })
  },
  //2.修改比赛信息
  updateCompLevel(compLevelInfo) {
    return request({
      url: `/atitcompetition/atai-comp-level/updateCompLevel`,
      method: 'post',
      data: compLevelInfo
    })
  },
  //3.删除比赛分类
  removeCompLevel(Id){
    return request({
      url: `/atitcompetition/atai-comp-level/${Id}`,
      method: 'delete'
    })
  },

  //4.查询全部
  findAll() {
    return request({
      url: `/atitcompetition/atai-comp-level/findAll`,
      method: 'get',
    })
  },
}