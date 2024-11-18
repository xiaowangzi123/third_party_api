package com.huawei.util;

import static com.huawei.constant.IsvProduceConstant.NONCE;
import static com.huawei.constant.IsvProduceConstant.SIGNATURE;
import static com.huawei.constant.IsvProduceConstant.TIMESTAMP;
import static com.huawei.util.ResultCodeEnum.INVALID_PARAM;
import static com.huawei.util.ResultCodeEnum.INVALID_TOKEN;
import static com.huawei.util.ResultCodeEnum.SUCCESS;

import com.huawei.model.IMessageResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

public class IsvProduceAPI {
    /**
     * 默认编码：UTF-8
     */
    private static final String CHARSET = "UTF-8";

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 基础接口验证签名
     *
     * @param temp
     * @param request
     * @param accessKey
     */
    public static IMessageResp verifySignature(Map<String, String> temp, HttpServletRequest request, String accessKey)
            throws Exception {
        IMessageResp resp = new IMessageResp();
        resp.setResultCode(SUCCESS.getResultCode());
        resp.setResultMsg(SUCCESS.getResultMsg());
        Map<String, String[]> paramsMap = request.getParameterMap();

        // 获取请求时间戳
        String reqTimestamp = getParamValue(paramsMap, TIMESTAMP);
        if (StringUtils.isEmpty(reqTimestamp)) {
            resp.setResultCode(INVALID_PARAM.getResultCode());
            resp.setResultMsg(INVALID_PARAM.getResultMsg());
            return resp;
        }

        // 请求时间戳与当前时间相差不超过60s
        if (!validateReqTime(reqTimestamp)) {
            resp.setResultCode(INVALID_PARAM.getResultCode());
            resp.setResultMsg(INVALID_PARAM.getResultMsg());
            return resp;
        }

        // 获取随机字符串
        String nonce = getParamValue(paramsMap, NONCE);
        if (StringUtils.isEmpty(nonce)) {
            resp.setResultCode(INVALID_PARAM.getResultCode());
            resp.setResultMsg(INVALID_PARAM.getResultMsg());
            return resp;
        }

        // 获取请求里的签名
        String reqSignature = getParamValue(paramsMap, SIGNATURE);
        if (StringUtils.isEmpty(reqSignature)) {
            resp.setResultCode(INVALID_TOKEN.getResultCode());
            resp.setResultMsg(INVALID_TOKEN.getResultMsg());
            return resp;
        }

        // 对入参进行顺序排序并排除value为空的key
        Map<String, Object> sortedMap = new TreeMap<>(temp);
        sortedMap.entrySet().removeIf(entry -> Objects.isNull(entry.getValue()));
        String reqParams = objectMapper.writeValueAsString(sortedMap);

        // 加密请求体
        String encryptBody = generateSignature(accessKey, reqParams);

        // 生成签名
        String signature = generateSignature(accessKey, accessKey + nonce + reqTimestamp + encryptBody);

        // 判断计算后的签名与云市场请求中传递的签名是否一致 不区分大小写
        if (!reqSignature.equalsIgnoreCase(signature)) {
            resp.setResultCode(INVALID_TOKEN.getResultCode());
            resp.setResultMsg(INVALID_TOKEN.getResultMsg());
            return resp;
        }
        return resp;
    }

    /**
     * 联营saas接口验证签名
     *
     * @param temp
     * @param request
     * @param accessKey
     */
    public static IMessageResp verifyTenantSignature(Map<String, Object> temp, HttpServletRequest request,
        String accessKey) {
        IMessageResp resp = new IMessageResp();
        resp.setResultCode(SUCCESS.getResultCode());
        resp.setResultMsg(SUCCESS.getResultMsg());

        // 获取请求时间戳
        String reqTimestamp = request.getHeader("x-timestamp");
        if (StringUtils.isEmpty(reqTimestamp)) {
            resp.setResultCode(INVALID_PARAM.getResultCode());
            resp.setResultMsg(INVALID_PARAM.getResultMsg());
            return resp;
        }

        // 请求时间戳与当前时间相差不超过60s
        if (!validateReqTime(reqTimestamp)) {
            resp.setResultCode(INVALID_PARAM.getResultCode());
            resp.setResultMsg(INVALID_PARAM.getResultMsg());
            return resp;
        }

        // 获取随机字符串
        String nonce = request.getHeader("x-nonce");
        if (StringUtils.isEmpty(nonce)) {
            resp.setResultCode(INVALID_PARAM.getResultCode());
            resp.setResultMsg(INVALID_PARAM.getResultMsg());
            return resp;
        }

        // 获取请求里的签名
        String reqSignature = request.getHeader("x-sign");
        if (StringUtils.isEmpty(reqSignature)) {
            resp.setResultCode(INVALID_TOKEN.getResultCode());
            resp.setResultMsg(INVALID_TOKEN.getResultMsg());
            return resp;
        }

        // 对入参进行顺序排序并排除value为空的key
        Map<String, Object> sortedMap = new TreeMap<>(temp);
        sortedMap.entrySet().removeIf(entry -> Objects.isNull(entry.getValue()));
        String reqParams;
        try {
            reqParams = objectMapper.writeValueAsString(sortedMap);
        } catch (JsonProcessingException e) {
            resp.setResultCode(INVALID_TOKEN.getResultCode());
            resp.setResultMsg(INVALID_TOKEN.getResultMsg());
            return resp;
        }

        // 生成签名
        String signature = generateSignature(accessKey, accessKey + nonce + reqTimestamp + reqParams);

        // 判断计算后的签名与云市场请求中传递的签名是否一致 不区分大小写
        if (!reqSignature.equalsIgnoreCase(signature)) {
            resp.setResultCode(INVALID_TOKEN.getResultCode());
            resp.setResultMsg(INVALID_TOKEN.getResultMsg());
            return resp;
        }
        return resp;
    }

    private static String getParamValue(Map<String, String[]> paramsMap, String param) {
        String[] paramArray = paramsMap.get(param);
        if (null == paramArray || paramArray.length == 0) {
            return null;
        }
        return paramArray[0];
    }

    private static boolean validateReqTime(String reqTimestamp) {
        long reqUTCTime = Long.parseLong(reqTimestamp);

        // 获取当前UTC时间戳
        long currentUTCTime = System.currentTimeMillis();
        return currentUTCTime - reqUTCTime <= 60000L;
    }

    public static byte[] hmacSHA256(String macKey, String macData) {
        try {
            SecretKeySpec secret = new SecretKeySpec(macKey.getBytes(CHARSET), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secret);
            return mac.doFinal(macData.getBytes(CHARSET));
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException e) {
            return new byte[0];
        }
    }

    /**
     * 获取请求体签名
     *
     * @param key  accessKey
     * @param body 待签名的body体字符串
     * @return 生成的签名
     */
    public static String generateSignature(String key, String body) {
        return Hex.encodeHexString(hmacSHA256(key, body));
    }
}

