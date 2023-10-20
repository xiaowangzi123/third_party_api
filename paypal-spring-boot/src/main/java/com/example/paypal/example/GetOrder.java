package com.example.paypal.example;

import com.example.paypal.AuthorizeIntentExamples.CreateOrder;
import com.example.paypal.client.PayPalClient;
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
		HttpResponse<Order> response = new CreateOrder().createOrder(false);
		new GetOrder().getOrder(response.result().id());
	}
}
