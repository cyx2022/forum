package com.cyx.auth.exception;


import com.cyx.common.resp.RespBeanEnum;
import org.springframework.security.core.AuthenticationException;

public class KaptchaNotMatchException extends AuthenticationException {
    public KaptchaNotMatchException(String msg, Throwable t) {
        super(msg, t);
    }

    public KaptchaNotMatchException(String msg) {
        super(msg);
    }
}
