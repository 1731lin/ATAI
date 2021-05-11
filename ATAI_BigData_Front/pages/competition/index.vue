<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 比赛列表 开始 -->
    <section class="container">
      <header class="comm-title all-competition-title">
        <h2 class="fl tac">
          <span class="c-333">全部比赛</span>
        </h2>
      </header>
      
      <section class="c-sort-box unBr">
         <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">比赛类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li  >  
                   <a title="全部" @click="view()">全部              
                </a>                     
                </li>
                
                <li v-for="(item,index) in subjectNestedList" :key="index" :class="{active:oneIndex==index}">
                  <a :title="item.title" href="#" @click="searchOne(item.id,index)">{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>          
          <div class="clear"></div>
        </section>

        <div class="js-wrap">
         
          <section class="fl">
            <ol class="js-tap clearfix">
             <li :class="{'current bg-orange':hotSort!=''}">
                <a title="参赛人数" href="javascript:void(0);" @click="searchBuyCount()">参赛人数
                <span :class="{hide:hotSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':gmtCreateSort!=''}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新
                <span :class="{hide:gmtCreateSort==''}">↓</span>
                </a>
              </li>
              <li :class="{'current bg-orange':priceSort!=''}">
                <a title="奖金" href="javascript:void(0);" @click="searchPrice()">奖金&nbsp;
                  <span :class="{hide:priceSort==''}">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>

        <div>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total==0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>

          <article class="i-competition-list" v-if="data.total>0">
            <ul class="of">
              <li v-for="competition in data.items" :key="competition.id">
                <section class="i-comp-wrap">
                  <a class="i-comp-pic" @click="view(competition.id)"  >
                    <el-row :gutter="20">
                      <el-col :span="3"  justify="start" >             
                            <el-button type="success" v-if="competition.level===1" size="mini">新人赛</el-button>
                            <el-button type="success" v-if="competition.level===2" size="mini">创新应用大赛</el-button>
                            <el-button type="success" v-if="competition.level===3" size="mini">算法大赛</el-button>                              
                      </el-col>
                      <el-col :span="9"  justify="start"> 
                          <div class="" style="font-weight:bold">{{competition.name}}</div>
                      </el-col>
                      <el-col :span="3"  justify="start"> 
                        <div class="">奖金</div></el-col>
                      <el-col :span="3"  justify="start"> 
                        <div class="">人数 </div> </el-col>
                      <el-col :span="5"  justify="start"> 
                        <div class="">赛季</div></el-col>
                    </el-row>
                 </a>
                  <div class="mt15 i-q-txt" style="height:60px;text-align:left">
                    <a @click="view(competition.id)" >
                      <el-row :gutter="20">
                    <el-col :span="12"  justify="start"> 
                    <div  :title="competition.intro" >   
                       {{competition.intro}}
                    </div>
                    </el-col>
                     <el-col :span="3"  justify="start"> 
                      <div class="mt10 hLh30 txtOf tac">￥{{competition.money}}</div>
                    </el-col>
                    <el-col :span="3"  justify="start"> 
                      <div class="mt10 hLh30 txtOf tac">{{competition.participants}} </div>
                    </el-col>
                     <el-col :span="5"  justify="start"> 
                      <div class="mt10 hLh30 txtOf tac">{{competition.deadline}}</div>
                    </el-col>
                    </el-row>                 
                    </a>             
                  </div>
                </section>
               
              </li>
            </ul>
            
            <div class="clear"></div>
          </article>
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
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /比赛列表 结束 -->
  </div>
</template>
<script>
  //引入调用competition.js文件
  import competitionApi from '@/api/competition'
  export default {
    data() {
      return {
        data: [], //查询之后接口返回集合
        page: 1, //当前页
        limit: 2, //每页记录数
        subjectNestedList: [], // 分类列表
        oneIndex: -1,
        compObj: {}, // 查询表单对象
        hotSort: "",
        gmtCreateSort: "",
        priceSort: ""
      }
    },
    created() {
      debugger
      this.compObj.name=this.$route.query.index     
      this.getCompetitionList()
       //分类显示
      this.initSubject()
     
    },
    methods: {
      //获取比赛列表
      getCompetitionList(page = 1) { //比赛列表的方法
        debugger
        this.page = page
     
        competitionApi.getCompetitionPageList(this.page, this.limit, this.compObj)
          .then(response => { //请求成功
            //response接口返回的数据
            this.data = response.data.data
            console.log(this.data.items[0])
          })
          .catch(error => { //请求失败
            console.log(error)
          })
      },
      //2 查询所有分类
      initSubject() {
        competitionApi.getAllSubject()
          .then(response => {
            debugger
            this.subjectNestedList = response.data.data.list
          })
      },
      //分页切换的方法
      //参数是页码数
      gotoPage(page) {
        debugger
        competitionApi.getCompetitionPageList(page, this.limit ,this.compObj)
          .then(response => {
            debugger
            this.data = response.data.data
          })
      },
      
      searchOne(subjectParentId, index) {
        debugger
        //把传递index值赋值给oneIndex,为了active样式生效  页面样式
        this.oneIndex = index
        this.compObj.level = subjectParentId
        //点击某个一级分类进行条件查询
        this.gotoPage(1)
      },

      //6 根据参赛人数排序
      searchBuyCount() {
        //设置对应变量值，为了样式生效
        this.hotSort = "1"
        this.gmtCreateSort = ""
        this.priceSort = ""

        //把值赋值到compObj
        this.compObj.hotSort = this.hotSort
        this.compObj.gmtCreateSort = this.gmtCreateSort;
        this.compObj.priceSort = this.priceSort;

        //调用方法查询
        this.gotoPage(1)
      },

      //7 最新排序
      searchGmtCreate() {
        //设置对应变量值，为了样式生效
        this.hotSort = ""
        this.gmtCreateSort = "1"
        this.priceSort = ""

        //把值赋值到compObj
        this.compObj.hotSort = this.hotSort
        this.compObj.gmtCreateSort = this.gmtCreateSort;
        this.compObj.priceSort = this.priceSort;

        //调用方法查询
        this.gotoPage(1)
      },

      //8 价格排序
      searchPrice() {
        //设置对应变量值，为了样式生效
        this.hotSort = ""
        this.gmtCreateSort = ""
        this.priceSort = "1"

        //把值赋值到compObj
        this.compObj.hotSort = this.hotSort
        this.compObj.gmtCreateSort = this.gmtCreateSort;
        this.compObj.priceSort = this.priceSort;

        //调用方法查询
        this.gotoPage(1)
      },


       view(id) {
        if(id!=null)
          this.$router.push({path: `/competition/${id}`})
        else{
          this.$router.push({path: `/file`})          
        }
          

      }

    }
  
  };
</script>
<style scoped>
  .active {
    background: #bdbdbd;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>
