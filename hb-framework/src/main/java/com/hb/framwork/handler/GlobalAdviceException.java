package com.hb.framwork.handler;

import com.hb.common.Result;
import com.hb.common.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 全局异常捕获拦截器
 *
 * @author hanbaolaoba
 */
@ControllerAdvice
@Slf4j
public class GlobalAdviceException extends Exception {

    /**
     * 捕获全局异常
     *
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result globalException(Exception exception) {
        return Result.error(exception.getMessage());
    }

    /**
     * 捕获校验异常
     *
     * @param argumentNotValidException 方法参数校验异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result notValidException(MethodArgumentNotValidException argumentNotValidException) {
        BindingResult bindingResult = argumentNotValidException.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String msg = allErrors.get(0).getDefaultMessage();
        return Result.error(msg);
    }


    /**
     * 捕获认证异常
     *
     * @param authenticationException 认证异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public Result notValidException(AuthenticationException authenticationException) {
        String msg = "";
        if (authenticationException instanceof UsernameNotFoundException) {
            log.info("【登录失败】" + authenticationException.getMessage());
            msg = "用户名不存在";
        }
        if (authenticationException instanceof LockedException) {
            log.info("【登录失败】" + authenticationException.getMessage());
            msg = "用户被冻结";
        }
        if (authenticationException instanceof BadCredentialsException) {
            log.info("【登录失败】" + authenticationException.getMessage());
            msg = "用户名密码不正确";
        }
        return Result.error(msg);
    }

}
