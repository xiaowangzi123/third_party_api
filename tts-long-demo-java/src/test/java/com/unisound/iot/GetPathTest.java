package com.unisound.iot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

/**
 * @author :wyq
 * @date ：Created in 2022/3/16
 * @description :
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class GetPathTest {

    @Test
    public void test1() {
        URL fileURL = this.getClass().getResource("classpath: text/unisound2.txt");
        System.out.println("获取的地址为：" + fileURL.getFile());
    }

}