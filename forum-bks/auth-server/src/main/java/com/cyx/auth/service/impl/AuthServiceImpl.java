package com.cyx.auth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.TypeReference;
import com.cyx.auth.constant.KaptchaContant;
import com.cyx.auth.exception.KaptchaNotMatchException;
import com.cyx.auth.feign.MainFeignService;
import com.cyx.auth.service.AuthService;
import com.cyx.auth.to.MemberTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MainFeignService memberFeignService;

    @Override
    public Long register(MemberTo memberTo, HttpServletRequest request) {
        String addr = request.getRemoteAddr();
        String s = redisTemplate.opsForValue().get(KaptchaContant.KAPTCHA_KEY + addr);
//        校验验证码
        if(ObjectUtil.isEmpty(s)||!s.substring(0,4).equalsIgnoreCase(memberTo.getCode())){
            throw new KaptchaNotMatchException("验证码已过期");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        memberTo.setPassword(bCryptPasswordEncoder.encode(memberTo.getPassword()));
        Long id=(Long)memberFeignService.add(memberTo).getObject(new TypeReference<Long>(){});
        return id;
    }
}
