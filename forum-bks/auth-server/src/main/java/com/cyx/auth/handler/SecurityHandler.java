package com.cyx.auth.handler;

import com.cyx.common.resp.RespBean;
import com.cyx.common.resp.RespBeanEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class SecurityHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter writer = httpServletResponse.getWriter();
        ObjectMapper objectMapper=new ObjectMapper();
        String res = objectMapper.writeValueAsString(RespBean.error((RespBeanEnum.LOGIN_ERROR)));
        writer.write(res);
    }
}
