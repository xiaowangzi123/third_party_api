package com.microsoft.demo.trans;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author wyq
 * @date 2022/11/29
 * @desc
 */
public class MainTest {
    public static void main(String[] args) {
        String jsonArr = "[{\"translations\":[{\"text\":\"返回当前值\",\"to\":\"zh-Hans\"}]},{\"translations\":[{\"text\":\"返回当前值\",\"to\":\"zh-Hans\"}]},{\"translations\":[{\"text\":\"返回当前值\",\"to\":\"zh-Hans\"}]}]";

        List<Aaa> microsoftTransVoList = JSONObject.parseArray(jsonArr, Aaa.class);
        System.out.println(microsoftTransVoList);
    }
}
