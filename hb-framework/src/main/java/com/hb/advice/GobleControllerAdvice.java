package com.hb.advice;

import com.hb.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GobleControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(GobleControllerAdvice.class); //日志记录器

    /**
     * 全局Exception异常拦截器
     *
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        log.error("exceptionHandler===>", e);
        return Result.error(e.getMessage());
    }

    /**
     * 校验异常 validation 拦截器
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public Result bindExceptionHandler(BindException e) {
        log.error("bindExceptionHandler===>{}", e.getAllErrors());
        return Result.error(e.getAllErrors().get(0).getDefaultMessage());
    }
}


