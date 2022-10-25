package com.hb.core.annotations;

import com.hb.common.enums.BusinessTypeEnum;

import java.lang.annotation.*;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/8/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {
    /**
     * 模块
     */
    String module() default "";

    /**
     * 功能
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;

}
