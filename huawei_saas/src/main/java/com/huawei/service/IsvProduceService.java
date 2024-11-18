package com.huawei.service;

import com.huawei.model.IMessageResp;

import java.util.Map;

public interface IsvProduceService {

    /**
     * SaaS2.0 调测业务实现
     *
     * @param provisionReq 请求参数
     * @return 调测结果
     */
    IMessageResp processProduceReq(Map<String, String> provisionReq);
}
