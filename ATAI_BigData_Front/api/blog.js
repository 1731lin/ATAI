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
    }
  
}
