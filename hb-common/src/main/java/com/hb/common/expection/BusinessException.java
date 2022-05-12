package com.hb.common.expection;

import com.hb.common.enums.BusinessExceptionEnum;

/**
 * 业务异常类
 * @author hanbaolaoba
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(BusinessException message) {
        super(message);
    }

    public BusinessException(BusinessExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
    }


}
