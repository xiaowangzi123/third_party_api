package com.onem.paypal.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author wyq
 * @date 2023/10/25
 * @desc
 */
public class CpuTest01 {

    public static void main(String[] args) {
        try {
            //不能再window上运行，
            Process process = Runtime.getRuntime().exec("top -b -n 1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Cpu(s):")) {
                    System.out.println(line);
                    break;
                }
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
