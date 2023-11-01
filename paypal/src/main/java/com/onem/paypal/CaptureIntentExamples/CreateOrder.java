package com.onem.paypal.CaptureIntentExamples;

import com.onem.paypal.enums.ItemCategoryEnum;
import com.onem.paypal.example.PayPalClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetail;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateOrder extends PayPalClient {


    private OrderRequest buildRequestBody() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("EXAMPLE INC").landingPage("BILLING")
                .cancelUrl("http://www.transwai.com/")
                .returnUrl("http://59.36.211.29:8081/paypal/payment/callback")
                .userAction("CONTINUE")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("PUHF")
                .description("Sporting Goods").customId("CUST-HighFashions").softDescriptor("HighFashions")
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("2.00")
                        .amountBreakdown(new AmountBreakdown()
                                .itemTotal(new Money().currencyCode("USD").value("2.00"))
                                .shipping(new Money().currencyCode("USD").value("0.00"))
                                .handling(new Money().currencyCode("USD").value("0.00"))
                                .taxTotal(new Money().currencyCode("USD").value("0.00"))
                                .shippingDiscount(new Money().currencyCode("USD").value("0.00"))))
                .items(new ArrayList<Item>() {
                    {
                        add(new Item().name("转写视频01.mp4").description("视频转写").sku("sku01")
                                .unitAmount(new Money().currencyCode("USD").value("1.00"))
                                .tax(new Money().currencyCode("USD").value("0.00")).quantity("1")
                                .category(ItemCategoryEnum.DIGITAL_GOODS.name()));
                        add(new Item().name("转写视频01.mp4").description("视频转写").sku("sku02")
                                .unitAmount(new Money().currencyCode("USD").value("1.0"))
                                .tax(new Money().currencyCode("USD").value("0.0")).quantity("1")
                                .category(ItemCategoryEnum.DIGITAL_GOODS.name()));
                    }
                })
                .shippingDetail(new ShippingDetail().name(new Name().fullName("John Doe"))
                        .addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
                                .adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }


    public HttpResponse<Order> createOrder(boolean debug) throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.header("prefer", "return=representation");
        request.requestBody(buildRequestBody());
        HttpResponse<Order> response = client().execute(request);
        System.out.println("创建订单返回--->" + com.alibaba.fastjson2.JSONObject.toJSONString(response));
        if (response.statusCode() == 201) {
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Status: " + response.result().status());
            System.out.println("Order ID: " + response.result().id());
            System.out.println("Intent: " + response.result().checkoutPaymentIntent());
            System.out.println("Links: ");
            for (LinkDescription link : response.result().links()) {
                System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
            }
            System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amountWithBreakdown().currencyCode()
                    + " " + response.result().purchaseUnits().get(0).amountWithBreakdown().value());
            System.out.println("Full response body:");
            System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
        }

        return response;
    }


    public static void main(String args[]) {
        try {
            new CreateOrder().createOrder(true);
        } catch (com.paypal.http.exceptions.HttpException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
