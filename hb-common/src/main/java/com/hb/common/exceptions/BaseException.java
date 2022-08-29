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

    public BaseException(BaseException e) {
        super(e.getMessage(), e.getCause());
        this.exceptionCode = e.exceptionCode;
    }


    public Integer getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(Integer exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
