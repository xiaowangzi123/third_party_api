package com.huawei.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author wyq
 * @date 2024/11/27
 * @desc
 *
 * Public Key: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh9aUR4yRsWpfBah/t1HwKeA9a0jEvTPQ+eCv4+q1HypC+hSr501gZOfvtX01lPebayUHpncYx8ZBZnkRgJJdK8oQRS5WtpJnM+QP6A4Y4EMcX9dtSmNS5RfbpXvZ9iI6koeaVNLmkjUHm0F9jel4QMkkS7H6wMxpec/lLI1GpkEgrcMlK2vSIkgj8byr/FmddwvMccaJiAuFpgPlfhemiR3PDNKrd660BI2Jtue4KIr2ERNIy0FtrXiXrrSgNZfx9xRm6YPI8PW6FvGcoH9Hpz3Oj7jhoiBvTQUxEhaYesuSkDHe8qS59pNDbZ90ZSI6Npzp0ai5K9atoN5RmJXkCwIDAQAB
 * Private Key: MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCH1pRHjJGxal8FqH+3UfAp4D1rSMS9M9D54K/j6rUfKkL6FKvnTWBk5++1fTWU95trJQemdxjHxkFmeRGAkl0ryhBFLla2kmcz5A/oDhjgQxxf121KY1LlF9ule9n2IjqSh5pU0uaSNQebQX2N6XhAySRLsfrAzGl5z+UsjUamQSCtwyUra9IiSCPxvKv8WZ13C8xxxomIC4WmA+V+F6aJHc8M0qt3rrQEjYm257goivYRE0jLQW2teJeutKA1l/H3FGbpg8jw9boW8Zygf0enPc6PuOGiIG9NBTESFph6y5KQMd7ypLn2k0Ntn3RlIjo2nOnRqLkr1q2g3lGYleQLAgMBAAECggEAW/2HAUVCzSE6JHzNsPn+aH2BWow3H+JdXTs6ImEqM2bNYMpq+ANAilU/0mpuxOlCCTdUjPkOgQvT8m9+2NFF44e9qPwOoSlYVp3V39LcKdOI/abITp4g3yBfERxkqPjZLa5DZ7pDhzP+aLfbLSW02DrfJ5vqMnK741BU5OzvsVpav51Pxra3Hd0062wVgD4YX5p0nx1isRhL9uRan6McfpxTKnH89xjXgA8c1iW+9FS+q2tZEeXNpdjYtE5yOYQVGIZukuTk5CTQvtBmLmFMzfmlU9qLktldxjV6yVgd3uGaw3FWCMBl3/JF3e2RPr0lQwJ3BDKbrx+o973WwyPtYQKBgQDpIkFZtWqCSQ9oXv9eVJmb5icC1THCCdLQ34m+vQ9pdM5lit5mDD6kbpFmYvErAH2LOGf3AO8vANNRVxyPXtnLQjiM+6T4qZnMo35wR5v+PvqEAxLudnYT9FIfXfbs1UfyCPT/H6q5wiTfhf6vD011HphaQpx1lpWLVHv6NNl90wKBgQCVKVViRg7MeIjno0y3VisU5wSsHbS2baBx9lbLsuPr/jDI6Fq9On2fv6llKGkZfnXZUO8gYpUoTsXKy1SEqZ92G1CIoDkksdn9dyKHhmDLOnkZoJJvFaA5dJ8VK9ddtrTF+vT43mUZbrkKyVNeHnpoIlgj9O0j1kDSbNSyZ5XF6QKBgFaaI/6IFrWpIDEcQO6wrJMuDsXNnns/HPH8Z/XT01rQSi+MQ4tS9x4r+JU5Ie9gckJ6RJyL203xl446tuBHUs5fnhQ5EpJQplJPVZOt9jOe/350id7NYJWPB8ahKC3YGK6/RwtSnx/8D6h85RvwqkxcfinhXq+LraehTaCnazeDAoGAeRI6vvDUlD0GCtaur2VexLGbRkU6aJTlFXt5ChAjFmuVl42aEVxHmYINchFx9YKDD6i04BmErln6C9gsux2fVFNpc+xxVYqSwTyRJ2X4bR0CLL37/fSUv+4cLigOwH9LjiNb8iE5+IG56rptU8jzXFD8L/te/KasqOAzaLfRhvkCgYEA6K0yrHHTFoxQ3XPfVc84LgyQz1SVN6DdAWE38bI2DH19YDEqOCB71Kor2Sv78BnvFhOPWG5J2NXXvLDuvCgUZcEJ3gvkd/mn/yVTH7lAymdyEMrW5gN3FJEI3dW4LcDJfiGsHNfO0BIhtLLZYT6UUKffQv06oJZnA5/Q9L8eeOw=
 */
public class GeneralKey {
    public static void main(String[] args) {

        try {
            // 指定密钥长度
            int keySize = 3072; // 使用3072位密钥

            // 创建一个KeyPairGenerator对象，指定算法为"RSA"
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

            // 初始化KeyPairGenerator对象
            keyGen.initialize(keySize);

            // 生成密钥对
            KeyPair keyPair = keyGen.generateKeyPair();

            // 获取公钥和私钥
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            // 将公钥和私钥转换为PKCS#8格式的字节数组
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());

            // 将公钥和私钥转换为Base64编码的字符串，便于存储和传输
            String publicKeyString = Base64.getEncoder().encodeToString(x509KeySpec.getEncoded());
            String privateKeyString = Base64.getEncoder().encodeToString(pkcs8KeySpec.getEncoded());

            // 输出公钥和私钥
            System.out.println("Public Key (Base64): " + publicKeyString);
            System.out.println("Private Key (Base64): " + privateKeyString);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
