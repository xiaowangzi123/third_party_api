package com.onem.paypal.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @PostMapping("/01")
    public String test01() {
        log.info("----------test 01-----");
        return "success";
    }

    @PostMapping("/02")
    public String test02() throws Exception {
        log.info("----------test 02-----");

        List<Double> res = new ArrayList<>();
        int conut = 0;
        while (conut < 10) {
            SystemInfo systemInfo = new SystemInfo();
            CentralProcessor processor = systemInfo.getHardware().getProcessor();
            long[] prevTicks = processor.getSystemCpuLoadTicks();
            long[] ticks = processor.getSystemCpuLoadTicks();
            long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
            long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
            long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
            long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
            long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
            long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
            long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
            long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
            long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

            double v = 1.0 - (Math.max(0, idle) * 1.0 / Math.max(1, totalCpu));
            System.out.println("输出结果-->" + v);
            String rate = new DecimalFormat("#.##%").format(v);

            log.info("CPU数量 = {},CPU利用率 ={}", processor.getLogicalProcessorCount(), rate);
//            Thread.sleep(1000);
            conut++;
            res.add(v);
        }

        return "CPU利用率" + res.stream().filter(a -> !Objects.isNull(a)).mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    @PostMapping("/03")
    public String systemInfo() {
        log.info("----------test 03-----");
        StringBuilder result = new StringBuilder();

        final long GB = 1024 * 1024 * 1024;
        int count = 0;
        while (count < 10) {
            OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

            String osJson = JSON.toJSONString(operatingSystemMXBean);
            JSONObject jsonObject = JSON.parseObject(osJson);
            double processCpuLoad = jsonObject.getDouble("processCpuLoad") * 100;
            double systemCpuLoad = jsonObject.getDouble("systemCpuLoad") * 100;
            Long totalSwapSpaceSize = jsonObject.getLong("totalSwapSpaceSize");
            log.info("totalSwapSpaceSize:{}", twoDecimal(totalSwapSpaceSize * 1.0 / GB));
            Long totalPhysicalMemorySize = jsonObject.getLong("totalPhysicalMemorySize");
            log.info("totalPhysicalMemorySize:{}", twoDecimal(totalPhysicalMemorySize * 1.0 / GB));

            Long committedVirtualMemorySize = jsonObject.getLong("committedVirtualMemorySize");
            log.info("committedVirtualMemorySize:{}", twoDecimal(committedVirtualMemorySize * 1.0 / GB));

            Long freePhysicalMemorySize = jsonObject.getLong("freePhysicalMemorySize");
            log.info("freePhysicalMemorySize:{}", twoDecimal(freePhysicalMemorySize * 1.0 / GB));

            Long freeSwapSpaceSize = jsonObject.getLong("freeSwapSpaceSize");
            log.info("freeSwapSpaceSize:{}", twoDecimal(freeSwapSpaceSize * 1.0 / GB));

            Runtime runtime = Runtime.getRuntime();
            long availableMemory = runtime.freeMemory();
            log.info("availableMemory:{}", twoDecimal(availableMemory * 1.0 / GB));

            double totalMemory = 1.0 * totalPhysicalMemorySize / GB;
            double freeMemory = 1.0 * freePhysicalMemorySize / GB;
            double memoryUseRatio = 1.0 * (totalPhysicalMemorySize - freePhysicalMemorySize) / totalPhysicalMemorySize * 100;

            result.append("系统CPU占用率: ")
                    .append(twoDecimal(systemCpuLoad))
                    .append("%，")
                    .append("该进程占用CPU：")
                    .append(twoDecimal(processCpuLoad))
                    .append("%");
            System.out.println(result);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
        return result.toString();
    }

    public static double twoDecimal(double doubleValue) {
        BigDecimal bigDecimal = new BigDecimal(doubleValue).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
