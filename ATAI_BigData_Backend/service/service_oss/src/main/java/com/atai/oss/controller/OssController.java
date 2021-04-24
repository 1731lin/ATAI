package com.atai.oss.controller;

import com.atai.commonutils.result.R;
import com.atai.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 上传文件部分
 * @author ZengJinming
 * @time 2020-04-03
 */

@Api(description="上传文件")
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "上传头像的方法")
    //上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.success().data("url",url);
    }


    @ApiOperation(value = "文件删除")
    @DeleteMapping("remove")
    public R removeFile(
            @ApiParam(value = "要删除的文件url路径", required = true)
            @RequestBody String url){
        ossService.removeFile(url);
        return R.success().message("文件删除成功");
    }

    /*

        第一次下载结果集，存入redis
        后续直接从redis中存取
        返回map
     */
    @ApiOperation(value = "返回结果集的方法")
    @PostMapping("download")
    public R downloadOssFile(
            @ApiParam(value = "要下载的文件url路径", required = true)
            @RequestBody() String url) throws IOException {
        //第一次存
        Map entries = redisTemplate.boundHashOps(url).entries();
        if(entries.size()<=0){
            //获取上传文件  MultipartFile
            //返回上传到oss的路径
            String result = ossService.downloadOssFile(url,redisTemplate);
        }else {
            for (Object key : entries.keySet()) {
                System.out.println(key + ":" + entries.get(key));
            }
            //校验
        }
        return R.success().data("result",entries);
    }

}
