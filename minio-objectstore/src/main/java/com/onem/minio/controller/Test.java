package com.onem.minio.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyq
 * @date 2023/5/11
 * @desc
 */
public class Test {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("1","aa");
        map.put("2","aa");
        System.out.println(String.join(",",map.keySet()));
    }
}
