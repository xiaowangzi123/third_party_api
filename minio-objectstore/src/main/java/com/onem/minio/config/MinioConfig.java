package com.onem.minio.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wyq
 * @date 2023/4/26
 * @desc
 */
@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    public String endpoint;

    @Value("${minio.accessKey}")
    public String accessKey;

    @Value("${minio.secretKey}")
    public String secretKey;

    @Value("${minio.bucketName}")
    public String bucketName;

    @Bean
    public MinioClient minioClient() throws Exception {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }


}