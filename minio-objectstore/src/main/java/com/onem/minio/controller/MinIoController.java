package com.onem.minio.controller;

import com.onem.minio.utils.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
        minioUtils.bucketExist(bucketName);
        return "成功";
    }
}
