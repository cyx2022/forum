package com.cyx.main.aop;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;
import com.cyx.common.exception.GlobalException;
import com.cyx.common.resp.RespBean;
import com.cyx.common.resp.RespBeanEnum;
import com.cyx.common.utils.JsonUtil;
import com.cyx.main.constant.SensitiveProperties;
import com.cyx.main.data.SensitiveData;
import com.cyx.main.utils.HttpHelper;

import com.cyx.main.wrapper.RequestReaderHttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.annotation.Resource;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Aspect
@Component
public class RequestBodyFilterAspect {
    @Resource
    SensitiveData sensitiveData;

    @Pointcut("@annotation(com.cyx.main.annotation.SensitiveFilter)")
    public void pt() {
    }

    ;

    @Before("pt()")
    public void filterRequestBody(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (int i=0;i<args.length;i++) {
            Object arg=args[i];
            if (arg instanceof Long || arg instanceof Integer || arg instanceof Float || arg instanceof Double || arg instanceof Boolean) {
            } else if (arg instanceof String) {
                if(SensitiveProperties.properties.indexOf(arg)!=-1){
                    String replaceStr = sensitiveData.filterWords((String) arg, '*');
                    args[i]=replaceStr;
                }
            } else {
                Class<?> aClass = arg.getClass();
                String s = JsonUtil.object2JsonStr(arg);
                Map<String,Object> map = JsonUtil.jsonStr2Object(s, Map.class);
                map.forEach((key,value)->{
                    if(SensitiveProperties.properties.indexOf(key)!=-1||value instanceof String){
                        String replaceStr = sensitiveData.filterWords((String)value, '*');
                        map.put(key,replaceStr);
                    }
                });
                String ss = JsonUtil.object2JsonStr(map);
                Object replaceObject = JsonUtil.jsonStr2Object(ss, aClass);
                args[i]=replaceObject;
            }
        }

        ((ProceedingJoinPoint) joinPoint).proceed(args);
    }


}
