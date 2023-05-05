package com.cyx.auth.handler;

import com.cyx.auth.constant.KaptchaContant;
import com.cyx.auth.constant.MemberContant;
import com.cyx.auth.converter.MemberToUserConverter;
import com.cyx.auth.entity.User;
import com.cyx.auth.to.TokenTo;
import com.cyx.common.entity.Member;
import com.cyx.common.resp.RespBean;
import com.cyx.common.resp.RespBeanEnum;
import com.cyx.common.utils.JwtUtil;
import com.cyx.common.utils.UUIDUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

//登录成功处理器
@Component
public class AuthLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String addr = request.getRemoteAddr();
//        输出登录token
        redisTemplate.delete(KaptchaContant.KAPTCHA_KEY+addr);

        User user = (User)authentication.getPrincipal();
        String access_token = JwtUtil.getToken(user.getId(),MemberContant.TOKEN_EXPIRE);
        String refresh_token = JwtUtil.getToken(user.getId(),MemberContant.TOKEN_EXPIRE);
//        双token
        redisTemplate.opsForValue().set(MemberContant.TOKEN+addr,access_token,MemberContant.TOKEN_EXPIRE,TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(MemberContant.REFRESH_TOKEN,refresh_token,MemberContant.REFRESH_TOKEN_EXPIRE,TimeUnit.SECONDS);

        TokenTo tokenTo=new TokenTo(user.getId(),access_token,refresh_token);
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(RespBean.success(tokenTo));
//        返回成功
        writer.write(res);
    }
}
