/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2018-2023. All rights reserved.
 */

package com.huawei.demo;

import com.cloud.apigateway.sdk.utils.Client;
import com.cloud.apigateway.sdk.utils.Request;
import com.huawei.model.Constant;
import com.huawei.model.HostName;
import com.huawei.model.SSLCipherSuiteUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
public class OkHttpDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpDemo.class);

    public static void main(String[] args) {
        // Create a new request.
        Request OkHttpRequest = new Request();
        try {
            // 认证用的ak和sk硬编码到代码中或者明文存储都有很大的安全风险，建议在配置文件或者环境变量中密文存放，使用时解密，确保安全；
            // 本示例以ak和sk保存在环境变量中为例，运行本示例前请先在本地环境中设置环境变量HUAWEICLOUD_SDK_AK和HUAWEICLOUD_SDK_SK。
//            OkHttpRequest.setKey(System.getenv("HUAWEICLOUD_SDK_AK"));
//            OkHttpRequest.setSecret(System.getenv("HUAWEICLOUD_SDK_SK"));
            OkHttpRequest.setKey("2CNBVV3R4B1H2JZ6AHAT");
            OkHttpRequest.setSecret("g9nMiK6vFqTwxGQfUkUyE9J5DO2gaqhJT8vh4HqX");
            OkHttpRequest.setMethod("GET");
//            OkHttpRequest.setUrl("your url");
            OkHttpRequest.setUrl("https://mkt.myhuaweicloud.com/api/mkp-openapi-public/global/v1/order/query?orderId=MOCKPERIODYEARNEW");

            OkHttpRequest.addHeader("Content-Type", "text/plain");
            OkHttpRequest.setBody("demo");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return;
        }
        try {
            // Sign the request.
            okhttp3.Request signedRequest = Client.signOkhttp(OkHttpRequest, Constant.SIGNATURE_ALGORITHM_SDK_HMAC_SHA256);
            OkHttpClient client;
            if (Constant.DO_VERIFY) {
                // creat okhttpClient and verify ssl certificate
                HostName.setUrlHostName(OkHttpRequest.getHost());
                client = SSLCipherSuiteUtil.createOkHttpClientWithVerify(Constant.INTERNATIONAL_PROTOCOL);
            } else {
                // creat okhttpClient and do not verify ssl certificate
                client = SSLCipherSuiteUtil.createOkHttpClient(Constant.INTERNATIONAL_PROTOCOL);
            }
            // Send the request.
            Response response = client.newCall(signedRequest).execute();
            // Print the status line of the response.
            LOGGER.info("status: " + response.code());
            // Print the body of the response.
            ResponseBody resEntity = response.body();


            if (resEntity != null) {
                log.info("Processing Body with name and value: {}", resEntity.toString());

                System.out.println("--------------------------------");
                System.out.println(resEntity);
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}