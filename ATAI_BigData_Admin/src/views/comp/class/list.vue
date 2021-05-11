<template>
  <div class="app-container">
    <div style="line-height: 40px; font-weight: 700; font-size: 22px; color: #a85a11; margin-left: 10px;margin-bottom:20px">
      <span>比赛分类列表</span>
      <el-button style="margin-left:700px" size="mini" type="primary" icon="el-icon-search" @click="dialogFormVisible = true">添加</el-button>
    </div>
    <!-- 表格 -->
    <el-table :data="list" element-loading-text="数据加载中" border fit highlight-current-row>
      <el-table-column prop="id" label="主键" width="280" :show-overflow-tooltip="true" />
      <el-table-column prop="title" label="名称" width="280" :show-overflow-tooltip="true" />
  
      <el-table-column prop="gmtCreate" label="添加时间" width="160" :show-overflow-tooltip="true" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">         
          <el-button type="primary" size="mini" icon="el-icon-edit"  @click="idd=scope.row.id;dialogFormVisible = true">修改</el-button>      
          <el-button type="danger" size="mini"  icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>  

    <!-- Form -->
  
    <el-dialog title="比赛分类" :visible.sync="dialogFormVisible">
      <el-form >
        
        <el-form-item label="名称"  label-width="60px">
          <el-input v-model="compLevel.title" autocomplete="off"  />
        </el-form-item>
         <!-- <el-form-item label="排序"  label-width="60px">
        <el-input-number v-model="compLevel.sort"  />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add()">确 定</el-button>
      </div>
    </el-dialog> 
  </div>
</template>

<script>
  //compLevel.js文件
  import compLevelApi from '@/api/comp/compLevel.js'
  export default {
    //写核心代码的位置
    data() { //定义变量和初始值
      return {
        list: null, //查询之后接口返回集合
        dialogFormVisible: false,
        idd: null,
        compLevel: {} //条件封装的对象 v-model双向绑定
      }
    },
    created() { //页面渲染之前执行，调用method定义的方法
      //调用
      this.getList()
    },
    methods: { //创建具体的方法，调用competition.js定义的方法
      getList() { //比赛列表的方法    
        compLevelApi.findAll()
          .then(response => { //请求成功           
            this.list = response.data.items            
          })
          .catch(error => { //请求失败
            console.log(error)
          })
      },
      add(){      
       
        debugger
        if(this.idd!=null){
          //修改
       
          this.compLevel.id=this.idd
          compLevelApi.updateCompLevel(this.compLevel)
          .then(response => { //添加成功
            //提示成功
            this.$message({
              type: 'success',
              message: '编辑成功！ 😄'
            });
            this.getList()
          })
          this.idd=null
        }else{
          //添加
          compLevelApi.addCompLevelInfo(this.compLevel)
          .then(response => { //添加成功
            //提示成功
            this.$message({
              type: 'success',
              message: '添加成功！ 😄'
            });
            this.getList()
          })
        }
         this.dialogFormVisible = false

      },

      removeDataById(id) { //删除比赛按钮的方法
        this.$confirm('此操作将永久删除比赛信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          compLevelApi.removeCompLevel(id)
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

