package com.onem.minio;

import com.onem.minio.utils.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class MinioTests {

    @Autowired
    private MinioUtils minioUtils;

    @Test
    public void test01() {
    }

}
