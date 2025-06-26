package com.huawei.util;

public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS("000000", "success."),

    /**
     * 鉴权失败
     */
    INVALID_TOKEN("000001", "token is invalid"),

    /**
     * 请求参数不合法
     */
    INVALID_PARAM("000002", "param is invalid"),

    /**
     * 实例ID不存在（商品续费、过期、资源释放以及升配需要校验）
     */
    INVALID_INSTANCE("000003", "instance is invalid"),

    /**
     * 请求处理中（特殊场景使用，普通ISV请忽略）
     */
    IN_PROCESS("000004", "request is processing"),

    /**
     * 其他内部错误
     */
    OTHER_INNER_ERROR("000005", "other inner error"),

    /**
     * 无可用实例资源分配（新购或升配如果没有可用资源时返回）
     */
    NO_AVAILABLE_RESOURCE("000100", "no available resource"),

    /**
     * app密钥问题
     */
    APP_SECRET_PROBLEM("000101", "something wrong with appSecret");

    /**
     * 错误码
     */
    private final String resultCode;

    /**
     * 错误码说明
     */
    private final String resultMsg;

    ResultCodeEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }
}
