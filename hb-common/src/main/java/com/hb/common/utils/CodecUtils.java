package com.hb.common.utils;


import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author zhaochengshui
 * @description
 * @date 2022/10/18
 */
public class CodecUtils {
    /**
     * 默认字符集 UTF_8
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    /**
     * base64 编码
     *
     * @param src source string
     * @return Encode string
     */
    public static String base64Encode(String src) {
        return Base64Utils.encodeToString(src.getBytes(DEFAULT_CHARSET));
    }

    /**
     * base64 解码
     *
     * @param src source string
     * @return decode string
     */
    public static String base64DecodeStr(String src) {
        byte[] bytes = Base64Utils.decodeFromString(src);
        return new String(bytes, DEFAULT_CHARSET);
    }

    /**
     * md5 32位加密
     *
     * @param src source string
     * @return decode string
     */
    public static String md5Encode32(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes(DEFAULT_CHARSET));
    }

    /**
     * md5 16位加密(32位中9～24位部分)
     *
     * @param src source string
     * @return decode string
     */
    public static String md5Encode16(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes(DEFAULT_CHARSET)).substring(8, 24);
    }

    public static void main(String[] args) {
        String s = CodecUtils.md5Encode32("中午");
        String s2 = CodecUtils.md5Encode16("中午");
        System.out.println(s);
        System.out.println(s2);
    }
}
