package com.hb.annotations;

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
     * 日志描述信息
     *
     * @return
     */
    String description() default "";
}
