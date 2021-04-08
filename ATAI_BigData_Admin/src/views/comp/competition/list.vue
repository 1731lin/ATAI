<template>
  <div class="app-container">
    <div style="line-height: 40px; font-weight: 700; font-size: 22px; color: #a85a11; margin-left: 10px;">比赛列表</div>
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="competitionQuery.name" placeholder="比赛名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="competitionQuery.level" clearable placeholder="比赛类型">
          <el-option :value="1" label="新人赛" />
          <el-option :value="2" label="创新应用大赛" />
          <el-option :value="2" label="算法大赛" />
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker v-model="competitionQuery.begin" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="competitionQuery.end" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00" />
      </el-form-item>

      <el-button plain="true" type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button plain="true" type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" v-loading="listLoading" element-loading-text="数据加载中" border fit highlight-current-row>

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" :show-overflow-tooltip="true" />

      <el-table-column label="比赛类型" width="110" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span v-if="scope.row.level===1" >新人赛</span>
          <span v-else-if="scope.row.level===2" >创新应用大赛</span>
          <span v-else-if="scope.row.level===3" >算法大赛</span>
        </template>
      </el-table-column>
      <el-table-column prop="intro" label="比赛简介" :show-overflow-tooltip="true" />
      <el-table-column prop="participants" label="参加人数" width="70" />
      <el-table-column prop="money" label="奖金" width="70" />
      <el-table-column prop="result" label="结果集数据路径" :show-overflow-tooltip="true" />
      <el-table-column prop="cover" label="赛题数据路径" :show-overflow-tooltip="true" />
      <el-table-column prop="deadline" label="截止日期" width="160" :show-overflow-tooltip="true" />
      <el-table-column prop="gmtCreate" label="添加时间" width="160" :show-overflow-tooltip="true" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/comp/edit/'+scope.row.id">
            <el-button type="primary" plain="true" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" plain="true" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="getList" />
  </div>
</template>
<script>
  //引入调用competition.js文件
  import competitionApi from '@/api/comp/competition.js'
  export default {
    //写核心代码的位置
    data() { //定义变量和初始值
      return {
        list: null, //查询之后接口返回集合
        page: 1, //当前页
        limit: 10, //每页记录数
        total: 0, //总记录数
        competitionQuery: {} //条件封装的对象 v-model双向绑定
      }
    },
    created() { //页面渲染之前执行，调用method定义的方法
      //调用
      this.getList()
    },
    methods: { //创建具体的方法，调用competition.js定义的方法
      getList(page = 1) { //比赛列表的方法
        this.page = page
        competitionApi.getCompetitionListPage(this.page, this.limit,this.competitionQuery)
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
        this.competitionQuery = {}
        //查询所有  恢复未进行条件查询的状态
        this.getList()
      },
      removeDataById(id) { //删除比赛按钮的方法
        this.$confirm('此操作将永久删除比赛信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          competitionApi.removeCompetition(id)
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

<!-- <style>
  .el-form-item__label {
    color: #a85a11
  }

  .el-table {
    color: #a85a11;
  }

  .el-table__placeholder {
    color: orange !important;
  }

  .el-input__inner {
    border: 1px solid #a85a11;
    border-radius: 0px;
    background-color: #f9d98e;
    color: #a85a11;
  }


</style> -->
