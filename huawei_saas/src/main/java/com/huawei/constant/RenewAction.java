package com.huawei.constant;

/**
 * 授权码变更场景
 */
public interface RenewAction {
    /**
     * 转正试用
     */
    String SUB = "TRIAL_TO_FORMAL";

    /**
     * 续费
     */
    String RENEW = "RENEWAL";

    /**
     * 退续费
     */
    String UNSUB = "UNSUBSCRIBE_RENEWAL_PERIOD";
}
