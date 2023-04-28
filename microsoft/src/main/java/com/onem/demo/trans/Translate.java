package com.onem.demo.trans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public class Translate {
    /*  Configure the local environment:
     * Set the TRANSLATOR_TEXT_RESOURCE_KEY,TRANSLATOR_TEXT_REGION and TRANSLATOR_TEXT_ENDPOINT environment
     * variables on your local machine using the appropriate method for your
     * preferred shell (Bash, PowerShell, Command Prompt, etc.).
     *
     * For TRANSLATOR_TEXT_ENDPOINT, use the same region you used to get your
     * resource keys.
     *
     * If the environment variable is created after the application is launched
     * in a console or with Visual Studio, the shell (or Visual Studio) needs to be
     * closed and reloaded to take the environment variable into account.
     */

//    private static String key = "96a2ae701e6d43aaab86f50ee57f51e8";
//    private static String location = "chinaeast2";
//    private static String endpointKey = "https://api.translator.azure.cn/";

//    private static String resourceKey = System.getenv(key);
//    private static String resourceKey = "96a2ae701e6d43aaab86f50ee57f51e8";
    private static String resourceKey = "61d3cff63c7840a992f73779488c8410";
//    private static String region = System.getenv(location);
    private static String region = "chinaeast2";
//    private static String endpoint = System.getenv(endpointKey);
    private static String endpoint = "https://api.translator.azure.cn/";
    String url = endpoint + "/translate?api-version=3.0&from=en-US&to=zh-CN";

    // Instantiates the OkHttpClient.
    OkHttpClient client = new OkHttpClient();

    // This function performs a POST request.
    public String Post() throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[{\n\t\"Text\": \"Welcome to Microsoft Translator. Guess how many languages I speak!\"\n}]");
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", resourceKey)
                .addHeader("Ocp-Apim-Subscription-Region", region)
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // This function prettifies the json response.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
    public static void main(String[] args) {
        try {
            Translate translateRequest = new Translate();
            String response = translateRequest.Post();
            System.out.println(prettify(response));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}