package com.example.obs;


import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ListObjectsRequest;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListObjectsTest {


    @Test
    public void Test() {
        // Endpoint以北京四为例，其他地区请按实际情况填写。
        String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";
        String ak = "2CNBVV3R4B1H2JZ6AHAT";
        String sk = "g9nMiK6vFqTwxGQfUkUyE9J5DO2gaqhJT8vh4HqX";
        List<String> dateList = new ArrayList<>();
        List<String> objectList = new ArrayList<>();
        try (final ObsClient obsClient = new ObsClient(ak, sk, endPoint);) {
            ListObjectsRequest request = new ListObjectsRequest("cloudtranslation");
            request.setMaxKeys(1000);
            request.setDelimiter("/");
            ObjectListing result;

            do {
                result = obsClient.listObjects(request);
                for (ObsObject obsObject : result.getObjects()) {
                    if (obsObject.getObjectKey().contains("/")){
                        System.out.println("---->"+obsObject.getObjectKey());
                    }
                    objectList.add(obsObject.getObjectKey());
                    dateList.add(formatDate(obsObject.getMetadata().getLastModified()));
//                    listObjectsByPrefix(obsClient, request, result);
                }
            } while (result.isTruncated());


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------");
        dateList = dateList.stream().sorted().collect(Collectors.toList());
        System.out.println(dateList);
        System.out.println(objectList);
    }


    public static void listObjectsByPrefix(ObsClient obsClient, ListObjectsRequest request, ObjectListing result) throws ObsException {
        for (String prefix : result.getCommonPrefixes()) {
            System.out.println("Objects in folder [" + prefix + "]:");
            request.setPrefix(prefix);
            result = obsClient.listObjects(request);
            for (ObsObject obsObject : result.getObjects()) {
                System.out.println("\t" + obsObject.getObjectKey());
                System.out.println("\t" + obsObject.getOwner());
            }
            listObjectsByPrefix(obsClient, request, result);
        }
    }

    private static String formatDate(Date date) {
        // 使用 SimpleDateFormat 定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 格式化 Date 对象为字符串
        return dateFormat.format(date);
    }
}