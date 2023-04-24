package com.onem.demo.ttssample;

/**
 * @author wyq
 * @date 2022/4/24
 * @desc
 */

import lombok.NoArgsConstructor;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/*
 * This class demonstrates how to get a valid O-auth token from
 * Azure Data Market.
 */
@NoArgsConstructor
public class Authentication {
    public static final String AccessTokenUri = "https://chinaeast2.api.cognitive.azure.cn/sts/v1.0/issuetoken";
    //Access token expires every 10 minutes. Renew it every 9 minutes only.
    private final int RefreshTokenDuration = 9 * 60 * 1000;
    private final String charsetName = "utf-8";
    private String apiKey;
    private String accessToken;
    private Timer accessTokenRenewer;
    private TimerTask nineMinitesTask = null;


    public Authentication(String apiKey) {
        this.apiKey = apiKey;

        this.accessToken = HttpPost(AccessTokenUri, this.apiKey);

        // renew the token every specified minutes
        accessTokenRenewer = new Timer();
        nineMinitesTask = new TimerTask() {
            public void run() {
                RenewAccessToken();
            }
        };
        accessTokenRenewer.schedule(nineMinitesTask, 0, RefreshTokenDuration);
    }

//                  eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJyZWdpb24iOiJjaGluYWVhc3QyIiwic3Vic2NyaXB0aW9uLWlkIjoiNGY4NTMzMmRjNGUyNDMxZjlmMGZiZjM5MjIxYmRjMjQiLCJwcm9kdWN0LWlkIjoiU3BlZWNoU2VydmljZXMuRjAiLCJjb2duaXRpdmUtc2VydmljZXMtZW5kcG9pbnQiOiJodHRwczovL2FwaS5jb2duaXRpdmUubWljcm9zb2Z0LmNvbS9pbnRlcm5hbC92MS4wLyIsImF6dXJlLXJlc291cmNlLWlkIjoiL3N1YnNjcmlwdGlvbnMvY2QzY2E4YTUtNzE0YS00YmQyLWEyM2QtNDM1YTYwNjM4NmZiL3Jlc291cmNlR3JvdXBzL3NwZWVjaC1ncm91cC9wcm92aWRlcnMvTWljcm9zb2Z0LkNvZ25pdGl2ZVNlcnZpY2VzL2FjY291bnRzL2Nlc2hpeXV5aW5zaGliaWUiLCJzY29wZSI6InNwZWVjaHNlcnZpY2VzIiwiZXhwIjoiMTY1MDgwMjE1MCIsImF1ZCI6InVybjptcy5zcGVlY2hzZXJ2aWNlcy5jaGluYWVhc3QyIiwiaXNzIjoidXJuOm1zLmNvZ25pdGl2ZXNlcnZpY2VzIn0.Y2ienbMIONCBIZpiFsfOWWeFYuwtBD__Or1xhyc5T7I
//new access token: eyJhbGciOiJodHRwOi8vd3d3LnczLm9yZy8yMDAxLzA0L3htbGRzaWctbW9yZSNobWFjLXNoYTI1NiIsInR5cCI6IkpXVCJ9.eyJyZWdpb24iOiJjaGluYWVhc3QyIiwic3Vic2NyaXB0aW9uLWlkIjoiNGY4NTMzMmRjNGUyNDMxZjlmMGZiZjM5MjIxYmRjMjQiLCJwcm9kdWN0LWlkIjoiU3BlZWNoU2VydmljZXMuRjAiLCJjb2duaXRpdmUtc2VydmljZXMtZW5kcG9pbnQiOiJodHRwczovL2FwaS5jb2duaXRpdmUubWljcm9zb2Z0LmNvbS9pbnRlcm5hbC92MS4wLyIsImF6dXJlLXJlc291cmNlLWlkIjoiL3N1YnNjcmlwdGlvbnMvY2QzY2E4YTUtNzE0YS00YmQyLWEyM2QtNDM1YTYwNjM4NmZiL3Jlc291cmNlR3JvdXBzL3NwZWVjaC1ncm91cC9wcm92aWRlcnMvTWljcm9zb2Z0LkNvZ25pdGl2ZVNlcnZpY2VzL2FjY291bnRzL2Nlc2hpeXV5aW5zaGliaWUiLCJzY29wZSI6InNwZWVjaHNlcnZpY2VzIiwiZXhwIjoiMTY1MDgwMjE1MCIsImF1ZCI6InVybjptcy5zcGVlY2hzZXJ2aWNlcy5jaGluYWVhc3QyIiwiaXNzIjoidXJuOm1zLmNvZ25pdGl2ZXNlcnZpY2VzIn0.Y2ienbMIONCBIZpiFsfOWWeFYuwtBD__Or1xhyc5T7I

    public static void main(String[] args) {
        String apikey = "49989ad01d5a434686dd31d82e094496";
        Authentication obj = new Authentication();
        String at = obj.HttpPost(AccessTokenUri, apikey);
        System.out.println("获取AT--> "+at);
    }

    public String GetAccessToken() {
        return this.accessToken;
    }

    private void RenewAccessToken() {
        String newAccessToken = HttpPost(AccessTokenUri, this.apiKey);
        //swap the new token with old one
        //Note: the swap is thread unsafe
        System.out.println("new access token: " + accessToken);
        this.accessToken = newAccessToken;
    }

    private String HttpPost(String AccessTokenUri, String apiKey) {
        InputStream inSt = null;
        HttpsURLConnection webRequest = null;

        //Prepare OAuth request
        try {
            webRequest = HttpsConnection.getHttpsConnection(AccessTokenUri);
            webRequest.setDoInput(true);
            webRequest.setDoOutput(true);
            webRequest.setConnectTimeout(5000);
            webRequest.setReadTimeout(5000);
            webRequest.setRequestMethod("POST");

            byte[] bytes = new byte[0];
            webRequest.setRequestProperty("content-length", String.valueOf(bytes.length));
            webRequest.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
            webRequest.connect();

            DataOutputStream dop = new DataOutputStream(webRequest.getOutputStream());
            dop.write(bytes);
            dop.flush();
            dop.close();

            inSt = webRequest.getInputStream();
            InputStreamReader in = new InputStreamReader(inSt);
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }

            bufferedReader.close();
            in.close();
            inSt.close();
            webRequest.disconnect();

            // parse the access token
            String token = strBuffer.toString();

            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
