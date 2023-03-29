package com.example.paypal.client;


import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.Capture;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.orders.Refund;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class OrdersGet extends PayPalClient {

    private String clientId = "AdTJ2en6Av42r1xoziJj6bJK-X4tRGDHACZId0OPXfGyXs8OyoYEmlm8bHjzrgd3UislDQR0iBP7x-wM";
    private String clientSecret = "EC9DSrfAfVfo-c3K-1dILXA5iijnHtaunKwv2JzECSz9jcdy3t78rDFeAEgaixnnIYYlgQcipbZQWoCa";
    private String mode = "sandbox";

    public void testOrdersGetRequest() throws IOException {

        OrdersGetRequest request = new OrdersGetRequest("订单id，CreateOrder 生成");

        HttpResponse<Order> response = null;
        try {
            response = client(mode, clientId, clientSecret).execute(request);
        } catch (Exception e) {
            try {
                System.out.println("调用paypal订单查询失败，链接异常1");
                response = client(mode, clientId, clientSecret).execute(request);
            } catch (Exception e2) {
                try {
                    System.out.println("调用paypal订单查询失败，链接异常2");
                    response = client(mode, clientId, clientSecret).execute(request);
                } catch (Exception e3) {
                    System.out.println("调用paypal订单查询失败，链接异常3");
                    System.out.println(e3.getMessage());
                }
            }
        }
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Status: " + response.result().status());
        System.out.println("Order id: " + response.result().id());
        if(response.result().purchaseUnits().get(0).payments() != null) {
            List<Capture> captures = response.result().purchaseUnits().get(0).payments().captures();
            if(captures != null) {
                for (Capture capture : captures) {
                    System.out.println("\t订单编号= " + capture.invoiceId() + "\tCapture Id= " + capture.id() + "\tCapture status= " + capture.status() + "\tCapture amount= " + capture.amount().currencyCode() + ":" + capture.amount().value());
                }
            }
            List<Refund> refunds = response.result().purchaseUnits().get(0).payments().refunds();
            if(refunds != null) {
                for (Refund refund : refunds) {
                    System.out.println("\t售后编号= " + refund.invoiceId() + "\tRefund Id= " + refund.id() + "\tRefund status= " + refund.status() + "\tRefund amount= " + refund.amount().currencyCode() + ":" + refund.amount().value());
                }
            }

        }
        System.out.println("Links: ");
        for (com.paypal.orders.LinkDescription link : response.result().links()) {
            System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
        }

        System.out.println("Full response body:");
        String json = new JSONObject(new Json().serialize(response.result())).toString(4);
        System.out.println(json);
    }

    public static void main(String[] args) {
        try {
            new OrdersGet().testOrdersGetRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

