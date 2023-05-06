package com.onem.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wyq
 * @date 2023/5/6
 * @desc
 */
public class Test {
    public static void main(String[] args) {
        List<String> arr = new ArrayList<String>();
        for (int i = 51; i <= 100; i++) {
            arr.add("\"" + i + "\"");
        }
        System.out.println(arr);

    }
}
