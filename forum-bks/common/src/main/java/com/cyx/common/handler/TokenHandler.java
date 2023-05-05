package com.cyx.common.handler;

import com.cyx.common.exception.TokenException;
import com.cyx.common.resp.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenHandler {
    @ExceptionHandler(TokenException.class)
    public RespBean ExceptionHandler(Exception e){
        return RespBean.error(RespBean.error(((TokenException) e).getRespBeanEnum()));
    }
}
