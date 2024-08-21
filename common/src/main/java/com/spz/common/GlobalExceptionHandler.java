package com.spz.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice({"spz.com.**.controller", "spz.com.**.interceptor", "spz.com.**.service"})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    // 全局异常捕获 逐步明确错误
    @ExceptionHandler(Exception.class)
    public Res<String> handleException(Exception e) {
        log.error("全局异常捕获:{}", e.getMessage());
        return Res.error("未知错误");
    }
}
