package com.huawei.util;

/**
 * @author wyq
 * @date 2024/11/28
 * @desc
 */
public class MainTest {

    public static void main(String[] args) {

//        String srcText = "this is src text";
        String srcText = "a5591d2c21524c3291cffd370939a565";
        String pubKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAjN9YCujrtKN9t32aFMVl\n" +
                "LlD3A3IIkmuZDjhmBwRHeDm4I6FwqfSml6dO2mJ9KohuNPQ15DxrjgZlRZlZk37a\n" +
                "xZ5u8Vaf9o7YbZ9Pb0JjNjMVzsB45vnVoKce1F7p8OxvTNM+au8K/Q8znJ0laZf8\n" +
                "Ef2DgrKomv6ND48SySdVXr2Rp7UOIQ5PD8c1vX4TPiSQVJIq2ENe7bzncnPfIFyB\n" +
                "p/xKsEf4cmVPkpsOLNZdiE5WlABgrt6Iij3qjKHHadXG6I6ofPQOTNBCEDhO/S+4\n" +
                "GgK8oqTF1b2hckJf5XK+ti9iABsnVZZ3c39tfJFYNilrKBk6c3vpaX7OyQChRtIN\n" +
                "R0OWphQMLEDR3nhY2mTA4EPFfRuGZpDOOjcrpObRJSV4uEQ/zy5+GQ5OW4HosMFN\n" +
                "sKFycngGyRJJzEZz7EmXAl+G57zc1znufdk5/l+YDp25AdP3STsWFlLxouzy8wwN\n" +
                "8aU6l+E0LUivSayUdqXb1wpAM8c8zsiU5pP1+Ywms5KMyGvK0rVdsaZun63xnwew\n" +
                "pxpZnzj9Yd0FAv6i2grz+QNKVOhHvS+z4b4kEQO+2C+4YiFKZzEdM1iIwjpfYgHu\n" +
                "5AcQnmgNUz4/ftgHF2+SFVD1Vj99fV56HuHscrE6f9UfJEQh+95b/+qnA3PThI1m\n" +
                "A67iNX0FeG5TQwmmnXyZT6sCAwEAAQ==";
        String key = "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";

        String accessKey = "88c5d7f4e11fdf98fdc8c47f3dc6c66ce06e2ed9a739f43c26aea84f5c9869c";

        String key2 = "a5591d2c21524c3291cffd370939a565";

        String encryptContent = EncryptUtils.encryptContent(srcText, pubKey, 1);
        System.out.println(encryptContent);
        String decrypt = EncryptUtils.decryptContent(encryptContent, pubKey, 1);
        System.out.println(decrypt);

    }
}
