package com.huawei.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * @author wyq
 * @date 2024/11/22
 * @desc
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLine {

    private String orderLineId;
    private String chargingMode;
    private String periodType;
    private int periodNumber;
    private String expireTime;
    private List<ProductInfo> productInfo;
    private List<ExtendParam> extendParams;
    private String currency;
    private String currencyAfterDiscount;
}
