package com.example.paypal.client;


import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.Money;
import com.paypal.payments.Refund;
import com.paypal.payments.RefundRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.IOException;

@Slf4j
public class RefundOrder extends PayPalClient {

    private String clientId = "AdTJ2en6Av42r1xoziJj6bJK-X4tRGDHACZId0OPXfGyXs8OyoYEmlm8bHjzrgd3UislDQR0iBP7x-wM";
    private String clientSecret = "EC9DSrfAfVfo-c3K-1dILXA5iijnHtaunKwv2JzECSz9jcdy3t78rDFeAEgaixnnIYYlgQcipbZQWoCa";
    private String mode = "sandbox";

    /**
     * 创建退款请求体
     */
    public RefundRequest buildRequestBody() {
        RefundRequest refundRequest = new RefundRequest();
        Money money = new Money();
        money.currencyCode("USD");
        money.value("40.00");
        refundRequest.amount(money);
        refundRequest.invoiceId("T202005230002");
        refundRequest.noteToPayer("7天无理由退款");
        return refundRequest;
    }

    /**
     * 申请退款
     */
    public HttpResponse<Refund> refundOrder(String orderId) throws IOException {

        OrdersGetRequest ordersGetRequest = new OrdersGetRequest(orderId);
        PayPalClient payPalClient = new PayPalClient();
        HttpResponse<com.paypal.orders.Order> ordersGetResponse = null;
        try {
            ordersGetResponse = payPalClient.client(mode, clientId, clientSecret).execute(ordersGetRequest);
        } catch (Exception e) {
            try {
                log.error("第1次调用paypal订单查询失败");
                ordersGetResponse = payPalClient.client(mode, clientId, clientSecret).execute(ordersGetRequest);
            } catch (Exception e2) {
                try {
                    log.error("第2次调用paypal订单查询失败");
                    ordersGetResponse = payPalClient.client(mode, clientId, clientSecret).execute(ordersGetRequest);
                } catch (Exception e3) {
                    log.error("第3次调用paypal订单查询失败，失败原因：{}", e3.getMessage());
                }
            }
        }
        String captureId = ordersGetResponse.result().purchaseUnits().get(0).payments().captures().get(0).id();
        CapturesRefundRequest request = new CapturesRefundRequest(captureId);
        request.prefer("return=representation");
        request.requestBody( buildRequestBody());
        HttpResponse<Refund> response = null;
        try {
            response = payPalClient.client(mode, clientId, clientSecret).execute(request);
        } catch (IOException e) {
            try {
                log.error("第1次调用paypal退款申请失败");
                response = payPalClient.client(mode, clientId, clientSecret).execute(request);
            } catch (Exception e1) {
                try {
                    log.error("第2次调用paypal退款申请失败");
                    response = payPalClient.client(mode, clientId, clientSecret).execute(request);
                } catch (Exception e2) {
                    log.error("第3次调用paypal退款申请失败，失败原因 {}", e2.getMessage());
                }
            }
        }
        log.info("Status Code = {}, Status = {}, RefundID = {}", response.statusCode(), response.result().status(), response.result().id());
        if("COMPLETED".equals(response.result().status())) {
            //进行数据库操作，修改状态为已退款（配合回调和退款查询确定退款成功）
            log.info("退款成功");
        }
        for (com.paypal.payments.LinkDescription link : response.result().links()) {
            log.info("Links-{}: {}    \tCall Type: {}", link.rel(), link.href(), link.method());
        }
        String json = new JSONObject(new Json().serialize(response.result())).toString(4);
        log.info("refundOrder response body: {}", json);
        return response;

    }


    public static void main(String[] args) {
        try {
            new RefundOrder().refundOrder("订单id，CreateOrder 生成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


