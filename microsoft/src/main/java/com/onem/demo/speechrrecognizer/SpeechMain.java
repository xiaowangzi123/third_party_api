package com.onem.demo.speechrrecognizer;

import com.alibaba.fastjson.JSON;
import com.microsoft.cognitiveservices.speech.CancellationReason;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.onem.demo.sr.SegTimeDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

/**
 * @author wyq
 * @date 2024/6/17
 * @desc
 */
@Slf4j
public class SpeechMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription("510b12c9d6374bc386786b4ac31f6c80", "chinaeast2");
        fromFile(speechConfig);
    }

    public static void fromFile(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
        String wavPath = "C:\\temp\\temp\\184d45e0-2347-4a1b-b1b3-d19dff7b4129.wav";
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(wavPath);
        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig);
        Semaphore stopTranslationWithFileSemaphore = new Semaphore(0);

        List<SegTimeDto> sequencesList = new ArrayList<>();
        List<SegTimeDto> segTimeDtoList = new ArrayList<>();
        recognizer.recognizing.addEventListener((s, e) -> {
            SegTimeDto segTimeDto = new SegTimeDto();
            segTimeDto.setText(e.getResult().getText());
            segTimeDto.setDuration(e.getResult().getDuration().divide(BigInteger.valueOf(10000)).intValue());
            segTimeDto.setOffset(e.getResult().getOffset().divide(BigInteger.valueOf(10000)).intValue());
            segTimeDtoList.add(segTimeDto);
            log.info("RECOGNIZING: 偏移量：{}, 持续时间:{},  Text={}", e.getResult().getOffset().divide(BigInteger.valueOf(10000)), e.getResult().getDuration().divide(BigInteger.valueOf(10000)), e.getResult().getText());
        });

        recognizer.recognized.addEventListener((s, e) -> {
            if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                if (StringUtils.isNotBlank(e.getResult().getText())) {
                    int endTime = e.getResult().getOffset().add(e.getResult().getDuration()).divide(new BigInteger("10000")).intValue();
                    log.debug("截止时间:{}", endTime);
                    SegTimeDto segTimeDto = new SegTimeDto();
                    segTimeDto.setText(e.getResult().getText());
                    segTimeDto.setDuration(e.getResult().getDuration().divide(BigInteger.valueOf(10000)).intValue());
                    segTimeDto.setOffset(e.getResult().getOffset().divide(BigInteger.valueOf(10000)).intValue());
                    sequencesList.add(segTimeDto);
                    log.info("RECOGNIZED: 偏移量:{} 持续时间:{}  Text={}", e.getResult().getOffset().divide(BigInteger.valueOf(10000)),
                            e.getResult().getDuration().divide(BigInteger.valueOf(10000)), e.getResult().getText());
                }
            } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                log.warn("NOMATCH: Speech could not be recognized.");
            }
        });

        recognizer.canceled.addEventListener((s, e) -> {
            log.error("CANCELED: Reason=" + e.getReason());

            if (e.getReason() == CancellationReason.Error) {
                log.error("CANCELED: ErrorCode=" + e.getErrorCode());
                log.error("CANCELED: ErrorDetails=" + e.getErrorDetails());
                log.error("CANCELED: Did you set the speech resource key and region values?");
            }

            stopTranslationWithFileSemaphore.release();
        });

        recognizer.sessionStopped.addEventListener((s, e) -> {
            log.info("\n    Session stopped event.");
            stopTranslationWithFileSemaphore.release();
        });

        try {
            // Starts continuous recognition. Uses StopContinuousRecognitionAsync() to stop recognition.
            recognizer.startContinuousRecognitionAsync().get();
            // Waits for completion.
            stopTranslationWithFileSemaphore.acquire();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        log.info("--------------------------------------");
        log.info("{}", JSON.toJSONString(sequencesList));
        log.info("--------------------------------------");
        log.info("{}", JSON.toJSONString(segTimeDtoList));
    }
}
