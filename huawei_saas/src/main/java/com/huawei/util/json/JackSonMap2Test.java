package com.huawei.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wyq
 * @date 2024/11/29
 * @desc
 */
public class JackSonMap2Test {

    public static void main(String[] args) throws Exception {
        // 假设你的 Map 数据是这样
        Map<String, Object> dataMap = new HashMap<>();

        // 向 Map 中添加数据
        dataMap.put("instanceId", "huaiweitest123456");
        dataMap.put("tenantId", "68cbc86abc2018ab880d92f36422fa0e");
        dataMap.put("flag", 1);
        dataMap.put("testFlag", 1);
        dataMap.put("currentSyncTime", "20241202135516592");
        dataMap.put("timeStamp", "20241202135516592");
        dataMap.put("appId", "0bd452b0-58af-4b98-9bda-6bf59da68d57");

        // 创建用户列表
        List<Map<String, Object>> userList = new ArrayList<>();
        Map<String, Object> user = new HashMap<>();
        user.put("userName", "zhangsan01");
        user.put("name", "张三");
        user.put("position", "系统管理员");
        user.put("orgCode", "123456789");
        user.put("role", "admin");
        user.put("enable", "true");

        // 将用户列表添加到 Map 中
        userList.add(user);
        dataMap.put("userList", userList);

        // 输出 Map 的内容
        System.out.println(dataMap);

        // 使用 ObjectMapper 将 Map 转换为 TenantSyncReq
        ObjectMapper objectMapper = new ObjectMapper();
        AuthSyncReq authSyncReq = objectMapper.convertValue(dataMap, AuthSyncReq.class);

        // 输出结果
        System.out.println(authSyncReq);
    }
}
