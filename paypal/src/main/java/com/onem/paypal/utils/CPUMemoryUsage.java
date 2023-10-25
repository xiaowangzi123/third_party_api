package com.onem.paypal.utils;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

/**
 * @author wyq
 * @date 2023/10/25
 * @desc
 */
public class CPUMemoryUsage {

    public static void main(String[] args) {
        OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        // 获取系统的可用处理器数量
        int availableProcessors = osBean.getAvailableProcessors();

        // 获取系统的CPU负载
        double systemLoadAverage = osBean.getSystemLoadAverage();

        // 计算CPU利用率
        double cpuUsage = (1.0 - systemLoadAverage / availableProcessors) * 100.0;

        System.out.println("CPU利用率: " + cpuUsage + "%");

        // 获取Java虚拟机的内存信息
        Runtime runtime = Runtime.getRuntime();

        // 获取可用内存
        long freeMemory = runtime.freeMemory();

        // 获取总内存
        long totalMemory = runtime.totalMemory();

        // 获取最大内存
        long maxMemory = runtime.maxMemory();

        System.out.println("可用内存: " + freeMemory / (1024 * 1024) + " MB");
        System.out.println("总内存: " + totalMemory / (1024 * 1024) + " MB");
        System.out.println("最大内存: " + maxMemory / (1024 * 1024) + " MB");

    }
}
