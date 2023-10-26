package com.onem.paypal.utils;

/**
 * @author wyq
 * @date 2023/10/25
 * @desc
 */
public class MemoryTest {

    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();

        // 获取可用内存
        long freeMemory = runtime.freeMemory();

        // 获取总内存
        long totalMemory = runtime.totalMemory();

        //不正确
        System.out.println("可用内存: " + freeMemory / (1024 * 1024) + " MB");
        System.out.println("总内存: " + totalMemory / (1024 * 1024) + " MB");

    }
}
