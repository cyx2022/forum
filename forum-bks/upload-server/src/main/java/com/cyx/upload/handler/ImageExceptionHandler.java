package com.cyx.upload.handler;

import com.cyx.common.resp.RespBean;
import com.cyx.upload.exception.ImageTimeOutException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageExceptionHandler {
    @ExceptionHandler(ImageTimeOutException.class)
    public RespBean ExceptionHandler(Exception e) {
//       TODO 进行日志记录,统计有多少用户错时更新
        return RespBean.success();
    }
}
