package com.cyx.auth.controller;

import cn.hutool.core.util.ObjectUtil;
import com.cyx.auth.constant.KaptchaContant;
import com.cyx.auth.converter.MemberToUserConverter;
import com.cyx.auth.entity.User;
import com.cyx.auth.service.AuthService;
import com.cyx.auth.service.common.CaptchaService;
import com.cyx.auth.to.MemberTo;
import com.cyx.auth.utils.CaptchaUtil;
import com.cyx.common.entity.Member;
import com.cyx.common.utils.CookieUtil;
import com.cyx.common.utils.ImgResult;
import com.cyx.common.utils.UUIDUtil;
import com.cyx.common.resp.RespBean;
import com.cyx.common.resp.RespBeanEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Api("认证相关")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    CaptchaService captchaService;

    @Autowired
    AuthService authService;

    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    public RespBean verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String addr = request.getRemoteAddr();
        String s = redisTemplate.opsForValue().get(KaptchaContant.KAPTCHA_KEY + addr);
//        一分钟内验证码连续获取
        if(s!=null&&System.currentTimeMillis()-Long.valueOf(s.substring(4))<1000){
            return RespBean.error(RespBeanEnum.ACCESS_LIMIT_REACHED);
        }
        ImgResult imgResult = captchaService.getCaptcha(request, response,addr);
        long time = System.currentTimeMillis();
        //        验证码有效期为10分钟
        redisTemplate.opsForValue().set(KaptchaContant.KAPTCHA_KEY+addr,imgResult.getCode()+time,10*60, TimeUnit.SECONDS);
        return RespBean.success(imgResult);
    }

    @ApiOperation("注册")
    @PostMapping("/sign")
    public RespBean sign(@RequestBody MemberTo to,HttpServletRequest request){
        long res=authService.register(to,request);
        return RespBean.success(res);
    }

}
