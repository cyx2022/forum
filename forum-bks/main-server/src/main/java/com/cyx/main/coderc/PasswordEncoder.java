package com.cyx.main.coderc;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    public static BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
}
