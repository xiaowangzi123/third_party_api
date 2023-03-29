package com.example.paypal.client;


import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import java.io.IOException;

@Slf4j
public class CaptureOrder extends PayPalClient {

    private String clientId = "AdTJ2en6Av42r1xoziJj6bJK-X4tRGDHACZId0OPXfGyXs8OyoYEmlm8bHjzrgd3UislDQR0iBP7x-wM";
    private String clientSecret = "EC9DSrfAfVfo-c3K-1dILXA5iijnHtaunKwv2JzECSz9jcdy3t78rDFeAEgaixnnIYYlgQcipbZQWoCa";
    private String mode = "sandbox";

    public OrderRequest buildRequestBody() {
        return new OrderRequest();
    }

    /**
     * 用户授权支付成功，进行扣款操作
     */
    public HttpResponse<Order> captureOrder(String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());
        PayPalClient payPalClient = new PayPalClient();
        HttpResponse<Order> response = null;
        try {
            response = payPalClient.client(mode, clientId, clientSecret).execute(request);
        } catch (IOException e1) {
            try {
                log.error("第1次调用paypal扣款失败");
                response = payPalClient.client(mode, clientId, clientSecret).execute(request);
            } catch (Exception e) {
                try {
                    log.error("第2次调用paypal扣款失败");
                    response = payPalClient.client(mode, clientId, clientSecret).execute(request);
                } catch (Exception e2) {
                    log.error("第3次调用paypal扣款失败，失败原因 {}", e2.getMessage() );
                }
            }
        }
        log.info("Status Code = {}, Status = {}, OrderID = {}", response.statusCode(), response.result().status(), response.result().id());
        for (LinkDescription link : response.result().links()) {
            log.info("Links-{}: {}    \tCall Type: {}", link.rel(), link.href(), link.method());
        }
        for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
            for (Capture capture : purchaseUnit.payments().captures()) {
                log.info("Capture id: {}", capture.id());
                log.info("status: {}", capture.status());
                log.info("invoice_id: {}", capture.invoiceId());
                if("COMPLETED".equals(capture.status())) {
                    //进行数据库操作，修改订单状态为已支付成功，尽快发货（配合回调和CapturesGet查询确定成功）
                    log.info("支付成功,状态为=COMPLETED");
                }
                if("PENDING".equals(capture.status())) {
                    log.info("status_details: {}", capture.captureStatusDetails().reason());
                    String reason = "PENDING";
                    if(capture.captureStatusDetails() != null && capture.captureStatusDetails().reason() != null) {
                        reason = capture.captureStatusDetails().reason();
                    }
                    //进行数据库操作，修改订单状态为已支付成功，但触发了人工审核，请审核通过后再发货（配合回调和CapturesGet查询确定成功）
                    log.info("支付成功,状态为=PENDING : {}", reason);
                }
            }
        }
        Payer buyer = response.result().payer();
        log.info("Buyer Email Address: {}", buyer.email());
        log.info("Buyer Name: {} {}", buyer.name().givenName(), buyer.name().surname());
        String json = new JSONObject(new Json().serialize(response.result())).toString(4);
        log.info("captureOrder response body: {}", json);
        return response;
    }


    public static void main(String[] args) {
        try {
            new CaptureOrder().captureOrder("订单id，CreateOrder 生成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

