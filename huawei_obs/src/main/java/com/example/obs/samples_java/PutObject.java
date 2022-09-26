package com.example.obs.samples_java;

import com.obs.services.ObsClient;

import java.io.File;

/**
 * @author :wyq
 * @date ：Created in 2022/9/26
 * @description :
 */
public class PutObject {
    private static final String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";

    private static final String ak = "Z9WKE1NSR5IHFCWZRYCB";

    private static final String sk = "nkw4R0qIthHd4CsDZccqAwN9elpCYFHvYsqhmSxw";

    private static ObsClient obsClient;

    private static final String bucketName = "picture-702d";

    public static void main(String[] args) {
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        // localfile为待上传的本地文件路径，需要指定到具体的文件名
        obsClient.putObject("bucketname", "objectkey", new File("F:\\Pictures\\1647682760.png"));
    }
}