package com.onem.paypal.CaptureIntentExamples;

import com.onem.paypal.example.PayPalClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.Capture;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.Payer;
import com.paypal.orders.PurchaseUnit;
import org.json.JSONObject;

import java.io.IOException;

public class CaptureOrder extends PayPalClient {


    /**
     * Creating empty body for capture request. We can set the payment source if
     * required.
     *
     * @return OrderRequest request with empty body
     */

    public OrderRequest buildRequestBody() {
        return new OrderRequest();
    }


    /**
     * Method to capture order after creation. Valid approved order Id should be
     * passed an argument to this method.
     *
     * @param orderId Order ID from createOrder response
     * @return HttpResponse<Order> response received from API
     * @throws java.io.IOException Exceptions from API if any
     */

    public HttpResponse<Order> captureOrder(String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(buildRequestBody());
        HttpResponse<Order> response = client().execute(request);
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Status: " + response.result().status());
        System.out.println("Order ID: " + response.result().id());
        System.out.println("Links: ");
        for (LinkDescription link : response.result().links()) {
            System.out.println("\t" + link.rel() + ": " + link.href());
        }
        System.out.println("Capture ids:");
        for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
            for (Capture capture : purchaseUnit.payments().captures()) {
                System.out.println("\t" + capture.id());
            }
        }
        System.out.println("Buyer: ");
        Payer buyer = response.result().payer();
        System.out.println("\tEmail Address: " + buyer.email());
        System.out.println("\tName: " + buyer.name().givenName() + " " + buyer.name().surname());
        System.out.println("Full response body:");
        System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        return response;
    }


    /**
     * Driver Function to invoke capture payment on order. Order Id should be
     * replaced with the valid approved order id.
     *
     * @param args
     */

    public static void main(String[] args) {
        try {
//            new CaptureOrder().captureOrder("431351996N440112E");
            new CaptureOrder().captureOrder("525720529P808800M");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

