package com.huawei.util.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantSyncReq {

    private String instanceId;
    private String tenantId;
    private Integer flag;
    private String timeStamp;
    private String orderId;
    private String tenantCode;
    private String name;
    private String domainName;

}
