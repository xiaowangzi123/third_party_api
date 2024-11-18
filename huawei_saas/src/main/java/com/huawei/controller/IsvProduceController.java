package com.huawei.controller;

import com.huawei.constant.Activity;
import com.huawei.model.IMessageResp;
import com.huawei.service.IsvProduceService;
import com.huawei.util.IsvProduceAPI;
import com.huawei.util.KeyPairUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.huawei.util.ResultCodeEnum.APP_SECRET_PROBLEM;
import static com.huawei.util.ResultCodeEnum.SUCCESS;

/**
 * SaaS类商品接入示例工程
 */
@RestController
@RequestMapping(value = "isv/produce")
@Slf4j
public class IsvProduceController {
    /**
     * 联营saas同步接口调试绑定密钥
     */
    @Value("${kit_access_key}")
    private String KIT_ACCESS_KEY;

    /**
     * 基础接口调试绑定密钥
     */
    @Value("${sign_access_key}")
    private String SIGN_ACCESS_KEY;

    @Resource
    private IsvProduceService isvProduceService;

    /**
     * 私钥part1
     */
    @Value("${private_key}")
    private String PRIVATE_KEY;

    @GetMapping("/test")
    public String test() {
        log.info("测试：{}", new Date());
        return "success:" + new Date();
    }

    /**
     * 主动通知接口请求处理
     *
     * @param isvProduceReq 请求体
     * @param request       请求
     * @return response 返回
     */
    @PostMapping(value = "")
    @ResponseBody
    public IMessageResp processProduceLicenseReq(@RequestBody Map<String, String> isvProduceReq,
                                                 HttpServletRequest request) throws Exception {
        // 验证签名
        IMessageResp resp = IsvProduceAPI.verifySignature(isvProduceReq, request, SIGN_ACCESS_KEY);
        // 如果鉴权返回成功则进行业务处理 验证入参格式 及异常场景校验
        if (SUCCESS.getResultCode().equals(resp.getResultCode())) {
            resp = isvProduceService.processProduceReq(isvProduceReq);
        }
        return resp;
    }

    /**
     * 租户信息同步
     *
     * @param request
     */
    @PostMapping(value = "/produceAPI/v2/tenantSync")
    public IMessageResp tenantSyncV2(@RequestBody Map<String, Object> tenantSyncReq, HttpServletRequest request) {
        return processKit(tenantSyncReq, request, Activity.TENANT_SYNC);
    }

    /**
     * 应用信息同步
     *
     * @param request
     */
    @PostMapping(value = "/produceAPI/v2/applicationSync")
    public IMessageResp applicationSyncV2(@RequestBody Map<String, Object> applicationSyncReq,
                                          HttpServletRequest request) {

        try {
            // 验证私钥解密：解密后获取到的clientSecret就是页面输入的值
            String clientSecret =
                    KeyPairUtil.decrypt(String.valueOf(applicationSyncReq.get("clientSecret")), PRIVATE_KEY);
        } catch (Exception e) {
            IMessageResp resp = new IMessageResp();
            resp.setResultCode(APP_SECRET_PROBLEM.getResultCode());
            resp.setResultMsg(APP_SECRET_PROBLEM.getResultMsg());
            return resp;
        }

        return processKit(applicationSyncReq, request, Activity.APPLICATION_SYNC);
    }

    /**
     * 授权信息同步
     *
     * @param request
     */
    @PostMapping(value = "/produceAPI/v2/authSync")
    public IMessageResp authSyncV2(@RequestBody Map<String, Object> authSyncReq, HttpServletRequest request) {
        return processKit(authSyncReq, request, Activity.AUTH_SYNC);
    }

    /**
     * 组织部门信息同步（增量）
     *
     * @param request
     */
    @PostMapping(value = "/produceAPI/v2/singleOrgSync")
    public IMessageResp singleOrgSyncV2(@RequestBody Map<String, Object> singleOrgSyncReq, HttpServletRequest request) {
        return processKit(singleOrgSyncReq, request, Activity.SINGLE_ORG_SYNC);
    }

    /**
     * 组织部门信息同步（全量）
     *
     * @param request
     */
    @PostMapping(value = "/produceAPI/v2/allOrgSync")
    public IMessageResp allOrgSyncV2(@RequestBody Map<String, Object> allOrgSyncReq, HttpServletRequest request) {
        return processKit(allOrgSyncReq, request, Activity.ALL_ORG_SYNC);
    }

    /**
     * 联营saas同步接口同步数据接收
     *
     * @param req
     * @param request
     * @return
     */
    private IMessageResp processKit(Map<String, Object> req, HttpServletRequest request, String activity) {
        // 根据绑定的联营saas同步接口的密钥进行验签
        IMessageResp resp = IsvProduceAPI.verifyTenantSignature(req, request, KIT_ACCESS_KEY);
        // 如果鉴权返回成功则进行业务处理 验证入参格式 及异常场景校验
        if (SUCCESS.getResultCode().equals(resp.getResultCode())) {
            req.put("activity", activity);
            resp = isvProduceService.processProduceReq(getParamMap(req));
        }
        return resp;
    }

    private Map<String, String> getParamMap(Map<String, Object> map) {
        Map<String, String> req = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            req.put(key, value);
        }

        return req;
    }
}