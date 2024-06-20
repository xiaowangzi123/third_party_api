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
 * @desc 录音文件转写为文字
 */
@Slf4j
public class SpeechMain {
    public static void main(String[] args) {
        String wavPath = "C:\\temp\\temp\\184d45e0-2347-4a1b-b1b3-d19dff7b4129.wav";
        String langCode = "zh-CN";
        fromFile(langCode, wavPath);
    }

    public static void fromFile(String langCode, String wavPath) {
        long t1 = System.currentTimeMillis();
        SpeechConfig speechConfig = SpeechConfig.fromSubscription("510b12c9d6374bc386786b4ac31f6c80", "chinaeast2");
        // 识别语言编码
        speechConfig.setSpeechRecognitionLanguage(langCode);
        // 识别的偏移量和持续时间
        speechConfig.requestWordLevelTimestamps();
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
//            log.info("RECOGNIZING: 偏移量：{}, 持续时间:{},  Text={}", e.getResult().getOffset().divide(BigInteger.valueOf(10000)), e.getResult().getDuration().divide(BigInteger.valueOf(10000)), e.getResult().getText());
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

            recognizer.stopContinuousRecognitionAsync().get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        speechConfig.close();
        audioConfig.close();
        recognizer.close();


        log.info("--------------------------------------");
        log.info("识别耗时：{}", (System.currentTimeMillis() - t1) / 1000);
        log.info("--------------------------------------");
        log.info("{}", JSON.toJSONString(sequencesList));
        log.info("--------------------------------------");
        log.info("{}", JSON.toJSONString(segTimeDtoList));
        log.info("--------------------------------------");
        /*for (SegTimeDto dto : segTimeDtoList) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(JSON.toJSONString(dto, true));
        }*/

    }
}
