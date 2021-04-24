import request from '@/utils/request'
export default{
  //1.公告列表-条件查询分页
  pageNoticeCondition(current,limit,noticeQuery){
    return request({
      url: `/eduservice/atai-notice/pageNoticeCondition/${current}/${limit}`,
      method: 'post',  
      data: noticeQuery
    })
  },
  //删除公告
  removeNotice(id){
    return request({
      url: `/eduservice/atai-notice/removeNotice/${id}`,
      method: 'get'
    })
  },
  //添加公告
  addNotice(notice){
    return request({
      url: `/eduservice/atai-notice/addNotice`,
      method: 'post',
      data: notice
    })
  },
  //根据id查询公告  数据回显
  getNoticeById(id){
    return request({
      url: `/eduservice/atai-notice/getNoticeById/${id}`,
      method: 'get'
    })
  },
  //修改公告
  updateNotice(notice){
    return request({
      url: `/eduservice/atai-notice/updateNotice`,
      method: 'post',
      data: notice
    })
  }
}
