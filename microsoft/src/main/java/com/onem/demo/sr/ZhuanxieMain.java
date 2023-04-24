package com.onem.demo.sr;

import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * @author wyq
 * @date 2022/7/22
 * @desc 微软转写
 */
public class ZhuanxieMain {
//    private static final String SPEECH_SUBSCRIPTION_KEY = "a6bfa21a746648caaf84f977fb07f45f";
    private static final String SPEECH_SUBSCRIPTION_KEY = "6c85dc055fd3405baa869a7dfb48bf45";
    private static final String SERVICE_REGION = "chinaeast2";
    private static final String FILE_PATH = "D:\\TestAudioVideo\\zhuanxie\\testZhuan30.wav";
//    private static final String FILE_PATH = "D:\\TestAudioVideo\\pronun\\test.wav";
    private static final String SRL_LANG_CODE = "en-US";

    private static Semaphore stopTranslationWithFileSemaphore;

    public static void main(String[] args) throws Exception {
        continuousRecognitionWithFileAsync();
    }

    public static void continuousRecognitionWithFileAsync() throws Exception {
        // <recognitionContinuousWithFile>
        // Creates an instance of a speech config with specified
        // subscription key and service region. Replace with your own subscription key
        // and service region (e.g., "westus").
        SpeechConfig config = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);

        // Creates a speech recognizer using file as audio input.
        // Replace with your own audio file name.
        AudioConfig audioInput = AudioConfig.fromWavFileInput(FILE_PATH);

        SpeechRecognizer recognizer = new SpeechRecognizer(config, audioInput);
        {
            // Subscribes to events.
            recognizer.recognizing.addEventListener((s, e) -> {
                System.out.println("RECOGNIZING: Text=" + e.getResult().getText());
            });

            recognizer.recognized.addEventListener((s, e) -> {
                if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                    System.out.println("RECOGNIZED: Text=" + e.getResult().getText());
                } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                    System.out.println("NOMATCH: Speech could not be recognized.");
                }
            });

            recognizer.canceled.addEventListener((s, e) -> {
                System.out.println("CANCELED: Reason=" + e.getReason());

                if (e.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + e.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + e.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            });

            recognizer.sessionStarted.addEventListener((s, e) -> {
                System.out.println("\n    Session started event.");
            });

            recognizer.sessionStopped.addEventListener((s, e) -> {
                System.out.println("\n    Session stopped event.");
            });

            // Starts continuous recognition. Uses stopContinuousRecognitionAsync() to stop recognition.
            System.out.println("Say something...");
            recognizer.startContinuousRecognitionAsync().get();

            System.out.println("Press any key to stop");
            new Scanner(System.in).nextLine();

            recognizer.stopContinuousRecognitionAsync().get();
        }

        config.close();
        audioInput.close();
        recognizer.close();
        // </recognitionContinuousWithFile>
    }
}
