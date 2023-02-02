package com.onem.microsoft.langIdentify;

import com.alibaba.fastjson2.JSON;
import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DetectedLanguage;
import com.azure.core.credential.AzureKeyCredential;

/**
 * @author wyq
 * @date 2023/1/31
 * @desc
 */
public class LangIdentify2 {
    private static String KEY = "46411c71077e47ca96f79b2108eeafe4";
    private static String ENDPOINT = "https://languageservice.cognitiveservices.azure.cn/";

    public static void main(String[] args) {
        TextAnalyticsClient client = new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(KEY)).endpoint(ENDPOINT).buildClient();
//        String text = "中文语种检测";
//        String text = "language detection test";
        String text = "123 ";

        DetectedLanguage detectedLanguage = client.detectLanguage(text);
        //{"confidenceScore":1.0,"iso6391Name":"zh_chs","name":"Chinese_Simplified","warnings":{}}
        //{"confidenceScore":0.74,"iso6391Name":"en","name":"English","warnings":{}}
        //{"confidenceScore":0.0,"iso6391Name":"(Unknown)","name":"(Unknown)","warnings":{}}
        System.out.printf("--->>>" + JSON.toJSONString(detectedLanguage));

    }

    // Method to authenticate the client object with your key and endpoint
    static TextAnalyticsClient authenticateClient(String key, String endpoint) {
        return new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();
    }

    // Example method for detecting the language of text
    static void detectLanguageExample(TextAnalyticsClient client) {
        // The text to be analyzed.
//        String text = "I am a teacher";
        String text = "中文语种检测";

        DetectedLanguage detectedLanguage = client.detectLanguage(text);
        System.out.printf("-----> Detected primary language: %s, ISO 6391 name: %s, score: %.2f.%n",
                detectedLanguage.getName(), //English
                detectedLanguage.getIso6391Name(),  //en
                detectedLanguage.getConfidenceScore());  //0.70

    }
}
