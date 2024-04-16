package com.example.obs.sample;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;

/**
 * This sample demonstrates how to delete objects under specified bucket 
 * from OBS using the OBS SDK for Java.
 * huaweiCloud:
 *   obs:
 *    endPoint: obs.cn-south-1.myhuaweicloud.com
 *    path: https://transwai-dev.obs.cn-south-1.myhuaweicloud.com/
 *   #  path: https://transwai-prod.obs.cn-south-1.myhuaweicloud.com/
 *    ak: 2CNBVV3R4B1H2JZ6AHAT
 *    sk: g9nMiK6vFqTwxGQfUkUyE9J5DO2gaqhJT8vh4HqX
 *   bucketName: transwai-dev
 *   # bucketName: transwai-prod
 */
public class DeleteObjectsSample {
    private static final String endPoint = "obs.cn-south-1.myhuaweicloud.com";
    private static final String ak = "2CNBVV3R4B1H2JZ6AHAT";
    private static final String sk = "g9nMiK6vFqTwxGQfUkUyE9J5DO2gaqhJT8vh4HqX";
    private static ObsClient obsClient;
    private static String bucketName = "transwai-dev";

    public static void main(String[] args) {

        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(endPoint);

    }
}
