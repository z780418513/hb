package com.hb.common.exceptions;

import com.hb.common.enums.BusinessExceptionEnum;

/**
 * @author zhaochengshui
 * @description 业务异常类
 */
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(BusinessExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
    }


}
