package com.microsoft.demo.quickstart;

import com.alibaba.fastjson.JSON;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.util.EventHandler;
import com.microsoft.cognitiveservices.speech.util.EventHandlerImpl;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author wyq
 * @date 2022/4/27
 * @desc
 */
public class GetInfoTest {
    private static final String SPEECH_SUBSCRIPTION_KEY = "d0395385638342efa555061a62fdc14b";
    private static final String SERVICE_REGION = "chinaeast2";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
// Creates an instance of a speech config with specified
        // subscription key and service region. Replace with your own subscription key
        // and service region (e.g., "westus").
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);

        // Creates a speech synthesizer
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, null);

        System.out.println("Enter a locale in BCP-47 format (e.g. en-US) that you want to get the voices of, or enter empty to get voices in all locales.");
        System.out.print("> ");
        String text = new Scanner(System.in).nextLine();


        Future<SynthesisVoicesResult> task = synthesizer.getVoicesAsync(text);

        SynthesisVoicesResult result = task.get();

//        SynthesisVoicesResult result = synthesizer.getVoicesAsync(text).get();

        System.out.println("result--> "+JSON.toJSONString(result));

        // Checks result.
        if (result.getReason() == ResultReason.VoicesListRetrieved) {
            System.out.println("Voices successfully retrieved, they are:");
            for (VoiceInfo voice : result.getVoices()) {
                System.out.println(voice.getName());
            }
        } else if (result.getReason() == ResultReason.Canceled) {
            System.out.println("CANCELED: ErrorDetails=" + result.getErrorDetails());
        }

        result.close();
        synthesizer.close();
    }

}


