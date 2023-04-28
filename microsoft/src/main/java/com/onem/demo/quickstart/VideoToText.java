package com.onem.demo.quickstart;

import com.microsoft.cognitiveservices.speech.SourceLanguageConfig;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author wyq
 * @date 2022/4/25
 * @desc
 */
public class VideoToText {
    private static final String SPEECH_SUBSCRIPTION_KEY = "49989ad01d5a434686dd31d82e094496";
    private static final String SERVICE_REGION = "chinaeast2";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        fromFile(speechConfig);
    }

    public static void fromFile(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        AudioConfig audioConfig = AudioConfig.fromWavFileInput("C:\\TestAudioVideo\\out123.mp3");
//        SourceLanguageConfig sourceLanguageConfig = SourceLanguageConfig.fromLanguage("zh-CN");
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);
//        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, sourceLanguageConfig, audioConfig);

        Future<SpeechRecognitionResult> task = recognizer.recognizeOnceAsync();
        SpeechRecognitionResult result = task.get();
        System.out.println("RECOGNIZED: Text=" + result.getText());
    }
}
