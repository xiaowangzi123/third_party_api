package com.onem.minio.controller;

import com.onem.minio.utils.MinioUtils;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        log.info("桶数量：{}",allBuckets.size());
        allBuckets.forEach(bucket -> {
            System.out.println(bucket.name());
        });
        return "成功";
    }

    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String upload = minioUtils.upload(file);
        log.info("路径：{}", upload);
        return upload;
    }

    @PostMapping("/file/preview")
    public String uploadFile(@RequestParam("fileName") String fileName) {
        String upload = minioUtils.preview(fileName);
        log.info("路径地址：{}", upload);
        return upload;
    }


    @PostMapping("/delete")
    public boolean deleteFile(@RequestParam("fileName") String fileName) {
        boolean upload = minioUtils.remove(fileName);
        log.info("路径地址：{}", upload);
        return upload;
    }
}
