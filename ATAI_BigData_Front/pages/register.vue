<template>
  <div class="main">
    <div class="title">
      <a href="/login">登录</a>
      <span>·</span>
      <a class="active" href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="params">

        <el-form-item class="input-prepend restyle" prop="nickname" :rules="[{ required: true, message: '请输入你的昵称', trigger: 'blur' }]">
          <div>
            <el-input type="text" placeholder="你的昵称" v-model="params.nickname"/>
            <i class="iconfont icon-user"/>
          </div>
        </el-form-item>

        <el-form-item class="input-prepend restyle no-radius" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div>
            <el-input type="text" placeholder="手机号" v-model="params.mobile"/>
            <i class="iconfont icon-phone"/>
          </div>
        </el-form-item>

         <el-form-item class="input-prepend restyle no-radius" prop="email" :rules="[{ required: true, message: '请输入注册邮箱', trigger: 'blur' },{validator: checkEmail, trigger: 'blur'}]">
          <div>
            <el-input type="text" placeholder="邮箱" v-model="params.email"/>
            <i class="iconfont icon-phone"/>
          </div>
        </el-form-item>

        <el-form-item class="input-prepend restyle no-radius" prop="code" :rules="[{ required: true, message: '请输入邮箱验证码', trigger: 'blur' }]">
          <div style="width: 100%;display: block;float: left;position: relative">
            <el-input type="text" placeholder="验证码" v-model="params.code"/>
            <i class="iconfont icon-phone"/>
          </div>
          <div class="btn" style="position:absolute;right: 0;top: 6px;width: 40%;">
            <a href="javascript:" type="button" @click="getCodeFun()" :value="codeTest" style="border: none;background-color: none">{{codeTest}}</a>
          </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="设置密码" v-model="params.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>
        <el-radio v-model="yzm" label="1">短信验证码</el-radio>
        <el-radio v-model="yzm" label="2">邮箱验证码</el-radio>
       

        <div class="btn">
          <input type="button" class="sign-up-button" value="注册" @click="submitRegister()">
        </div>
        <br />
        <p class="sign-up-msg">
          点击 “注册” 即表示您同意并愿意遵守简书
          <br>
          <a target="_blank" href="http://www.jianshu.com/p/c44d171298ce">用户协议</a>
          和
          <a target="_blank" href="http://www.jianshu.com/p/2ov8x3">隐私政策</a> 。
        </p>
      </el-form>
      <!-- 更多注册方式 -->
      <!-- <div class="more-sign">
        <h6>社交帐号直接注册</h6>
        <ul>
          <li><a id="weixin" class="weixin" target="_blank" href="http://huaan.free.idcfengye.com/api/ucenter/wx/login"><i
            class="iconfont icon-weixin"/></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div> -->

      
    </div>
  </div>
</template>

<script>
  import '~/assets/css/sign.css'
  import '~/assets/css/iconfont.css'

  //引入调用register.js文件
  import registerApi from '@/api/register'

  export default {
    layout: 'sign',
    data() {
      return {
        params: {  //封装注册输入的数据
          mobile: '',  //手机号
          code: '',  //验证码
          nickname: '',  //昵称
          password: ''        
        },
        yzm: '2',
        valite1: '0',
        valite2: '0',
        sending: true,      //是否发送验证码
        second: 300,        //倒计时间
        codeTest: '获取验证码'
      }
    },
    methods: {
      //注册提交的方法
      submitRegister() {
        registerApi.registerMember(this.params)
          .then(response => {
            //提示注册成功
            this.$message({
              type: 'success',
              message: "注册成功! ✌"
            })
            //跳转到登陆页面
            this.$router.push({path: '/login'})
          })
      },

      //给已经输入的手机号发送验证码
      getCodeFun(){
        debugger
       
           if(this.yzm=="1"){
              if(this.params.mobile==''||this.valite1=='0'){
                  this.$message({
                  type: 'fail',
                  message: "请输入手机号"
                })
                return
              }
              debugger
               //调用倒计时的方法
               this.timeDown()
              registerApi.sendCode(this.params.mobile).then(response => {
                //提示发送验证码成功
                debugger
                this.$message({
                  type: 'success',
                  message: "验证🐎已发送🆗"
                })
                //点完发送  300秒内不让再发
                this.sending = false
               
              })
        }else{
            if(this.params.email==''||this.valite2=='0'){
                  this.$message({
                  type: 'fail',
                  message: "请输入邮箱号"
                })
                return
              }
              debugger
              this.timeDown()
              registerApi.sendCodeByEmail(this.params.email).then(response => {
                //提示发送验证码成功
                debugger
                this.$message({
                  type: 'success',
                  message: "验证🐎已发送🆗"
                })
                //点完发送  300秒内不让再发
                this.sending = false
                //调用倒计时的方法
               
              })
            }
       

      },

      //定时器  发送验证码倒数
      timeDown() {
        let result = setInterval(() => {  //setInterval定时器
            --this.second;
            this.codeTest = this.second
            if (this.second < 1) {
              clearInterval(result);
              this.sending = true;
              //this.disabled = false;
              this.second = 300;
              this.codeTest = "获取验证码"
            }
          }, 1000);
        },

      //手机号格式校验 自定义规则
      checkPhone (rule, value, callback) {
        debugger
        if (!(/^1[34578]\d{9}$/.test(value))) {
          return callback(new Error('手机号码格式不正确'))
        }else{
          this.valite1='1'
        }
        return callback()
      },
      checkEmail (rule, value, callback) {
        debugger
        const mal = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/   
        if (rule.required && !value) {
          return callback(new Error('不能为空'))
        }
        if (value) {
          if (!(mal.test(value))) {
            callback(new Error('请输入正确邮箱'))
          } else {
            this.valite2='1'
            callback()
          }
      }
    }
    },
  }
</script>
