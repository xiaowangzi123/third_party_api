package com.microsoft.demo.ttssample;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

/**
 * @author wyq
 * @date 2022/4/24
 * @desc
 */

public class HttpsConnection {
    public static HttpsURLConnection getHttpsConnection(String connectingUrl) throws Exception {
        URL url = new URL(connectingUrl);
        HttpsURLConnection webRequest = (HttpsURLConnection) url.openConnection();
        return webRequest;
    }
}
