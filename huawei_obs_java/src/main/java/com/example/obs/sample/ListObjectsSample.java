package com.example.obs.sample;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.ListObjectsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This sample demonstrates how to list objects under a specified folder of a bucket
 * from OBS using the OBS SDK for Java.
 */
public class ListObjectsSample {
    private static final String endPoint = "obs.cn-south-1.myhuaweicloud.com";
    private static final String ak = "2CNBVV3R4B1H2JZ6AHAT";
    private static final String sk = "g9nMiK6vFqTwxGQfUkUyE9J5DO2gaqhJT8vh4HqX";
    private static ObsClient obsClient;
    private static String bucketName = "transwai-dev";

    public static void main(String[] args) throws UnsupportedEncodingException {
        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(endPoint);
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // 使用临时AK/SK和SecurityToken初始化客户端
        // ObsClient obsClient = new ObsClient(ak, sk, securityToken, endPoint);

        List<ObsObject> allList = new ArrayList<>();

        try {
            // 分页列举全部对象
            ListObjectsRequest request = new ListObjectsRequest(bucketName);
            // 设置每页100个对象
            request.setMaxKeys(100);
            ObjectListing result;
            do {
                result = obsClient.listObjects(request);
                allList.addAll(result.getObjects());
                for (ObsObject obsObject : result.getObjects()) {
//                    System.out.println("ObjectKey:" + obsObject.getObjectKey());
                    System.out.println(obsObject);
                    Date lastModified = obsObject.getMetadata().getLastModified();
                    System.out.println("最后修改时间：" + lastModified);
                }
                request.setMarker(result.getNextMarker());
            } while (result.isTruncated());
        } catch (Exception e) {
            System.out.println("listObjects failed");
            // 其他异常信息打印
            e.printStackTrace();
        }

        System.out.println("---------------------------------------------");
        System.out.println(allList.size());
        System.out.println("---------------------------------------------");
        Date currentDate = new Date();
        List<ObsObject> collect = allList.stream().filter(a -> {
            Date lastModified = a.getMetadata().getLastModified();
            long timeDifference = currentDate.getTime() - lastModified.getTime();
            //再大有越界风险
            long oneYearInMillis = 365 * 24 * 60 * 60 * 1000L;
//            long oneYearInMillis = 2 * 365 * 24 * 60 * 60 * 1000L;
            return timeDifference > oneYearInMillis;
        }).collect(Collectors.toList());
        System.out.println(collect.size());
        System.out.println("---------------------------------------------");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        String year = format.format(y);
        System.out.println("过去一年：" + year);

        System.out.println(y.compareTo(currentDate));

        List<ObsObject> collect2 = allList.stream().filter(a -> {
            Date lastModified = a.getMetadata().getLastModified();
            return y.compareTo(lastModified) > 0;
        }).collect(Collectors.toList());

        System.out.println(collect2.size());
        System.out.println("---------------------------------------------");

    }


}
