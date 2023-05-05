package com.cyx.main.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperatorLog {
//    服务名
    String module() default "";
//    操作名
    String operator() default "";
}
