package com.example.obs.sample;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.ListObjectsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private static final String ak = "HPUARVBH00YJFYRXVMJE";
    private static final String sk = "gU2KOtpfk2ROCg2gfE3oF7x0OoI42q89eCqaVFtL";
    private static ObsClient obsClient;
    private static String bucketName = "transwai-dev";

    public static void main(String[] args) throws Exception {
        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(endPoint);

        List<ObsObject> allList = new ArrayList<>();
        try (ObsClient obsClient = new ObsClient(ak, sk, endPoint);) {
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
        System.out.println("总数：" + allList.size());
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
        System.out.println("超过一年的数据"+collect.size());
        System.out.println("---------------------------------------------");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -1);
        Date y = calendar.getTime();
        String yearFormat = format.format(y);

        System.out.println("过去一年：" + yearFormat);

        System.out.println(y.compareTo(currentDate));
        LocalDateTime oneYearTime = LocalDateTime.now().minusYears(1);


        List<ObsObject> collect2 = allList.stream().filter(a -> {
            Date lastModified = a.getMetadata().getLastModified();
            return y.compareTo(lastModified) > 0;
        }).collect(Collectors.toList());

        System.out.println(collect2.size());
        System.out.println("---------------------------------------------");

    }


}
