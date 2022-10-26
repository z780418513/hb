package com.hb.core.advice;

import com.hb.common.core.Result;
import com.hb.common.enums.SysExceptionEnum;
import com.hb.common.exceptions.BusinessException;
import com.hb.common.exceptions.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
        log.error("全局异常:  {} ===> {}", e.getClass().getName(), e.getMessage());
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
        log.error("校验异常:  {} ===> {}", e.getClass().getName(), e.getMessage());
        String allErrorMsg = e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return Result.error(allErrorMsg);
    }


    /**
     * 请求参数异常 拦截器
     *
     * @param e MissingServletRequestParameterException
     * @return Result
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Result missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("请求参数异常:  {} ===> {}", e.getClass().getName(), e.getMessage());
        return Result.error(e.getMessage());
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
        log.error("认证失败:  {} ===> {}", e.getClass().getName(), e.getMessage());
        if (e instanceof BadCredentialsException) {
            return Result.error(SysExceptionEnum.USERNAME_PASSWORD_ERROR.getMsg());
        }
        return Result.error(e.getMessage());
    }


    /**
     * AccessDeniedException 拦截器
     *
     * @param e AuthenticationException
     * @return Result
     */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public Result accessDeniedExceptionHandler(AccessDeniedException e) {
        log.error("鉴权失败:  {} ===> {}", e.getClass().getName(), e.getMessage());
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
    public Result businessExceptionHandler(BusinessException e) {
        log.error("业务异常:  {} ===> {}", e.getClass().getName(), e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 系统异常 SysException 拦截器
     *
     * @param e SysException
     * @return Result
     */
    @ExceptionHandler({SysException.class})
    @ResponseBody
    public Result sysExceptionHandler(SysException e) {
        log.error("系统失败:  {} ===> {}", e.getClass().getName(), e.getMessage());
        return Result.error(e.getMessage());
    }


}


