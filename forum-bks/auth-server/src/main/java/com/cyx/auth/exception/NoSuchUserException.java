package com.cyx.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class NoSuchUserException extends AuthenticationException {
    public NoSuchUserException(String msg, Throwable t) {
        super(msg, t);
    }

    public NoSuchUserException(String msg) {
        super(msg);
    }
}
