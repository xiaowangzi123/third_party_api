package com.unisound.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wyq
 * @date 2023/10/10
 * @desc
 */
public class TestMain {

    public static void main(String[] args) {
//        String s1 = "http://192.168.2.217:9000/transwai-prod/frontend/adfd69042dcc434cabc1b492bf8e2994/transwai-prod.mp4";
        String s1 = "http://192.168.2.217:9000/transwai-prod/transcode/89f9e350-db6d-464a-ac09-a6f06c3125a6/bj.jpg";
        String s2 = "/"+"transwai-prod"+"/";
        int index = s1.indexOf(s2);
        String substring = s1.substring(s1.lastIndexOf(s2) + s2.length());
        System.out.println(substring);

        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String srcSeg = iterator.next();
            if (srcSeg.equals("bbb")) {
                iterator.remove();
            }
        }
        System.out.println(list);
    }
}
