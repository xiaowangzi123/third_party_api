package com.onem.demo.speechrrecognizer;

import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.demo.sr.SegTimeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author wyq
 * @date 2022/10/9
 * @desc
 */

@Slf4j
public class TransMain {
    private static final String SPEECH_SUBSCRIPTION_KEY = "ed7790c795eb47d3a7f1a272ecea684c";
    //    private static final String SPEECH_SUBSCRIPTION_KEY = "a6bfa21a746648caaf84f977fb07f45f";
    private static final String SERVICE_REGION = "chinaeast2";
    public static void main(String[] args) {

    }

    private void fromMic( String audioFilePath,String srcLangCode) throws ExecutionException, InterruptedException {

        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(audioFilePath);

        //  识别语言编码
        speechConfig.setSpeechRecognitionLanguage(srcLangCode);

        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);
        Semaphore stopTranslationWithFileSemaphore = new Semaphore(0);

        recognizer.recognizing.addEventListener((s, e) -> {
            SegTimeDto segTimeDto = new SegTimeDto();
            segTimeDto.setText(e.getResult().getText());
            segTimeDto.setDuration(e.getResult().getDuration().divide(BigInteger.valueOf(10000)).intValue());
            segTimeDto.setOffset(e.getResult().getOffset().divide(BigInteger.valueOf(10000)).intValue());
            log.info("RECOGNIZING: Text=" + e.getResult().getText() + " 偏移量: " + e.getResult().getOffset().divide(BigInteger.valueOf(10000)) + " 持续时间：" + e.getResult().getDuration().divide(BigInteger.valueOf(10000)));
        });

        recognizer.recognized.addEventListener((s, e) -> {
            if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                if (StringUtils.isNotBlank(e.getResult().getText())) {
                    SegTimeDto segTimeDto = new SegTimeDto();
                    segTimeDto.setText(e.getResult().getText());
                    segTimeDto.setDuration(e.getResult().getDuration().divide(BigInteger.valueOf(10000)).intValue());
                    segTimeDto.setOffset(e.getResult().getOffset().divide(BigInteger.valueOf(10000)).intValue());
//                    sequencesList.add(segTimeDto);
                    log.info("RECOGNIZING: Text=" + e.getResult().getText() + "  偏移量: " + e.getResult().getOffset().divide(BigInteger.valueOf(10000)) + "    持续时间：" + e.getResult().getDuration().divide(BigInteger.valueOf(10000)));
                }
            } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                log.info("NOMATCH: Speech could not be recognized.");
            }
        });

        recognizer.canceled.addEventListener((s, e) -> {
            log.info("CANCELED: Reason=" + e.getReason());

            if (e.getReason() == CancellationReason.Error) {
                log.info("CANCELED: ErrorCode=" + e.getErrorCode());
                log.info("CANCELED: ErrorDetails=" + e.getErrorDetails());
                log.info("CANCELED: Did you set the speech resource key and region values?");
            }

            stopTranslationWithFileSemaphore.release();
        });

        recognizer.sessionStopped.addEventListener((s, e) -> {
            log.info("\n    Session stopped event.");
            stopTranslationWithFileSemaphore.release();
        });

        // Starts continuous recognition. Uses StopContinuousRecognitionAsync() to stop recognition.
        recognizer.startContinuousRecognitionAsync().get();

        // Waits for completion.
        stopTranslationWithFileSemaphore.acquire();

//        log.info("Number of segments in source language: {}", sequencesList.size());

    }
}
