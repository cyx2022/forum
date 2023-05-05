package com.cyx.common.exception;


import com.cyx.common.resp.RespBeanEnum;

//客户端没有携带token token过期  token无法延期
public class TokenException extends RuntimeException{
    private RespBeanEnum respBeanEnum;

    public RespBeanEnum getRespBeanEnum() {
        return respBeanEnum;
    }

    public void setRespBeanEnum(RespBeanEnum respBeanEnum) {
        this.respBeanEnum = respBeanEnum;
    }

    public TokenException(RespBeanEnum respBeanEnum){
        this.respBeanEnum=respBeanEnum;
    }
}
