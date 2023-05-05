package com.cyx.auth.exception;

import com.cyx.common.resp.RespBeanEnum;

public class TokenNotMatchException extends RuntimeException {
    private RespBeanEnum respBeanEnum;

    public RespBeanEnum getRespBeanEnum() {
        return respBeanEnum;
    }

    public void setRespBeanEnum(RespBeanEnum respBeanEnum) {
        this.respBeanEnum = respBeanEnum;
    }

    public TokenNotMatchException(RespBeanEnum respBeanEnum){
        this.respBeanEnum=respBeanEnum;
    }
}
