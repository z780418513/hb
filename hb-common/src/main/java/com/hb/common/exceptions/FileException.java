package com.hb.common.exceptions;

import com.hb.common.enums.BusinessExceptionEnum;

/**
 * @author zhaochengshui
 * @description 文件异常类
 * @date 2022/9/26
 */
public class FileException extends BusinessException {
    public FileException(String message) {
        super(message);
    }

    public FileException(BusinessExceptionEnum exceptionEnum) {
        super(exceptionEnum);
    }
}
