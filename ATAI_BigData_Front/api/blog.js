import request from '@/utils/request'
export default {
    //分页查询文章的方法
    pageArticleCondition(page,limit,articleQuery) {
      return request({
        url: `/eduservice/atai-article/pageArticleCondition/${page}/${limit}`,
        method: 'post',
        data: articleQuery
      })
    },

    //文章id查询这篇文章+文章体
    viewArticle(id) {
      return request({
        url: `/eduservice/atai-article/getArticleById/${id}`,
        method: 'get'
      })
    },

    //查询文章所有分类
    getCategorys(){
      return request({
        url: `/eduservice/atai-category/findAll`,
        method: 'get'
      })
    },

    //发布文章
    publishArticle(articlePublish){
      return request({
      url: `/eduservice/atai-article/addArticle`,
      method: 'post',
      data: articlePublish
      })
    },

 
    //上传图片
    upload(formdata){
      return request({
        headers: {'Content-Type': 'multipart/form-data'},
        url: '/eduoss/fileoss',
        method: 'post',
        data: formdata
      })  
    },

    //查询当前用户的比赛列表
    getMyArticleList(page,limit){
    return request({
      url: `/eduservice/atai-article/getMyArticleList/${page}/${limit}`,
      method: 'get',
    })
  }



}
