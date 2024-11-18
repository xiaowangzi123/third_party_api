package com.huawei.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckInstanceInfo extends IMessageResp {
    private InstanceInfo[] info;
}