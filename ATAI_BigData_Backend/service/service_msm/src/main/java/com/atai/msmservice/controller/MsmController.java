package com.atai.msmservice.controller;

import com.atai.commonutils.result.R;
import com.atai.commonutils.util.SendMail;
import com.atai.msmservice.service.MsmService;
import com.atai.msmservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(description="短信服务")
@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送邮件的方法
    @ApiOperation(value = "发送邮件")
    @GetMapping("sendByEmail/{email}")
    public R sendEmail(@PathVariable String email){
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)) {
            return R.success();
        }

        //2 如果redis获取 不到，进行邮件发送
        //生成随机值
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        String senderName = "ATAIEmail";
        String subject = "ATAI大数据竞赛平台用户注册";
        String content = "本次验证码为:"+code+",验证码有效时间为5分钟。";
        //调用s发送邮件的方法
        try {
            SendMail.senEmail(email,senderName,email,subject,content);
            redisTemplate.opsForValue().set(email,code,5, TimeUnit.MINUTES);
            return R.success();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("邮件发送失败");
        }
    }


    //发送短信的方法
    @ApiOperation(value = "发送短信")
    @GetMapping("send/{phoneNumber}")
    public R sendMsm(@PathVariable String phoneNumber){
        //1 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if(!StringUtils.isEmpty(code)) {
            return R.success();
        }

        //2 如果redis获取 不到，进行阿里云发送
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);

        //调用service发送短信的方法
        boolean isSend = msmService.send(param,phoneNumber);
        if(isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(phoneNumber,code,5, TimeUnit.MINUTES);
            return R.success();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
