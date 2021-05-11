<template>
  <article class="col-7 fl">
    <div class="u-r-cont">
      <section>
        <div>
          <section class="c-infor-tabTitle c-tab-title">

            <a href="javascript: void(0)" title="我的文章" class="current">
              文章列表
            </a>
          </section>
        </div>
        <div class="mt40">
          <section v-if="articlesList.length === 0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam" >您还没有编写文章哦！</span>
          </section>

          <!-- 表格 -->
          <el-table
            v-if="articlesList.length>0"
            :data="articlesList"
            bcompetition
            fit
            highlight-current-row>
            <el-table-column  label="文章类型"  width="150" align="center" >
                       <el-col   slot-scope="scope"  >             
                            <el-button type="success"  size="mini">{{ scope.row.category }}</el-button>                                              
                      </el-col>
            </el-table-column>
            <el-table-column label="文章信息" align="center" >
              <template slot-scope="scope">
                <div class="info" >                 
                  <div class="title">
                    <a @click="view(scope.row.id)" >{{ scope.row.title }}</a>
                  </div>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="评论数" width="100" align="center">
              <template slot-scope="scope">
                {{ scope.row.commentCounts }}
              </template>
            </el-table-column>
            <el-table-column label="访问量" width="100" align="center" >
              <template slot-scope="scope">
                {{ scope.row.viewCounts }}
              </template>
            </el-table-column>
            <el-table-column label="创建时间" width="250" align="center">
              <template slot-scope="scope">
                {{ scope.row.gmtCreate }}
              </template>
            </el-table-column>                  
          </el-table>
        </div>

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
      </section>
    </div>
  </article>
</template>

<script>
 
  import blogApi from '@/api/blog'

  export default {
    data(){
      return{
        data:[],
        page: 1, //当前页
        limit: 2, //每页记录数
        articlesList:[],
      }
    },

    created() {
      this.fetcharticlesList()
    },

    methods:{
      fetcharticlesList(page=1){
        this.page=page
        blogApi.getMyArticleList(this.page, this.limit).then(response => {
          debugger
          this.data = response.data.data
          this.articlesList = response.data.data.records
        })
      },
          //分页切换的方法
      //参数是页码数
      gotoPage(page) {
        this.page=page
         blogApi.getMyArticleList(this.page, this.limit).then(response => {
          debugger
          
          this.articlesList = response.data.data.records
          this.data = response.data.data
        })
      },

       view(id) {
        this.$router.push({path: `/blog/${id}`})
      }
    }

  }
</script>
