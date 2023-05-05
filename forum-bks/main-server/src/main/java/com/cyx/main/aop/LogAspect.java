package com.cyx.main.aop;

import com.cyx.common.entity.log.OptLog;
import com.cyx.common.utils.IpUtil;
import com.cyx.common.utils.JsonUtil;
import com.cyx.main.annotation.OperatorLog;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.service.log.OptLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

//记录操作日志
@Component
@Aspect
public class LogAspect {
    @Resource
    @Qualifier("logThreadPoolExecutor")
    ThreadPoolExecutor executor;

    @Resource
    OptLogService optLogService;

    @Pointcut("@annotation(com.cyx.main.annotation.OperatorLog)")
    public void pt(){};

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Long begin=System.currentTimeMillis();
        Object res = joinPoint.proceed();
        long end=System.currentTimeMillis();
        long time=end-begin;
        recordLog(joinPoint,time);
        return res;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        OptLog log = createLog(joinPoint, time);
        executor.submit(()->{
            optLogService.save(log);
        });

    }

    private OptLog createLog(ProceedingJoinPoint joinPoint, long time){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        OperatorLog operatorLog=method.getAnnotation(OperatorLog.class);
        OptLog optLog=new OptLog();
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        String ip = IpUtil.getClientIp(request);
        optLog.setMemberId(MemberIdHolder.getMemberId());
        optLog.setIp(ip);
        operatorLog.operator();
        optLog.setModule(operatorLog.module());
        optLog.setOperator(operatorLog.operator());
        optLog.setTime(new Date());
        optLog.setCost(time);
        optLog.setUrl(request.getRequestURI());
        Object[] args = joinPoint.getArgs();
        String s = JsonUtil.object2JsonStr(args);
        optLog.setArgs(s);
        return optLog;
    }


}
