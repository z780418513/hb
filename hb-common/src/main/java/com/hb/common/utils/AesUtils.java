package com.hb.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES加解密工具类
 *
 * @author zhaochengshui
 * @description 用于aes加解密计算
 * @date 2022/10/18
 */
@Slf4j
public class AesUtils {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";

    /**
     * aes加密
     *
     * @param toEncryptStr 代加密字符串
     * @param key          16位密钥key
     * @return 加密后BASE64转码字符串
     */
    public static String encrypt(String toEncryptStr, String key) {
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            // 初始化为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] decryptBytes = cipher.doFinal(toEncryptStr.getBytes(StandardCharsets.UTF_8));
            //通过Base64转码返回
            return Base64Utils.encodeToString(decryptBytes);
        } catch (Exception e) {
            log.error("aes encrypt fail toEncrypt string: [{}], err: {}", toEncryptStr, e);
        }
        return null;
    }

    /**
     * aes解密
     *
     * @param decryptStr 代解密字符串(base64位)
     * @param key        16位密钥key
     * @return 解密后源内容
     */
    public static String decrypt(String decryptStr, String key) {
        try {
            byte[] bytes = Base64Utils.decodeFromString(decryptStr);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encryptBytes = cipher.doFinal(bytes);
            return new String(encryptBytes);
        } catch (Exception e) {
            log.error("aes encrypt fail toEncrypt string: [{}], err: {}", decryptStr, e);
        }
        return null;
    }


    public static void main(String[] args) {
        String content = "你好,我是MAX";
        String key = "1234567890123456";
        String encryptResult = encrypt(content, key);
        System.out.println("加密后的结果为:" + encryptResult);
        String decrypetResult2 = decrypt(encryptResult, key);
        System.out.println("解密后的结果为:" + decrypetResult2);
    }


}
