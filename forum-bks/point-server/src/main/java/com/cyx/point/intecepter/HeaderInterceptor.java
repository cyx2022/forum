package com.cyx.point.intecepter;

import com.cyx.common.exception.GlobalException;
import com.cyx.common.resp.RespBeanEnum;
import com.cyx.common.holder.MemberIdHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//获取GateWay在header设置的memberId
@Component
public class HeaderInterceptor extends InterceptorRegistry implements HandlerInterceptor {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        来自auth的请求,放行
        if(request.getHeader("relief")!=null){
            return true;
        }
//        if(request.getRequestURI().equals("/swagger-ui.html")) return true;
        Long memberId=Long.valueOf(request.getHeader("memberId"));
        if(memberId==null){
//            不是来自网关的请求,非法请求
            throw new GlobalException(RespBeanEnum.INVALID_REQUEST);
        }else{
            MemberIdHolder.setMemberId(memberId);
            return true;
        }

    }
}
