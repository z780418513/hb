package com.hb.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA工具类
 *
 * @author zhaochengshui
 * @description 用于RSA操作
 * @date 2022/10/19
 */
@Slf4j
public class RsaUtils {

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "RSA";

    /**
     * 写入公钥，私钥对到指定路径，返回KeyPair
     *
     * @param publicPath  公钥保存路径
     * @param privatePath 私钥保存路径
     * @return KeyPair
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static KeyPair writeToFile(String publicPath, String privatePath) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());

        // 写入文件
        FileUtils.writeStringToFile(new File(privatePath), privateKeyStr, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(new File(publicPath), publicKeyStr, StandardCharsets.UTF_8);
        return keyPair;
    }

    /**
     * 根据指定路径获取公钥
     *
     * @param publicPath 公钥路径
     * @return 公钥
     */
    public static PublicKey getPublicKey(String publicPath) {
        try {
            // 读取文件
            String publicKeyStr = FileUtils.readFileToString(new File(publicPath), StandardCharsets.UTF_8);
            // 公钥
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;

        } catch (Exception e) {
            log.error("RsaUtils getPublicKey fail publicPath: {}, errorMsg: {}", publicPath, e);
        }
        return null;
    }

    /**
     * 根据指定路径获取私钥
     *
     * @param privatePath 私钥路径
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String privatePath) {
        try {
            String privateKeyStr = FileUtils.readFileToString(new File(privatePath), StandardCharsets.UTF_8);
            // 私钥
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            return privateKey;
        } catch (Exception e) {
            log.error("RsaUtils getPrivateKey fail privatePath: {}, errorMsg: {}", privatePath, e);
        }
        return null;
    }

    /**
     * 加密
     * 公钥加密==》私钥解密
     * 私钥加密==》公钥解密
     *
     * @param toEncryptStr 代加密字符串
     * @param key          公钥或私钥
     * @return 加密后base64字符串
     */
    public static String encrypt(String toEncryptStr, Key key) {
        try {
            // 创建加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 加密初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] encryptBytes = cipher.doFinal(toEncryptStr.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encryptBytes);
        } catch (Exception e) {
            log.error("RsaUtils encrypt fail toEncryptStr: {}, errorMsg: {}", toEncryptStr, e);
        }
        return null;
    }

    /**
     * 解密
     * 公钥加密==》私钥解密
     * 私钥加密==》公钥解密
     *
     * @param toDecryptStr 代解密字符串
     * @param key          公钥或私钥
     * @return 加密后base64字符串
     */
    public static String decrypt(String toDecryptStr, Key key) {
        byte[] toDecryptBytes = Base64.decodeBase64(toDecryptStr);
        try {
            // 创建加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 解密初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] encryptBytes = cipher.doFinal(toDecryptBytes);
            return new String(encryptBytes);
        } catch (Exception e) {
            log.error("RsaUtils decrypt fail toEncryptStr: {}, errorMsg: {}", toDecryptStr, e);
        }
        return null;
    }

    public static void main(String[] args) {
        String publicPath = "/Users/zhaochengshui/code/demo/netty-demo/src/test/java/rsa.pu";
        String privatePath = "/Users/zhaochengshui/code/demo/netty-demo/src/test/java/rsa.pr";
        PublicKey publicKey = getPublicKey(publicPath);
        PrivateKey privateKey = getPrivateKey(privatePath);
        String encrypt = encrypt("大河向东流", publicKey);
        System.out.println("加密后： " + encrypt);
        String decrypt = decrypt(encrypt, privateKey);
        System.out.println("解密后： " + decrypt);
    }
}
