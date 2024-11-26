package com.huawei.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author wyq
 * @date 2024/11/22
 * @desc
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyerInfo {
    private String mobilePhone;
    private String email;
    private String customerId;
    private String customerName;
    private String customerRealName;
    private int customerType;
}
