package com.huawei.constant;

/**
 * license生产接口常量
 */
public interface IsvProduceConstant {
    /**
     * UNIX 时间戳（单位秒），服务商需要校验这个时间戳与当前时间相差不超过60s
     */
    String TIMESTAMP = "timestamp";

    /**
     * 随机字符串，云商店在每次调用时会随机生成，服务商可以通过对这个随机数的缓存来防御API重放攻击
     */
    String NONCE = "nonce";

    /**
     * 加密签名，通过一定的规则对请求进行签名产生的值
     */
    String SIGNATURE = "signature";

    /**
     * 是否为调试请求  1：调试请求  0：非调试请求（空也是非调测）
     */
    String DEBUG_TEST = "1";
}