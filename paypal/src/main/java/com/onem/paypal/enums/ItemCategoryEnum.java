package com.onem.paypal.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * paypay商品类型
 */

@NoArgsConstructor
@AllArgsConstructor
public enum ItemCategoryEnum {

    DIGITAL_GOODS("DIGITAL_GOODS","数字商品"),
    PHYSICAL_GOODS("PHYSICAL_GOODS","实体商品"),
    DONATION("DONATION","捐赠");

    private String dict;
    private String desc;
}
