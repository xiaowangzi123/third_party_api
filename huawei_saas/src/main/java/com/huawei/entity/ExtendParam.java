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
public class ExtendParam {
    private String name;
    private String value;
}
