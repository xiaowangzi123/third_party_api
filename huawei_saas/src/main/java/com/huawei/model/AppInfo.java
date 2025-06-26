package com.huawei.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * 应用实例信息
 * 资源开通成功后需要给买家展示的SaaS资源开通结果
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class AppInfo {
    /**
     * 前台地址
     * 客户购买商品后， 可以访问的网站地址。
     */
    private String frontEndUrl;

    /**
     * 管理地址
     * 客户购买商品后，可以访问的管理后台地址。
     */
    private String adminUrl;

    /**
     * 管理员帐号
     * 客户购买商品后，访问服务商管理后台的账号（一般为邮箱和手机号）。
     */
    private String userName;

    /**
     * 管理员初始密码
     * 客户购买商品后，访问服务商管理后台的密码（一般由服务商生成）。
     */
    private String password;

    /**
     * 备注
     * 注意：如果备注包含中文内容，请将中文转换成unicode编码，例如：“中文”可以转换成“\u4e2d\u6587”
     */
    private String memo;
}
