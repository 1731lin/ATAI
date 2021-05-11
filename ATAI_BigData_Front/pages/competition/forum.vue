<template>
  <div class="me-view-body" >
    <section class="container">
    <el-container class="me-view-container">
      <el-main>        
        <div class="me-view-card">
          <div class="me-view-comment">
            <div class="me-view-comment-write">
              <el-row :gutter="20">
                <el-col :span="1.5">
        
                </el-col>
                <el-col :span="22">
                  <el-input
                    type="textarea"
                    :autosize="{ minRows: 2}"
                    placeholder="你的评论..."
                    class="me-view-comment-text"
                    v-model="comment.content"
                    resize="none">
                  </el-input>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="2" :offset="22">
                  <el-button type="text" @click="publishComment()">评论</el-button>
                </el-col>
              </el-row>
            </div>

            <!-- <div class="me-view-comment-title">
              <span>{{article.commentCounts}} 条评论</span>
            </div> -->

            <commment-item
              v-for="(c,index) in comments"
              :comment="c"
              :articleId="article.id"
              :index="index"
              :rootCommentCounts="comments.length"
              @getArticle="getArticle"
              :key="c.id">
            </commment-item>
          </div>
        </div>

      </el-main>

    </el-container>
      </section>
  </div>
</template>

<script>
  import CommmentItem from '@/components/comment/CommentItem'
  import blogApi from '@/api/blog'
  import commentApi from '@/api/comment'
  import cookie from 'js-cookie'
  import default_avatar from '@/assets/img/tipQe.png'
    export default {
      name: 'forum',
      created() {
      this.getArticle()
    },
    watch: {
      '$route': 'getArticle'
    },
    data() {
      return {
        article: {
          id: '',
          title: '',
          commentCounts: 0,
          viewCounts: 0,
          summary: '',
          nickname: '',
          authorId: '',
          avatar:'',
          content:'',
          contentId:'',
          tags: [],
          tag: '',
          category:{},
          gmtCreate: '',
          editor: {
            value: "",
            toolbarsFlag: false,
            subfield: false,
            defaultOpen: 'preview'
          }
        },
        competitionId: '',
        comments: [],
        comment: {
          article: {},
          content: ''
        }
      }
    },
    computed: {
      avatar() {
        // let avatar = this.$store.state.avatar

        if (avatar.length > 0) {
          return avatar
        }
        return default_avatar
      }    
    },
    methods: {

      getArticle() {
        this.getCommentsByArticle()
      },
      publishComment() {
        debugger
        let that = this
        debugger
        const token = cookie.get('ATAI_BigData_token')
        // 如果未登录，提示登录
        if (token) {  
            if (!that.comment.content) {
              return;
            }
            that.comment.articleId = this.$route.params.id
            commentApi.publishComment(that.comment).then(response => {
              debugger
              // that.$message({type: 'success', message: '评论成功', showClose: true})           
              that.comment.content = ''
              this.getArticle();
            }).catch(error => {
              if (error !== 'error') {
                that.$message({type: 'error', message: '评论失败', showClose: true})
                
              }
            })
          
        }else{
          debugger
            this.$message({
              type: 'error',
              message: '请先登录️再进行下一步操作'
            });

            //vue路由跳转
            
            this.$router.push({
              path: '/login'
            })
        }
        
      },
      getCommentsByArticle() {
        debugger
        let that = this
        commentApi.getCommentsByArticle(1,2,this.$route.params.id).then(response => {
          debugger
          that.comments = response.data.data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '评论加载失败', showClose: true})
          }
        })
      }
    },
    components: {

      CommmentItem
    }
  }
</script>

<style>
  .me-view-body {
    margin: 20px auto 140px;
  }

  .el-main {
    overflow: hidden;
  }

  .me-view-title {
    font-size: 34px;
    font-weight: 700;
    line-height: 1.3;
  }

  .me-view-author {
    /*margin: 30px 0;*/
    margin-top: 30px;
    vertical-align: middle;
  }


  .me-view-info {
    display: inline-block;
    vertical-align: middle;
    padding: 8px;
  }

  .me-view-meta {
    font-size: 12px;
    color: #969696;
  }

  .me-view-end {
    margin-top: 20px;
  }