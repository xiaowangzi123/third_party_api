package com.huawei.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 *
 * 如果不关心 userId 字段：使用 @JsonIgnoreProperties(ignoreUnknown = true) 注解来忽略未知字段。
 * 如果希望处理 userId 字段：确保在 BuyerInfo 类中定义该字段。
 * 如果 JSON 中的字段和 Java 类字段不匹配：使用 @JsonProperty 注解来进行字段映射。
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    private String resultCode;
    private String resultMsg;
    private OrderInfo orderInfo;
}
