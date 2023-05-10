package com.onem.entity;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wyq
 * @date 2023/5/9
 * @desc
 */
public class Test {
    public static void main(String[] args) {

        SyntaxResultDto dto = new SyntaxResultDto();

        List<String> a1 = new ArrayList<String>();
        List<List<String>> dependencyList=new ArrayList<>();


        String s = "nn(New-11, Japan-2)\n" +
                "conj_and(Sales-111, FoodLine-121)";


        List<String> b1 = Arrays.asList(s.split("\\n"));
        dependencyList.add(b1);

        String s2 = "nn(New-11, Japan-2)\n" +
                "conj_and(Sales-111, FoodLine-121)";

        List<String> b2 = Arrays.asList(s2.split("\\n"));
        dependencyList.add(b2);
        dto.setDependencyList(dependencyList);
        System.out.println(JSON.toJSONString(dto));
    }
}
