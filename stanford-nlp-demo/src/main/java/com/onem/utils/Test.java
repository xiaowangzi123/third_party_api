package com.onem.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wyq
 * @date 2023/5/6
 * @desc
 */
public class Test {
    public static void main(String[] args) {
        List<String> objects = Collections.emptyList();
        System.out.println(objects.size());
        List<String> arr = new ArrayList<String>();
        for (int i = 51; i <= 100; i++) {
            arr.add("\"" + i + "\"");
        }
        System.out.println(arr);

        List<List<String>> lists = splitList(arr, 8);
        System.out.println(lists.size());
    }

    private static <T> List<List<T>> splitList(List<T> list, int subNum) {
        List<List<T>> tNewList = new ArrayList<List<T>>();
        int priIndex = 0;
        int lastPriIndex = 0;
        int insertTimes = list.size() / subNum;
        List<T> subList = new ArrayList<>();
        for (int i = 0; i <= insertTimes; i++) {
            priIndex = subNum * i;
            lastPriIndex = priIndex + subNum;
            if (i == insertTimes) {
                subList = list.subList(priIndex, list.size());
            } else {
                subList = list.subList(priIndex, lastPriIndex);
            }
            if (subList.size() > 0) {
                tNewList.add(subList);
            }
        }
        return tNewList;
    }
}
