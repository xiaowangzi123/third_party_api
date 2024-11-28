package com.huawei.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * @author wyq
 * @date 2024/11/28
 * @desc
 */
public class EncryptUtils {

    private static final String CHARSET = "UTF-8";
    private static final int AES256_CBC_PKCS5PADDING = 1;

    public static String encryptContent(String content, String accessKey, int encryptType) {
        String iv = getRandomChars(16);

        int keySize;
        if (AES256_CBC_PKCS5PADDING == encryptType) {
            keySize = 256;
        } else {
            keySize = 128;
        }

        String isvEncryptBody = encryptAESCBCEncode(content, accessKey, iv, keySize);
        return iv + isvEncryptBody;
    }

    /**
     * AES CBC 256位加密
     *
     * @param content 加密内容
     * @param key     加密秘钥
     * @param iv      加密盐值
     * @return 加密结果
     */
    public static String encryptAESCBCEncode(String content, String key, String iv, int keySize) {

        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(key) || StringUtils.isEmpty(iv)) {
            return null;
        }

        try {
            byte[] encrypContent =
                    encryptAESCBC(content.getBytes(CHARSET), key.getBytes(CHARSET), iv.getBytes(CHARSET), keySize);
            if (null != encrypContent) {
                return base_64(encrypContent);
            } else {
                return null;
            }

        } catch (UnsupportedEncodingException e) {
            //error message log
            return null;
        }
    }

    public static byte[] encryptAESCBC(byte[] content, byte[] keyBytes, byte[] iv, int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(keyBytes);
            keyGenerator.init(keySize, secureRandom);
            SecretKey key = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            return cipher.doFinal(content);
        } catch (Exception e) {
            //error message log
            return null;
        }
    }

    public static String base_64(byte[] bytes) {

        try {
            return new String(Base64.encodeBase64(bytes), CHARSET);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 获取随机字符串
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String getRandomChars(int length) {
        StringBuilder randomCharsBuf = new StringBuilder(1024);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {

            // 字母和数字中随机
            if (random.nextInt(2) % 2 == 0) {

                // 输出是大写字母还是小写字母
                int letterIndex = random.nextInt(2) % 2 == 0 ? 65 : 97;
                randomCharsBuf.append((char) (random.nextInt(26) + letterIndex));
            } else {
                randomCharsBuf.append(random.nextInt(10));
            }
        }

        return randomCharsBuf.toString();
    }
}
