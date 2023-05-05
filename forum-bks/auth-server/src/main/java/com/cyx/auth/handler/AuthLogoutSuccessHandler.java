package com.cyx.auth.handler;

import com.cyx.auth.constant.MemberContant;
import com.cyx.auth.service.AuthService;
import com.cyx.common.resp.RespBean;
import com.cyx.common.resp.RespBeanEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

//退出成功处理器
@Component
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String addr=request.getRemoteAddr();
        
//        输出token
        redisTemplate.delete(MemberContant.TOKEN+addr);
        redisTemplate.delete(MemberContant.REFRESH_TOKEN+addr);

        ObjectMapper objectMapper=new ObjectMapper();
        String res = objectMapper.writeValueAsString(RespBean.success("退出成功"));
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println(res);
    }
}
