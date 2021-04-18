<template>
  <!-- <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load"> -->
     <scroll-page >
    <el-card :body-style="{ padding: '8px 18px' }">        
        <el-form :inline="true" class="demo-form-inline" size="mini">  
      <el-form-item >
          <el-date-picker style="width:160px" v-model="compObj.begin" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00" />
        </el-form-item>
        <el-form-item>
          <el-date-picker style="width:160px" v-model="compObj.end" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00" />
        </el-form-item>
         <el-form-item>
        <el-input v-model="compObj.title" placeholder="输入文章标题"/>
      </el-form-item>      
       <el-button size="mini" type="primary" icon="el-icon-search" @click="search(compObj.title,compObj.begin,compObj.end)">查询</el-button>
       <el-button size="mini" type="primary" icon="el-icon-edit" @click="write()">写文章</el-button>
    </el-form>
    </el-card> 
    <!-- 文章组件    -->
    <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>
     <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a :class="{undisable: !data.hasPrevious}" href="#" title="首页" @click.prevent="gotoPage(1)">首页</a>

            <a :class="{undisable: !data.hasPrevious}" href="#" title="前一页" @click.prevent="gotoPage(data.current-1)">&lt;</a>

            <a v-for="page in data.pages" :key="page" :class="{current: data.current == page, undisable: data.current == page}"
              :title="'第'+page+'页'" href="#" @click.prevent="gotoPage(page)">{{ page }}</a>

            <a :class="{undisable: !data.hasNext}" href="#" title="后一页" @click.prevent="gotoPage(data.current+1)">&gt;</a>

            <a :class="{undisable: !data.hasNext}" href="#" title="末页" @click.prevent="gotoPage(data.pages)">末页</a>

            <div class="clear" />
          </div>
        </div>
        <!-- 公共分页 结束 -->
  </scroll-page>
</template>

<script>
  import ArticleItem from '@/components/article/ArticleItem'
  import ScrollPage from '@/components/scrollpage'
  import blogApi from '@/api/blog'
  import cookie from 'js-cookie'
  import '~/assets/css/iconfont.css'
  export default {
    name: "ArticleScrollPage",
    data(){
      return{ 
        data: [], //查询之后接口返回集合
        page1: 1, //当前页
        limit: 3, //每页记录数
        subjectNestedList: [], // 分类列表
        oneIndex: -1,
        compObj: { }, // 查询表单对象
         loading: false,
        noData: false,
        // innerPage: {
        //   pageSize: 5,
        //   pageNumber: 1,
        //   name: 'a.createDate',
        //   sort: 'desc'
        // },
        articles: ["111111111"]
        }     
    },

    // props: {
      // offset: {//default父组件没传值时候的默认值
      //   type: Number,
      //   default: 100
      // },
      // page: {
      //   type: Object,
      //   default() {
      //     return {}
      //   }
      // },
      // query: {
      //   type: Object,
      //   default() {
      //     return {}
      //   }
      // }
    // },
    // watch: {
    //   'query': {
    //     handler() {
    //       this.noData = false
    //       this.articles = []
    //       this.innerPage.pageNumber = 1
    //       this.pageArticleCondition()
    //     },
    //     deep: true
    //   },
    //   'page': {
    //     handler() {
    //       this.noData = false
    //       this.articles = []
    //       this.innerPage = this.page
    //       this.pageArticleCondition()
    //     },
    //     deep: true
    //   }
    // },
    created() {
      // this.pageArticleCondition(this.page1,this.limit,this.compObj)
      // this.gotoPage(1)
    },
    mounted(){
      this.gotoPage(1)
    },
    methods: {
      gotoPage(page) {
        console.log(this.articles)
        // debugger
        blogApi.pageArticleCondition(page, this.limit ,this.compObj)
          .then(response => {
                  debugger
                  this.data = response.data.data
                  let newArticles = response.data.data.records
                  this.articles = []
                  if (newArticles && newArticles.length > 0) {
                    // this.innerPage.pageNumber += 1
                    debugger
                    this.articles = this.articles.concat(newArticles)
                    for(let i=0;i<newArticles.length;i++){
                      this.articles[i]["tags"]=newArticles[i].tag.split(",")
                      
                    };
          } else {
            this.noData = true
          }})
        .catch(error => {
          if (error !== 'error') {
            this.$message({type: 'error', message: '文章加载失败!', showClose: true})
          }
        }).finally(() => {
          this.loading = false
        })
      },
      search(title,begin,end) {
        console.log(title,begin,end)
        this.compObj.title=title
        this.compObj.begin=begin
        this.compObj.end=end
         blogApi.pageArticleCondition(1, this.limit ,this.compObj)
          .then(response => {
                  // debugger
                  this.data = response.data.data
                  let newArticles = response.data.data.records
                  this.articles = []
                  if (newArticles && newArticles.length > 0) {
                    // this.innerPage.pageNumber += 1
                    // debugger
                    this.articles = this.articles.concat(newArticles)
                    for(let i=0;i<newArticles.length;i++){
                      this.articles[i]["tags"]=newArticles[i].tag.split(",")
                      
                    };
          } else {
            this.noData = true
          }})
        .catch(error => {
          if (error !== 'error') {
            this.$message({type: 'error', message: '文章加载失败!', showClose: true})
          }
        }).finally(() => {
          this.loading = false
        })       
      },
      //写文章
      write(){
         const token = cookie.get('ATAI_BigData_token')
            // 如果未登录，提示登录
            if (token) {  
            let authorId=1
            this.$router.push({path: `/write/${authorId}`})    
            }else{
              debugger
                this.$message({
                  type: 'error',
                  message: '请先登录?再进行下一步操作'
                });
                //vue路由跳转            
                this.$router.push({
                  path: '/login'
                })
            }
           
      },
      load() {
      //   //下拉执行函数

      //   // this.pageArticleCondition()
      },
      // view(id) {
      //   this.$router.push({path: `/view/${id}`})
      // }
    },
    //注册组件
    components: {
      'article-item': ArticleItem,
      'scroll-page': ScrollPage
    }

  }
</script>

<style scoped>
  .el-card {
    border-radius: 0;
  }

  .el-card:not(:first-child) {
    margin-top: 10px;

  }
</style>
