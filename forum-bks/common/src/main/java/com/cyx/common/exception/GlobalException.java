package com.cyx.common.exception;


import com.cyx.common.resp.RespBeanEnum;

public class GlobalException extends RuntimeException {
    private RespBeanEnum respBeanEnum;

    public RespBeanEnum getRespBeanEnum() {
        return respBeanEnum;
    }

    public void setRespBeanEnum(RespBeanEnum respBeanEnum) {
        this.respBeanEnum = respBeanEnum;
    }

    public GlobalException(RespBeanEnum respBeanEnum){
        this.respBeanEnum=respBeanEnum;
    }
}
