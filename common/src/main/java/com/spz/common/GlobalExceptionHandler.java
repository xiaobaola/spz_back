package com.spz.common;

import com.spz.common.GlobalException;
import com.spz.common.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice({"spz.com.**.controller", "spz.com.**.interceptor", "spz.com.**.service"})
//@RestControllerAdvice({"spz.com.**"})
@RestControllerAdvice
//@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    // 全局异常捕获 逐步明确错误
    @ExceptionHandler(GlobalException.class)
    public Res<String> handleGlobalException(GlobalException e) {
        log.error("自定义异常捕获:{}", e.getMessage());
        e.printStackTrace();
        return Res.error(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Res<String> handleException(Exception e) {
        log.error("全局异常捕获:{}", e.getMessage());
        e.printStackTrace();
        return Res.error("未知错误");
    }

}
