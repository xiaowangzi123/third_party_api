package com.huawei.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wyq
 * @date 2024/11/29
 * @desc
 */
public class JackSonMapTest {

    public static void main(String[] args) throws Exception {
        // 假设你的 Map 数据是这样
        Map<String, Object> tenantSyncReqMap = new HashMap<>();
        tenantSyncReqMap.put("instanceId", "huaiweitest123456");
        tenantSyncReqMap.put("tenantId", "68cbc86abc2018ab880d92f36422fa0e");
        tenantSyncReqMap.put("flag", "1");
        tenantSyncReqMap.put("testFlag", "1");
        tenantSyncReqMap.put("timeStamp", "20241129134456052");
        tenantSyncReqMap.put("orderId", "CS1906666666ABCDE");
        tenantSyncReqMap.put("tenantCode", "example.com");
        tenantSyncReqMap.put("name", "huaweitenantname");
        tenantSyncReqMap.put("domainName", "https://example.tenantaccount.com");

        // 使用 ObjectMapper 将 Map 转换为 TenantSyncReq
        ObjectMapper objectMapper = new ObjectMapper();
        TenantSyncReq tenantSyncReq = objectMapper.convertValue(tenantSyncReqMap, TenantSyncReq.class);

        // 输出结果
        System.out.println(tenantSyncReq);
    }
}
