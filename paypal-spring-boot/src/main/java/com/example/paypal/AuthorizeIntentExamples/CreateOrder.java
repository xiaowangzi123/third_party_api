package com.example.paypal.AuthorizeIntentExamples;

import com.example.paypal.example.PayPalClient;
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



	private OrderRequest buildCompleteRequestBody() {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("AUTHORIZE");

		ApplicationContext applicationContext = new ApplicationContext().brandName("TransWai").landingPage("BILLING")
				.cancelUrl("https://www.example.com")
				.returnUrl("https://www.example.com")
				.userAction("CONTINUE")
				.shippingPreference("SET_PROVIDED_ADDRESS");
		orderRequest.applicationContext(applicationContext);

		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().referenceId("PUHF")
				.description("Sporting Goods").customId("CUST-HighFashions").softDescriptor("HighFashions")
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("30.00")
						.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value("10.00"))
								.shipping(new Money().currencyCode("USD").value("20.00"))
								.handling(new Money().currencyCode("USD").value("10.00"))
								.taxTotal(new Money().currencyCode("USD").value("20.00"))
								.shippingDiscount(new Money().currencyCode("USD").value("30.00"))))
				.items(new ArrayList<Item>() {
					{
						add(new Item().name("转写视频01.mp4").description("Green XL").sku("sku01")
								.unitAmount(new Money().currencyCode("USD").value("90.00"))
								.tax(new Money().currencyCode("USD").value("10.00")).quantity("1")
								.category("PHYSICAL_GOODS"));
						add(new Item().name("转写视频02.mp4").description("Running, Size 10.5").sku("sku02")
								.unitAmount(new Money().currencyCode("USD").value("45.00"))
								.tax(new Money().currencyCode("USD").value("5.00")).quantity("2")
								.category("PHYSICAL_GOODS"));
					}
				})
				.shippingDetail(new ShippingDetail().name(new Name().fullName("John Doe"))
						.addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
								.adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}




	private OrderRequest buildMinimumRequestBody() {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("AUTHORIZE");
		ApplicationContext applicationContext = new ApplicationContext()
				.cancelUrl("https://www.example.com").returnUrl("https://www.example.com");
		orderRequest.applicationContext(applicationContext);
		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value("220.00"));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}



	public HttpResponse<Order> createOrder(boolean debug) throws IOException {
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer","return=representation");
		request.requestBody(buildCompleteRequestBody());
		HttpResponse<Order> response = client().execute(request);
		if (debug) {
			if (response.statusCode() == 201) {
				System.out.println("Order with Complete Payload: ");
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
		}
		return response;
	}




	public HttpResponse<Order> createOrderWithMinimumPayload(boolean debug) throws IOException {
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer","return=representation");
		request.requestBody(buildMinimumRequestBody());
		HttpResponse<Order> response = client().execute(request);
		if (debug) {
			if (response.statusCode() == 201) {
				System.out.println("Order with Minimum Payload: ");
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
		}
		return response;
	}



	public static void main(String args[]) {
		try {
			new CreateOrder().createOrder(true);
			new CreateOrder().createOrderWithMinimumPayload(true);
		} catch (com.paypal.http.exceptions.HttpException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
