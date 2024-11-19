package com.huawei.service.impl;

import com.huawei.constant.Activity;
import com.huawei.constant.ChangeStatus;
import com.huawei.constant.IsvProduceConstant;
import com.huawei.constant.RenewAction;
import com.huawei.model.AppInfo;
import com.huawei.model.CheckInstanceInfo;
import com.huawei.model.IMessageResp;
import com.huawei.model.InstanceInfo;
import com.huawei.service.IsvProduceService;
import com.huawei.util.ResultCodeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.huawei.constant.IsvProduceConstant.DEBUG_TEST;
import static com.huawei.util.ResultCodeEnum.INVALID_PARAM;


@Slf4j
@Component
public class IsvProduceServiceImpl implements IsvProduceService {

    /**
     * 使用Collections.unmodifiableMap修饰可变集合，保证在方法处理过程中不会对常量方法成员误删除、修改等操作
     */
    private final Map<String, Function<Map<String, String>, IMessageResp>> isvProduceConsumers =
            Collections.unmodifiableMap(initConsumers());

    /**
     * 根据活动类型调用对应的实现方法
     */
    private Map<String, Function<Map<String, String>, IMessageResp>> initConsumers() {
        Map<String, Function<Map<String, String>, IMessageResp>> consumers = new HashMap<>(11);
        consumers.put(Activity.NEW_INSTANCE, this::newInstance);
        consumers.put(Activity.REFRESH_INSTANCE, this::refreshInstance);
        consumers.put(Activity.UPDATE_INSTANCE_STATUS, this::updateInstanceStatus);
        consumers.put(Activity.RELEASE_INSTANCE, this::releaseInstance);
        consumers.put(Activity.QUERY_INSTANCE, this::queryInstance);
        consumers.put(Activity.UPGRADE_INSTANCE, this::upgradeInstance);
        consumers.put(Activity.AUTH_SYNC, this::defaultMethod);
        consumers.put(Activity.TENANT_SYNC, this::defaultMethod);
        consumers.put(Activity.APPLICATION_SYNC, this::defaultMethod);
        consumers.put(Activity.SINGLE_ORG_SYNC, this::defaultMethod);
        consumers.put(Activity.ALL_ORG_SYNC, this::defaultMethod);
        return consumers;
    }

    @Override
    public IMessageResp processProduceReq(Map<String, String> provisionReq) {
        return isvProduceConsumers.get(provisionReq.get("activity")).apply(provisionReq);
    }

    /**
     * 创建实例
     */
    private IMessageResp newInstance(Map<String, String> provisionReq) {
        log.info("创建实例：{}", provisionReq);
        IMessageResp resp = new IMessageResp();
        resp.setResultCode(ResultCodeEnum.SUCCESS.getResultCode());
        resp.setResultMsg(ResultCodeEnum.SUCCESS.getResultMsg());

        // 测试的业务代码由伙伴根据自身需求填充，instanceId必须返回
        resp.setInstanceId(provisionReq.get("businessId"));

        if (DEBUG_TEST.equals(provisionReq.get("testFlag"))) {
            return resp;
        }

        // 查询用户信息，创建用户

        return resp;
    }

    /**
     * 接收更新示例消息
     *
     * @param provisionReq 入参
     * @return 响应结果
     */
    private IMessageResp refreshInstance(Map<String, String> provisionReq) {
        log.info("接收更新示例消息：{}", provisionReq);
        IMessageResp resp = new IMessageResp();
        resp.setResultCode(ResultCodeEnum.SUCCESS.getResultCode());
        resp.setResultMsg(ResultCodeEnum.SUCCESS.getResultMsg());

        // 测试的业务代码由伙伴根据自身需求填充
        if (DEBUG_TEST.equals(provisionReq.get("testFlag"))) {
            return resp;
        }

        // 真实的业务代码由伙伴根据自身业务填充
        switch (provisionReq.get("scene")) {
            case RenewAction.SUB:
                // TRIAL_TO_FORMAL：试用转正

                break;
            case RenewAction.RENEW:
                // RENEWAL：续费

                break;
            case RenewAction.UNSUB:
                // UNSUBSCRIBE_RENEWAL_PERIOD：退续费

                break;
            default:
                break;
        }
        return resp;
    }

    /**
     * 接收实例状态变更消息
     *
     * @param provisionReq 请求入参
     * @return 处理结果
     */
    private IMessageResp updateInstanceStatus(Map<String, String> provisionReq) {
        log.info("更新实例：{}", provisionReq);
        IMessageResp resp = new IMessageResp();
        resp.setResultCode(ResultCodeEnum.SUCCESS.getResultCode());
        resp.setResultMsg(ResultCodeEnum.SUCCESS.getResultMsg());

        // 测试的业务代码由伙伴根据自身需求填充
        if (DEBUG_TEST.equals(provisionReq.get("testFlag"))) {
            return resp;
        }

        // 真实的业务代码由伙伴根据自身业务填充
        switch (provisionReq.get("status")) {
            case ChangeStatus.FREEZE:
                // 冻结

                break;
            case ChangeStatus.UNFREEZE:
                // 解冻

                break;
            default:
                break;
        }
        return resp;
    }

    /**
     * 接收释放消息
     *
     * @param provisionReq 请求入参
     * @return 释放操作结果
     */
    private IMessageResp releaseInstance(Map<String, String> provisionReq) {
        log.info("释放实例：{}", provisionReq);
        IMessageResp resp = new IMessageResp();
        resp.setResultCode(ResultCodeEnum.SUCCESS.getResultCode());
        resp.setResultMsg(ResultCodeEnum.SUCCESS.getResultMsg());

        // 调测数据不做业务处理
        if (DEBUG_TEST.equals(provisionReq.get("testFlag"))) {
            return resp;
        }

        // 根据实例id到数据库查到详情信息，然后实现资源释放逻辑

        return resp;
    }

    /**
     * 接收升级消息
     *
     * @param isvProduceReq 请求入参
     * @return 升级操作结果
     */
    private IMessageResp upgradeInstance(Map<String, String> isvProduceReq) {
        log.info("升级实例：{}", isvProduceReq);
        IMessageResp resp = new IMessageResp();
        //  测试的业务代码由伙伴根据自身需求填充
        if (IsvProduceConstant.DEBUG_TEST.equals(isvProduceReq.get("testFlag"))) {
            return resp;
        }

        // 根据实例Id到表里查到对应的订单, 真实的业务代码由伙伴根据自身业务填充

        return resp;
    }

    /**
     * 默认返回成功
     *
     * @param isvProduceReq 请求入参
     * @return 操作结果
     */
    private IMessageResp defaultMethod(Map<String, String> isvProduceReq) {
        return new IMessageResp();
    }

    @SneakyThrows
    private IMessageResp queryInstance(Map<String, String> reqMap) {
        log.info("查询实例：{}", reqMap);
        String instanceId = reqMap.get("instanceId");
        CheckInstanceInfo checkInstanceInfo = new CheckInstanceInfo();
        if (StringUtils.isBlank(instanceId)) {
            checkInstanceInfo.setResultCode(INVALID_PARAM.getResultCode());
            checkInstanceInfo.setResultMsg(INVALID_PARAM.getResultMsg());
        } else {
            String[] instanceIds = instanceId.split(",");
            if (instanceIds.length > 100) {
                checkInstanceInfo.setResultCode(INVALID_PARAM.getResultCode());
                checkInstanceInfo.setResultMsg(INVALID_PARAM.getResultMsg());
            } else {
                // 根据instanceId查询真实的数据信息来返回，testFlag=1的时候是调测数据可以模拟返回
                InstanceInfo[] infos = new InstanceInfo[instanceIds.length];
                for (int index = 0; index < instanceIds.length; index++) {
                    InstanceInfo newInstanceInfo = new InstanceInfo();
                    AppInfo appInfo = new AppInfo();
                    appInfo.setUserName("hwadmin");
                    appInfo.setPassword("Huawei12345");
                    appInfo.setFrontEndUrl("http://transwai.com");
                    appInfo.setAdminUrl("https://transwai.com");
                    appInfo.setMemo("创建实例成功");
                    newInstanceInfo.setInstanceId(instanceIds[index]);
                    newInstanceInfo.setAppInfo(appInfo);
                    infos[index] = newInstanceInfo;
                }
                checkInstanceInfo.setInfo(infos);
            }
        }
        return checkInstanceInfo;
    }
}