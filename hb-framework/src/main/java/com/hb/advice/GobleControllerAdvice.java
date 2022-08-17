package com.hb.advice;

import com.hb.common.core.Result;
import com.hb.common.enums.BusinessExceptionEnum;
import com.hb.common.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;


/**
 * 全局异常处理类
 *
 * @author zhaochengshui
 */
@ControllerAdvice
public class GobleControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(GobleControllerAdvice.class);

    /**
     * 全局Exception异常拦截器
     *
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        log.error("exceptionHandler ===> ", e);
        return Result.error(e.getMessage());
    }

    /**
     * 校验异常 validation 拦截器
     *
     * @param e BindException
     * @return Result
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public Result bindExceptionHandler(BindException e) {
        log.error("bindExceptionHandler ===> {}", e.getAllErrors());
        String allErrorMsg = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return Result.error(allErrorMsg);
    }

    /**
     * 认证失败异常 AuthenticationException 拦截器
     *
     * @param e AuthenticationException
     * @return Result
     */
    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public Result bindExceptionHandler(AuthenticationException e) {
        if (e instanceof BadCredentialsException) {
            return Result.error(BusinessExceptionEnum.PASSWORD_ERROR);
        }
        log.error("authenticationException  ===> ", e);
        return Result.error(e.getMessage());
    }

    /**
     * 业务异常 BusinessException 拦截器
     *
     * @param e BusinessException
     * @return Result
     */
    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public Result bindExceptionHandler(BusinessException e) {
        return Result.error(e);
    }

}


