package com.hb.common.exceptions;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/24
 */
public class BaseException extends RuntimeException {
    public Integer exceptionCode;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.exceptionCode = code;
    }


    public Integer getExceptionCode() {
        return exceptionCode;
    }

}
