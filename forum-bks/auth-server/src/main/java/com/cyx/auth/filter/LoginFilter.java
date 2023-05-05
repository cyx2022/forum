package com.cyx.auth.filter;

import cn.hutool.core.util.ObjectUtil;
import com.cyx.auth.constant.KaptchaContant;
import com.cyx.auth.exception.KaptchaNotMatchException;
import com.cyx.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    StringRedisTemplate redisTemplate;


    private String kaptcha = KaptchaContant.KAPTCHA_KEY;

    public String getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(String kaptcha) {
        this.kaptcha = kaptcha;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, String> userInfo = objectMapper.readValue(request.getInputStream(), Map.class);
                String username = userInfo.get(getUsernameParameter());
                String password = userInfo.get(getPasswordParameter());
                String kaptcha = userInfo.get(getKaptcha());
//                验证码为空,异常
                if (ObjectUtil.isEmpty(kaptcha)) {
                    throw new KaptchaNotMatchException("验证码为空");
                } else {
                    String addr = request.getRemoteAddr();
//                 redis验证验证码
                    String code = String.valueOf(redisTemplate.opsForValue().get(KaptchaContant.KAPTCHA_KEY + addr).substring(0,4));
//                    验证码异常
                    if(!code.equalsIgnoreCase(kaptcha)){
                        throw new KaptchaNotMatchException("验证码错误");
                    }
                }
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, token);
                return this.getAuthenticationManager().authenticate(token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.attemptAuthentication(request,response);
    }
}
