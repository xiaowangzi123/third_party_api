package com.example.obs.sample;

import com.obs.services.ObsClient;
import com.obs.services.model.AccessControlList;

/**
 * @author wyq
 * @date 2024/4/16
 * @desc
 */
public class GetObject {
    private static final String endPoint = "obs.cn-south-1.myhuaweicloud.com";
    private static final String ak = "2CNBVV3R4B1H2JZ6AHAT";
    private static final String sk = "g9nMiK6vFqTwxGQfUkUyE9J5DO2gaqhJT8vh4HqX";
    private static final String bucketName = "transwai-dev";

    public static void main(String[] args) {

        try (ObsClient obsClient = new ObsClient(ak, sk, endPoint)) {
            // 获取对象访问权限
            AccessControlList acl = obsClient.getObjectAcl(bucketName, "060b1f36-a2b8-4d61-a3dc-55a301774ce2/");
//            AccessControlList acl = obsClient.getObjectAcl(bucketName, "060b1f36-a2b8-4d61-a3dc-55a301774ce2/20zh.mp4");
            System.out.println("getObjectAcl successfully");
            System.out.println(acl);
        } catch (Exception e) {
            System.out.println("getObjectAcl failed");
            // 其他异常信息打印
            e.printStackTrace();
        }
    }
}
