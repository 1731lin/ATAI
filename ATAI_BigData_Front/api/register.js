import request from '@/utils/request'
export default {
  //根据手机号发送验证码
  sendCode(phoneNumber) {
    return request({
      url: `/edumsm/msm/send/${phoneNumber}`,
      method: 'get'
    })
  },
 //根据邮箱发送验证码
 sendCodeByEmail(email) {
  return request({
    url: `/edumsm/msm/sendByEmail/${email}`,
    method: 'get'
  })
},

  //注册
  registerMember(formItem) {
    return request({
      url: `/eduucenter/ucenter-member/register`,
      method: 'post',
      data: formItem
    })
  },
}
