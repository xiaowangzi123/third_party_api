package com.onem.paypal.example;

import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.orders.Order;
import com.paypal.orders.OrdersGetRequest;
import org.json.JSONObject;

import java.io.IOException;

public class GetOrder extends PayPalClient {


	public void getOrder(String orderId) throws IOException {
		OrdersGetRequest request = new OrdersGetRequest(orderId);
		HttpResponse<Order> response = client().execute(request);
		System.out.println("Full response body:");
		System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
	}




	public static void main(String[] args) throws IOException {
		//创建订单，直接查看订餐状态
//		HttpResponse<Order> response = new CreateOrder().createOrder(false);
//		new GetOrder().getOrder(response.result().id());

		new GetOrder().getOrder("525720529P808800M");
	}
}
