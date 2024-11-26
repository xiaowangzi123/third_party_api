package com.huawei.constant;

/**
 * 接口类型
 */
public interface Activity {
    /**
     * 创建实例
     */
    String NEW_INSTANCE = "newInstance";

    /**
     * 更新实例
     */
    String REFRESH_INSTANCE = "refreshInstance";

    /**
     * 更新实例状态
     */
    String UPDATE_INSTANCE_STATUS = "updateInstanceStatus";

    /**
     * 释放实例
     */
    String RELEASE_INSTANCE = "releaseInstance";

    /**
     * 查询实例信息
     */
    String QUERY_INSTANCE = "queryInstance";

    /**
     * 实例升级
     */
    String UPGRADE_INSTANCE = "upgradeInstance";

    /**
     * AUTH_SYNC
     */
    String AUTH_SYNC = "authSync";

    /**
     * TENANT_SYNC
     */
    String TENANT_SYNC = "tenantSync";

    /**
     * APPLICATION_SYNC
     */
    String APPLICATION_SYNC = "applicationSync";

    /**
     * SINGLE_ORG_SYNC
     */
    String SINGLE_ORG_SYNC = "singleOrgSync";

    /**
     * ALL_ORG_SYNC
     */
    String ALL_ORG_SYNC = "allOrgSync";
}