import request from '@/utils/request'
export default {
  //上传文章
  upload(formdata) {
    return request({
      headers: {'Content-Type': 'multipart/form-data'},
      url: '/upload',
      method: 'post',
      data: formdata
    })
  }
}

