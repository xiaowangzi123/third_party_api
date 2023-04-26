package com.onem.minio.controller;

import com.onem.minio.utils.MinioUtils;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wyq
 * @date 2023/4/26
 * @desc
 */

@Slf4j
@RestController
@RequestMapping(value = "/minio/file")
public class MinIoController {
    @Resource
    private MinioUtils minioUtils;


    @PostMapping("/bucket/exist")
    public String bucketExists(@RequestParam("bucketName") String bucketName) {
        Boolean exist = minioUtils.bucketExist(bucketName);
        log.info("是否存在：{}",exist);
        return "成功";
    }

    @PostMapping("/bucket/create")
    public String bucketCreate(@RequestParam("bucketName") String bucketName) {
        Boolean exist = minioUtils.makeBucket(bucketName);
        log.info("是否创建成功：{}",exist);
        return "成功";
    }

    @PostMapping("/bucket/delete")
    public String bucketDelete(@RequestParam("bucketName") String bucketName) {
        Boolean exist = minioUtils.removeBucket(bucketName);
        log.info("是否删除成功：{}",exist);
        return "成功";
    }


    @PostMapping("/bucket/list")
    public String bucketList() {
        List<Bucket> allBuckets = minioUtils.getAllBuckets();
        log.info("是否删除成功：{}",JSON.toJSONString(allBuckets));
        return "成功";
    }
}
