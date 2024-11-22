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
public class OrderInfo {

    private String orderId;
    private String orderType;
    private String createTime;
    private List<OrderLine> orderLine;
    private BuyerInfo buyerInfo;
    private List<ExtendParam> extendParams;
}
