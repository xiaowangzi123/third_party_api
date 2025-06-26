
package com.huawei.model;

import com.huawei.util.ResultCodeEnum;
import lombok.Data;

@Data
public class IMessageResp {
    private String resultCode = ResultCodeEnum.SUCCESS.getResultCode();

    private String resultMsg = ResultCodeEnum.SUCCESS.getResultMsg();

    private String instanceId;
}