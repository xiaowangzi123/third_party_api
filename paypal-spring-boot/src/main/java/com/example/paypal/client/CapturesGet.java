package com.example.paypal.client;


import com.paypal.http.HttpResponse;
import com.paypal.http.serializer.Json;
import com.paypal.payments.Capture;
import com.paypal.payments.CapturesGetRequest;
import com.paypal.payments.LinkDescription;
import org.json.JSONObject;

import java.io.IOException;

public class CapturesGet extends PayPalClient {

    private String clientId = "AdTJ2en6Av42r1xoziJj6bJK-X4tRGDHACZId0OPXfGyXs8OyoYEmlm8bHjzrgd3UislDQR0iBP7x-wM";
    private String clientSecret = "EC9DSrfAfVfo-c3K-1dILXA5iijnHtaunKwv2JzECSz9jcdy3t78rDFeAEgaixnnIYYlgQcipbZQWoCa";
    private String mode = "sandbox";

    public void testCapturesGetRequest() throws IOException {
        CapturesGetRequest request = new CapturesGetRequest("扣款id， CaptureOrder生成");

        HttpResponse<Capture> response = client(mode, clientId, clientSecret).execute(request);
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Status: " + response.result().status());
        System.out.println("Capture ids: " + response.result().id());
        System.out.println("Links: ");
        for (LinkDescription link : response.result().links()) {
            System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
        }
        System.out.println("Full response body:");
        System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
    }
    public static void main(String[] args) {
        try {
            new CapturesGet().testCapturesGetRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

