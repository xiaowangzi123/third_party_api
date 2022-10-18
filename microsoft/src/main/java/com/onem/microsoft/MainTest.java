package com.onem.microsoft;


import com.alibaba.fastjson.JSON;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

/**
 * @author wyq
 * @date 2022/9/9
 * @desc 语音识别
 */
public class MainTest {
    private static final String SPEECH_SUBSCRIPTION_KEY = "ed7790c795eb47d3a7f1a272ecea684c";
    //    private static final String SPEECH_SUBSCRIPTION_KEY = "a6bfa21a746648caaf84f977fb07f45f";
    private static final String SERVICE_REGION = "chinaeast2";

    public static void main(String[] args) {
        String path = "F:\\Github\\third_party_api\\audio\\unisound.wav";
        String lang = "zh-CN";
        speechRecognition(path, lang);
    }

    public static void speechRecognition(String audioPath, String srcLangCode) {

        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        List<SegTimeDto> childSegTimeDtoList = new ArrayList<>();
        List<SegTimeDto> parentSegTimeDtoList = new ArrayList<>();
        //识别语言编码
        speechConfig.setSpeechRecognitionLanguage(srcLangCode);
        //识别的偏移量和持续时间
        speechConfig.requestWordLevelTimestamps();
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(audioPath);
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);
        Semaphore stopTranslationWithFileSemaphore = new Semaphore(0);

        recognizer.recognizing.addEventListener((s, e) -> {
            SegTimeDto segTimeDto = new SegTimeDto();
            segTimeDto.setText(e.getResult().getText());
            segTimeDto.setDuration(e.getResult().getDuration().divide(BigInteger.valueOf(10000)).intValue());
            segTimeDto.setOffset(e.getResult().getOffset().divide(BigInteger.valueOf(10000)).intValue());
            childSegTimeDtoList.add(segTimeDto);
            System.out.println("RECOGNIZING: Text=" + e.getResult().getText() + "  偏移量: " + e.getResult().getOffset().divide(BigInteger.valueOf(10000)) + "    持续时间：" + e.getResult().getDuration().divide(BigInteger.valueOf(10000)));
        });

        System.out.println("----------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------");
        recognizer.recognized.addEventListener((s, e) -> {
            if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                if (StringUtils.isNotBlank(e.getResult().getText())) {
                    SegTimeDto segTimeDto = new SegTimeDto();
                    segTimeDto.setText(e.getResult().getText());
                    segTimeDto.setDuration(e.getResult().getDuration().divide(BigInteger.valueOf(10000)).intValue());
                    segTimeDto.setOffset(e.getResult().getOffset().divide(BigInteger.valueOf(10000)).intValue());
                    parentSegTimeDtoList.add(segTimeDto);
                    System.out.println("Text=" + e.getResult().getText() + "  偏移量: " + e.getResult().getOffset().divide(BigInteger.valueOf(10000)) + "    持续时间：" + e.getResult().getDuration().divide(BigInteger.valueOf(10000)));

                }
            } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                System.out.println("NOMATCH: Speech could not be recognized.");

            }
        });

        System.out.println("==========================================================");
        System.out.println(JSON.toJSONString(parentSegTimeDtoList));

        System.out.println("==========================================================");
        recognizer.canceled.addEventListener((s, e) -> {
            System.out.println("CANCELED: Reason=" + e.getReason());

            if (e.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + e.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + e.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
            }

            stopTranslationWithFileSemaphore.release();
        });

        recognizer.sessionStopped.addEventListener((s, e) -> {
            System.out.println("\n    Session stopped event.");
            stopTranslationWithFileSemaphore.release();
        });

        // Starts continuous recognition. Uses StopContinuousRecognitionAsync() to stop recognition.
        try {
            recognizer.startContinuousRecognitionAsync().get();
            stopTranslationWithFileSemaphore.acquire();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        final RecognitionTextDto recognitionTextDto = new RecognitionTextDto();
        recognitionTextDto.setChildSegTimeDtoList(childSegTimeDtoList);
        recognitionTextDto.setParentSegTimeDtoList(parentSegTimeDtoList);


    }
}
