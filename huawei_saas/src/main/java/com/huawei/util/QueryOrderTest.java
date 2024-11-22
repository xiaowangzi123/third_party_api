package com.huawei.util;

import com.cloud.apigateway.sdk.utils.Client;
import com.cloud.apigateway.sdk.utils.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.entity.OrderResponse;
import com.huawei.model.Constant;
import com.huawei.model.HostName;
import com.huawei.model.SSLCipherSuiteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author wyq
 * @date 2024/11/11
 * @desc
 */
@Slf4j
public class QueryOrderTest {

    public static void main(String[] args) throws Exception {
        // Create a new request.
        Request httpClientRequest = new Request();
        try {
            // Set the request parameters.
            // AppKey, AppSecrect, Method and Url are required parameters.
            // 认证用的ak和sk硬编码到代码中或者明文存储都有很大的安全风险，建议在配置文件或者环境变量中密文存放，使用时解密，确保安全；
            // 本示例以ak和sk保存在环境变量中为例，运行本示例前请先在本地环境中设置环境变量HUAWEICLOUD_SDK_AK和HUAWEICLOUD_SDK_SK。
            httpClientRequest.setKey("2CNBVV3R4B1H2JZ6AHAT");
            httpClientRequest.setSecret("g9nMiK6vFqTwxGQfUkUyE9J5DO2gaqhJT8vh4HqX");
            httpClientRequest.setMethod("GET");
            httpClientRequest.setUrl("https://mkt.myhuaweicloud.com/api/mkp-openapi-public/global/v1/order/query?orderId=MOCKPERIODYEARNEW");
            httpClientRequest.addHeader("Content-Type", "text/plain");
//            httpClientRequest.setBody("demo");
        } catch (Exception e) {
            log.error(e.getMessage());
            return;
        }
        CloseableHttpClient client = null;
        try {
            // Sign the request.
            HttpRequestBase signedRequest = Client.sign(httpClientRequest, Constant.SIGNATURE_ALGORITHM_SDK_HMAC_SHA256);
            if (Constant.DO_VERIFY) {
                // creat httpClient and verify ssl certificate
                HostName.setUrlHostName(httpClientRequest.getHost());
                client = (CloseableHttpClient) SSLCipherSuiteUtil.createHttpClientWithVerify(Constant.INTERNATIONAL_PROTOCOL);
            } else {
                // creat httpClient and do not verify ssl certificate
                client = (CloseableHttpClient) SSLCipherSuiteUtil.createHttpClient(Constant.INTERNATIONAL_PROTOCOL);
            }
            HttpResponse response = client.execute(signedRequest);
            if (response.getStatusLine().getStatusCode() == 200) {


            }
            // Print the body of the response.
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String jsonStr = EntityUtils.toString(resEntity, "UTF-8");
                log.info("Processing Body with name: {} and value: {}", System.getProperty("line.separator"),
                        jsonStr);


                System.out.println("--------------------------------");
                System.out.println(resEntity.getContent());
                System.out.println("--------------------------------");

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    OrderResponse orderResponse = objectMapper.readValue(jsonStr, OrderResponse.class);

                    // 打印结果
                    System.out.println("ResultCode: " + orderResponse.getResultCode());
                    System.out.println("ResultMsg: " + orderResponse.getResultMsg());
                    System.out.println("OrderId: " + orderResponse.getOrderInfo().getOrderId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}
//{"resultCode":"MKT.0000","resultMsg":"Success","orderInfo":{"orderId":"MOCKPERIODYEARNEW","orderType":"NEW","createTime":"20241121090846","orderLine":[{"orderLineId":"MOCKPERIODYEARNEW-000001","chargingMode":"PERIOD","periodType":"year","periodNumber":1,"expireTime":"20251121235959","productInfo":[{"productId":"OFFI846204237451911168","skuCode":"0a4d1578-5295-46a7-92d4-7c803dccc51d","linearValue":50,"productName":"MOCK测试商品,测试规格,基础版,包周期"}],"extendParams":[{"name":"emailDomainName","value":"test.xxxx.com"},{"name":"ip","value":"127.0.0.1"}],"currency":"100","currencyAfterDiscount":"100"}],"buyerInfo":{"mobilePhone":"18688888888","email":null,"customerId":"459bbbec25d24749b2eb83be37b602a8","customerName":"mock_user_01","customerRealName":"测试用户1","customerType":0,"userId":null,"userName":null},"extendParams":[]}}