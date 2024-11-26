package com.huawei.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

/**
 * 私钥解密
 *
 */
public class KeyPairUtil {
    /** 指定加密算法为RSA */
    private static final String ALGORITHM = "RSA";

    private KeyPairUtil() {
    }

    private static KeyFactory keyFactory;

    static {
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
        }
    }

    /**
     * 解密
     *
     * @param cryptography 密文
     * @param privateKey 私钥
     * @return 返回解密后的明文
     * @throws Exception 异常
     */
    public static String decrypt(String cryptography, String privateKey) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        PrivateKey pk = keyFactory.generatePrivate(priPKCS8);

        // 得到Cipher对象对已用公钥加密的数据进行RSA解密
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
        OAEPParameterSpec oaepParameterSpec = new OAEPParameterSpec("SHA-256", "MGF1",
            new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT);

        cipher.init(Cipher.DECRYPT_MODE, pk, oaepParameterSpec);

        byte[] b1 = Base64.decodeBase64(cryptography);

        // 执行解密操作
        byte[] b = cipher.doFinal(b1);
        return new String(b, StandardCharsets.UTF_8);
    }
}