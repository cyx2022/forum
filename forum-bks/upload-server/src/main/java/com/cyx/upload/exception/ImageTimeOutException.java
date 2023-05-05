package com.cyx.upload.exception;

import com.cyx.common.resp.RespBeanEnum;

public class ImageTimeOutException extends RuntimeException {
    private RespBeanEnum respBeanEnum;

    public RespBeanEnum getRespBeanEnum() {
        return respBeanEnum;
    }

    public void setRespBeanEnum(RespBeanEnum respBeanEnum) {
        this.respBeanEnum = respBeanEnum;
    }

    public ImageTimeOutException(RespBeanEnum respBeanEnum){
        this.respBeanEnum=respBeanEnum;
    }
    public ImageTimeOutException(){
    }
}
