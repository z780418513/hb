package com.hb.common.utils;

import java.util.UUID;

/**
 * ID生成工具类
 *
 * @author zhaochengshui
 * @description 生成UUID
 * @date 2022/8/18
 */
public class IdUtils {

    /**
     * 简单UUID
     * 生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
     *
     * @return simpleUUID
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 简单UUID (大写)
     * 生成的是不带-的字符串，类似于：2E8EE372214949FBB1528B553FECED11
     *
     * @return simpleUpperCaseUUID
     */
    public static String simpleUpperCaseUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * UUID (带 -)
     * 生成的UUID是带-的字符串，类似于：a5c8a5e8-df2b-4706-bea4-08d0939410e3
     *
     * @return randomUUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }


}
