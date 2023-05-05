package com.cyx.main.handler;

import com.cyx.common.exception.GlobalException;
import com.cyx.common.resp.RespBean;
import com.cyx.common.resp.RespBeanEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//处理通用异常
@RestControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(GlobalException.class)
    public RespBean ExceptionHandler(Exception e) {
        if(e instanceof GlobalException){
            return RespBean.error(((GlobalException) e).getRespBeanEnum());
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
