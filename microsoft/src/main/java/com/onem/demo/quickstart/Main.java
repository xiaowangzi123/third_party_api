package com.onem.demo.quickstart;

import com.microsoft.cognitiveservices.speech.*;

import java.util.Scanner;
import java.util.concurrent.Future;


public class Main {

    private static final String SPEECH_SUBSCRIPTION_KEY = "49989ad01d5a434686dd31d82e094496";
    private static final String SERVICE_REGION = "chinaeast2";

    public static void main(String[] args) {
        try (SpeechConfig config = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
             SpeechSynthesizer synth = new SpeechSynthesizer(config)) {

            assert (config != null);
            assert (synth != null);

            int exitCode = 1;

            System.out.println("Type some text that you want to speak...");
            System.out.print("> ");
            String text = new Scanner(System.in).nextLine();

            Future<SpeechSynthesisResult> task = synth.SpeakTextAsync(text);
            assert (task != null);

            SpeechSynthesisResult result = task.get();
            assert (result != null);

            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                System.out.println("Speech synthesized to speaker for text [" + text + "]");
                exitCode = 0;
            } else if (result.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }

            System.exit(exitCode);
        } catch (Exception ex) {
            System.out.println("Unexpected exception: " + ex.getMessage());

            assert (false);
            System.exit(1);
        }
    }
}