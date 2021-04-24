<template>
  <div class="app-container">
    <div style="line-height: 40px; font-weight: 700; font-size: 22px; color: #a85a11; margin-left: 10px;">公告列表</div>
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="noticeQuery.title" placeholder="标题"/>
      </el-form-item>
     
      <el-form-item label="添加时间">
        <el-date-picker v-model="noticeQuery.begin" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="noticeQuery.end" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00" />
      </el-form-item>

      <el-button  type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button  type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" element-loading-text="数据加载中" border fit highlight-current-row>

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="标题" width="80" />
    
      <el-table-column prop="content" label="内容" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/notice/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini"  icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="getList" />
  </div>
</template>
<script>

  import noticeApi from '@/api/edu/notice.js'
  export default {
    //写核心代码的位置
    data() { //定义变量和初始值
      return {
        list: null, //查询之后接口返回集合
        page: 1, //当前页
        limit: 10, //每页记录数
        total: 0, //总记录数
        noticeQuery: {} //条件封装的对象 v-model双向绑定
      }
    },
    created() { //页面渲染之前执行，调用method定义的方法
      //调用
      this.getList()
    },
    methods: { //创建具体的方法，调用teacher.js定义的方法
      getList(page = 1) { //公告列表的方法
        this.page = page
        noticeApi.pageNoticeCondition(this.page, this.limit, this.noticeQuery)
          .then(response => { //请求成功
            //response接口返回的数据
            //console.log(response)
            this.list = response.data.records
            this.total = response.data.total
          })
          .catch(error => { //请求失败
            console.log(error)
          })
      },
      resetData() { //清空按钮的方法
        //清空已输入数据
        this.noticeQuery = {}
        //查询所有  恢复未进行条件查询的状态
        this.getList()
      },
      removeDataById(id) { //删除公告按钮的方法
        this.$confirm('此操作将永久删除公告信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          noticeApi.removeNotice(id)
            .then(response => { //删除成功
              //提示信息
              this.$message({
                type: 'success',
                message: '删除成功🤭'
              });
              //回到列表页面
              this.getList()
            })
        })
        //点取消执行catch方法  用户体验角度
        //此处无需进行提示取消
        //框架在./utils/request.js封装好了提示error的方法
      }
    }
  }
</script>


