package com.hb.common.exceptions;

import com.hb.common.enums.SysExceptionEnum;

/**
 * @author zhaochengshui
 * @description 系统异常类
 * @date 2022/8/24
 */
public class SysException extends BaseException{

    public SysException(String message) {
        super(message);
    }

    public SysException(SysExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
    }
}
