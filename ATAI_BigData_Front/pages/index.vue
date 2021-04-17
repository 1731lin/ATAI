<template>

  <div>
    <!-- <Message></Message> -->
    <!-- 幻灯片 开始 -->
    <!-- <div v-swiper:mySwiper="swiperOption" style="width:80%;height:80%" >
      <div class="swiper-wrapper">
  
        <div v-for="banner in bannerList" :key="banner.id" class="swiper-slide"  >
          <a target="_blank" :href="banner.linkUrl">
            <img :src="banner.imageUrl" :alt="banner.title">
          </a>
        </div>
      </div>
      <div class="swiper-pagination swiper-pagination-white"></div>
      <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
      <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
    </div> -->
    <!-- 幻灯片 结束 -->
    
  <div>
    <el-table
        :data="tableData1"  
        align="center"
        style="width: 100%;font-size:20px">
        <el-table-column
          prop="notice"
          label="通知公告"
          width="800%" >
            <template slot-scope="scope">
               <!-- <a style="text-decoration:none;" href="scope.row.notice" target="_blank" class="buttonText">{{scope.row.notice}}</a>
                -->
                <div @click="open(scope.row.notice)">
              {{scope.row.notice}}
                </div>
             </template>
        </el-table-column>
        <el-table-column
          prop="date1"
          width="200%">
          <template slot="header" slot-scope="">
                  <a href="" target="_blank">More  </a>
                </template>
        </el-table-column>
      </el-table>
  </div>
      
    <div id="aCoursesList">
            <!-- 网校名师 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">实验室成员</span>
            </h2>
          </header>
          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="teacher in teacherList" :key="teacher.id">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a href="/teacher/1" :title="teacher.name">
                        <img :alt="teacher.name" :src="teacher.avatar">
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a href="/teacher/1" :title="teacher.name" class="fsize18 c-666">{{teacher.name}}</a>
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{teacher.career}}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">{{teacher.intro}}</p>
                    </div>
                  </section>
                </li>

              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac">
              <a href="/teacher/" title="全部成员" class="comm-btn c-btn-2">全部成员</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校名师 结束 -->
     

    </div>
  </div>
</template>

<script>

  //引入调用index.js文件
  import indexApi from '@/api/index'

  export default {

    data() {
      return {
        swiperOption: {
          //配置分页
          pagination: {
            el: '.swiper-pagination' //分页的dom节点
          },
          //配置导航
          navigation: {
            nextEl: '.swiper-button-next', //下一页dom节点
            prevEl: '.swiper-button-prev' //前一页dom节点
          }
        },
        bannerList: [], //banner数组
        courseList: [],
        teacherList: [],
        tableData1: [{
          notice: '《重庆日报》刊发我校马克思主义学院教师文章',
          date1 :'2021-04-06'
        }, {
          notice: '《重庆日报》刊发我校马克思主义学院教师文章',
          date1 :'2021-04-06'
        }, {
          notice: '《重庆日报》刊发我校马克思主义学院教师文章',
          date1 :'2021-04-06'
        }, {
          notice: '《重庆日报》刊发我校马克思主义学院教师文章',
          date1 :'2021-04-06'
        }]
      }
      
    },
    created() {


      this.getHotTeacher()
    },
    methods: {
        open(topic) {
        this.$alert('4月1日 ，《重庆日报》刊发我校马克思主义学院院长徐茂华和马克思主义学院教师苗存龙文章《在党史学习教育中厚植人民情怀》。文章指出，人民情怀贯穿中国共产党的发展历程，是党的精神谱系中最亮丽的底色。我们要认真学习党史，厚植为民情怀，教育引导全党深刻认识党的性质宗旨，坚持一切为了人民、一切依靠人民，不断增强人民群众对党的信任和信心，筑牢党长期执政最可靠的阶级基础和群众根基。要恪守权为民用，学习党史，感悟精神，自觉把为民情怀贯穿于行使权力的全过程和各方面，实现好、维护好、发展好最广大人民根本利益。要坚守群众立场，人民立场是中国共产党的根本政治立场，认真研读党史，牢记党的性质宗旨，始终从群众立场上想问题、看事情、解难题，厚植为民情怀。要办好民生实事，民生连着民心，民心关系国运，要把党史学习教育同为群众办实事结合起来，把学习成效转化为工作动力和成效，以昂扬的姿态和坚强的毅力投身到民族复兴的伟业之中。（全文见附件）', topic, {
          confirmButtonText: '确定',
          customClass: 'winClass',//弹窗样式
          callback: action => {

          }
        });
      },

      //查询名师
      getHotTeacher() {
        indexApi.getHotTeacher()
          .then(response => {
            this.teacherList = response.data.data.teacherList
          })
      }


    }
  }
</script>
<style >
    .winClass{
        width: 600px;
    }
 /*   .btnFalses{
        background-color: #022F92
    }*/
</style >