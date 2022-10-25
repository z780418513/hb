package com.hb.core.sensitive;

import java.util.function.Function;

/**
 * 数据脱敏
 *
 * @author zhaochengshui
 * @description 校验数据类型枚举
 * @date 2022/9/30
 */
public enum SensitiveStrategy {
    /**
     * Username sensitive strategy.  $1 替换为正则的第一组  $2 替换为正则的第二组
     */
    USERNAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),
    /**
     * Id card sensitive type.
     */
    ID_CARD(s -> s.replaceAll("(\\d{3})\\d{13}(\\w{2})", "$1****$2")),
    /**
     * Phone sensitive type.
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),
    /**
     * Address sensitive type.
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****"));


    private final Function<String, String> desensitize;

    /**
     * 定义构造函数，传入一个函数
     */
    SensitiveStrategy(Function<String, String> desensitize) {
        this.desensitize = desensitize;
    }

    /**
     * getter方法
     */
    public Function<String, String> desensitize() {
        return desensitize;
    }
}


