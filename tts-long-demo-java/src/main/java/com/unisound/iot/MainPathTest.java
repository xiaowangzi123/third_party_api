package com.unisound.iot;

import java.net.URL;

/**
 * @author :wyq
 * @date ：Created in 2022/3/16
 * @description :
 */
public class MainPathTest {
    public static void main(String[] args) {
        MainPathTest obj = new MainPathTest();
        URL fileURL = obj.getClass().getResource("classpath: text/unisound2.txt");
        System.out.println("获取的地址为：" + fileURL.getFile());

    }
}