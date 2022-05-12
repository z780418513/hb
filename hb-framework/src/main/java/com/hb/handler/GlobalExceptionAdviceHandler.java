package com.hb.handler;

import com.hb.common.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionAdviceHandler extends Exception {

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
}
