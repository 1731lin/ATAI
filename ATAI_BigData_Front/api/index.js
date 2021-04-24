import request from '@/utils/request'
export default {

  //查询前五条公告
  getLatestNotice(){
    return request({
      url: `/eduservice/atai-notice/getLatestNotice`,
      method: 'get',
    })
  },

  //查询4热门老师
  getHotTeacher() {
    return request({
      url: `/eduservice/indexfront/indexHotTeacher`,
      method: 'get'
    })
  }
}
