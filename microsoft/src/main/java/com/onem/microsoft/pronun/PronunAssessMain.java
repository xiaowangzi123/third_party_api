package com.microsoft.demo.pronun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.audio.AudioInputStream;
import com.microsoft.cognitiveservices.speech.audio.AudioStreamFormat;
import com.microsoft.cognitiveservices.speech.audio.PushAudioInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author wyq
 * @date 2022/6/30
 * @desc 发音评估
 */
public class PronunAssessMain {
    //    private static final String SPEECH_SUBSCRIPTION_KEY = "ed7790c795eb47d3a7f1a272ecea684c";
    //云译语音key
    private static final String SPEECH_SUBSCRIPTION_KEY = "a6bfa21a746648caaf84f977fb07f45f";
    private static final String SERVICE_REGION = "chinaeast2";
    // Pronunciation assessment with events from a push stream
    // This sample takes and existing file and reads it by chunk into a local buffer and then pushes the
    // buffer into an PushAudioStream for pronunciation assessment.
    // See more information at https://aka.ms/csspeech/pa
    private static Semaphore stopRecognitionSemaphore;

    public static void main(String[] args) {
        String path = "D:\\TestAudioVideo\\pronun\\test.wav";
        String text = "Today was a beautiful day. We had a great time taking a long walk outside in the morning. The countryside was in full bloom, yet the air was crisp and cold. Towards the end of the day, clouds came in, forecasting much needed rain.";
//        String text = "the weather is nice today. In the morning we took a walk outside and had a good time. The countryside is full of flowers, but the air is fresh and cold. Towards the end of the day, dark clouds were gathering, indicating an urgent need for rain.";
//        pronAssess(path, text);
        pronunAssessAsync(text, path);

    }

    public static void pronAssess(String path, String text) {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(path);

        //要请求 IPA 音素，请将音素字母设置为 "IPA"。 如果不指定字母表，则默认情况下音素为 SAPI 格式。
        JSONObject json = new JSONObject();
        json.put("referenceText", text);
        json.put("gradingSystem", "HundredMark");
        json.put("granularity", "Phoneme");
        json.put("phonemeAlphabet", "IPA");
        PronunciationAssessmentConfig pronunciationAssessmentConfig = PronunciationAssessmentConfig.fromJson(JSONObject.toJSONString(json));
//        PronunciationAssessmentConfig pronunciationAssessmentConfig = PronunciationAssessmentConfig.fromJson("{\"referenceText\":\"good morning\",\"gradingSystem\":\"HundredMark\",\"granularity\":\"Phoneme\",\"phonemeAlphabet\":\"IPA\"}");
        SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);
        SpeechRecognitionResult speechRecognitionResult = null;
        try {
            pronunciationAssessmentConfig.applyTo(speechRecognizer);
            Future<SpeechRecognitionResult> future = speechRecognizer.recognizeOnceAsync();
            speechRecognitionResult = future.get(30, TimeUnit.SECONDS);
            // The pronunciation assessment result as a Speech SDK object
//            PronunciationAssessmentResult pronunciationAssessmentResult = PronunciationAssessmentResult.fromResult(speechRecognitionResult);
//            System.out.println("result--->:" + pronunciationAssessmentResult);
            // The pronunciation assessment result as a JSON string

            if (speechRecognitionResult.getReason().equals(ResultReason.Canceled)) {
                System.out.println("识别失败！错误码：" + speechRecognitionResult.getReason());
                CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }
            if (ResultReason.RecognizedSpeech.equals(speechRecognitionResult.getReason())) {
                System.out.println("识别结束！状态码：" + speechRecognitionResult.getReason());
                PronunciationAssessmentResult pronResult = PronunciationAssessmentResult.fromResult(speechRecognitionResult);

                String resultJsonStr = speechRecognitionResult.getProperties().getProperty(PropertyId.SpeechServiceResponse_JsonResult);
                JSONObject resJson = JSON.parseObject(resultJsonStr);
                System.out.println("JSONResult--->:" + resJson.get("RecognitionStatus"));
                System.out.println("JSONResult2--->:" + resJson.get("NBest"));
                JSONArray resArr = (JSONArray) resJson.get("NBest");
                System.out.println("JSONResult3--->:" + resArr.get(0));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            speechRecognizer.close();
            speechConfig.close();
            audioConfig.close();
            pronunciationAssessmentConfig.close();
            speechRecognitionResult.close();
        }

    }

    public static void pronunAssessAsync(String referenceText, String path) {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        AudioConfig audioConfig = AudioConfig.fromWavFileInput(path);

        String lang = "en-US";
        speechConfig.setProperty(PropertyId.SpeechServiceConnection_EndSilenceTimeoutMs, "3000");
        PronunciationAssessmentConfig pronunciationConfig = new PronunciationAssessmentConfig(referenceText,
                PronunciationAssessmentGradingSystem.HundredMark, PronunciationAssessmentGranularity.Phoneme, true);

        SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, lang, audioConfig);
        SpeechRecognitionResult result = null;
        try {
//            pronunciationConfig.setReferenceText(referenceText);
            pronunciationConfig.applyTo(recognizer);
//            result = recognizer.recognizeOnceAsync().get();
            result = recognizer.recognizeOnceAsync().get(30, TimeUnit.SECONDS);

            if (result.getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("RECOGNIZED: Text=" + result.getText());
                System.out.println("  PRONUNCIATION ASSESSMENT RESULTS:");
                PronunciationAssessmentResult pronunciationResult = PronunciationAssessmentResult.fromResult(result);
                System.out.printf(
                        "    Accuracy score: %f, Pronunciation score: %f, Completeness score : %f, FluencyScore: %f%n",
                        pronunciationResult.getAccuracyScore(), pronunciationResult.getPronunciationScore(),
                        pronunciationResult.getCompletenessScore(), pronunciationResult.getFluencyScore());
            } else if (result.getReason() == ResultReason.NoMatch) {
                System.out.println("NOMATCH: Speech could not be recognized.");
            } else if (result.getReason() == ResultReason.Canceled) {
                CancellationDetails cancellation = CancellationDetails.fromResult(result);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you update the subscription info?");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(result).close();
            recognizer.close();
            pronunciationConfig.close();
            speechConfig.close();
        }


    }

    // Pronunciation assessment.
    // See more information at https://aka.ms/csspeech/pa
    public static void pronunciationAssessmentWithMicrophoneAsync() throws ExecutionException, InterruptedException {
        // Creates an instance of a speech config with specified subscription key and service region.
        // Replace with your own subscription key and service region (e.g., "westus").
        SpeechConfig config = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        // Replace the language with your language in BCP-47 format, e.g., en-US.
        String lang = "en-US";
        // The pronunciation assessment service has a longer default end silence timeout (5 seconds) than normal STT
        // as the pronunciation assessment is widely used in education scenario where kids have longer break in reading.
        // You can adjust the end silence timeout based on your real scenario.
        config.setProperty(PropertyId.SpeechServiceConnection_EndSilenceTimeoutMs, "3000");

        String referenceText = "Today was a beautiful day. We had a great time taking a long walk outside in the morning.";
        // create pronunciation assessment config, set grading system, granularity and if enable miscue based on your requirement.
        PronunciationAssessmentConfig pronunciationConfig = new PronunciationAssessmentConfig(referenceText,
                PronunciationAssessmentGradingSystem.HundredMark, PronunciationAssessmentGranularity.Phoneme, true);

        while (true) {
            // Creates a speech recognizer for the specified language, using microphone as audio input.
            SpeechRecognizer recognizer = new SpeechRecognizer(config, lang);
            {
                // Receives reference text from console input.
                System.out.println("Enter reference text you want to assess, or enter empty text to exit.");
                System.out.print("> ");
                referenceText = new Scanner(System.in).nextLine();
                if (referenceText.isEmpty()) {
                    break;
                }

                pronunciationConfig.setReferenceText(referenceText);

                // Starts recognizing.
                System.out.println("Read out \"" + referenceText + "\" for pronunciation assessment ...");

                pronunciationConfig.applyTo(recognizer);

                // Starts speech recognition, and returns after a single utterance is recognized.
                // For long-running multi-utterance recognition, use StartContinuousRecognitionAsync() instead.
                SpeechRecognitionResult result = recognizer.recognizeOnceAsync().get();

                // Checks result.
                if (result.getReason() == ResultReason.RecognizedSpeech) {
                    System.out.println("RECOGNIZED: Text=" + result.getText());
                    System.out.println("  PRONUNCIATION ASSESSMENT RESULTS:");

                    PronunciationAssessmentResult pronunciationResult = PronunciationAssessmentResult.fromResult(result);
                    System.out.println(
                            String.format(
                                    "    Accuracy score: %f, Pronunciation score: %f, Completeness score : %f, FluencyScore: %f",
                                    pronunciationResult.getAccuracyScore(), pronunciationResult.getPronunciationScore(),
                                    pronunciationResult.getCompletenessScore(), pronunciationResult.getFluencyScore()));
                } else if (result.getReason() == ResultReason.NoMatch) {
                    System.out.println("NOMATCH: Speech could not be recognized.");
                } else if (result.getReason() == ResultReason.Canceled) {
                    CancellationDetails cancellation = CancellationDetails.fromResult(result);
                    System.out.println("CANCELED: Reason=" + cancellation.getReason());

                    if (cancellation.getReason() == CancellationReason.Error) {
                        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                        System.out.println("CANCELED: Did you update the subscription info?");
                    }
                }

                result.close();
                recognizer.close();
            }
        }

        pronunciationConfig.close();
        config.close();
    }

    public static void pronunciationAssessmentWithPushStream() throws InterruptedException, IOException {
        // Creates an instance of a speech config with specified
        // subscription key and service region. Replace with your own subscription key
        // and service region (e.g., "westus").
        SpeechConfig config = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);

        // Replace the language with your language in BCP-47 format, e.g., en-US.
        String lang = "en-US";

        // Set audio format
        long samplesPerSecond = 16000;
        short bitsPerSample = 16;
        short channels = 1;

        // Whether to simulate the real time recording (need be set to true when measuring latency with streaming)
        boolean simulateRealtimeRecording = false;

        // Create the push stream to push audio to.
        PushAudioInputStream pushStream = AudioInputStream.createPushStream(AudioStreamFormat.getWaveFormatPCM(samplesPerSecond, bitsPerSample, channels));

        // Creates a speech recognizer using Push Stream as audio input.
        AudioConfig audioInput = AudioConfig.fromStreamInput(pushStream);

        SpeechRecognizer recognizer = new SpeechRecognizer(config, lang, audioInput);

        stopRecognitionSemaphore = new Semaphore(0);

        final long[] lastAudioUploadedTime = new long[1];
        recognizer.recognized.addEventListener((s, e) -> {
            if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("RECOGNIZED: Text=" + e.getResult().getText());
                PronunciationAssessmentResult pronunciationResult = PronunciationAssessmentResult.fromResult(e.getResult());
                System.out.println(
                        String.format(
                                "    Accuracy score: %f, Pronunciation score: %f, Completeness score : %f, FluencyScore: %f",
                                pronunciationResult.getAccuracyScore(), pronunciationResult.getPronunciationScore(),
                                pronunciationResult.getCompletenessScore(), pronunciationResult.getFluencyScore()));
                long resultReceivedTime = System.currentTimeMillis();
                System.out.println(String.format("Latency: %d ms", resultReceivedTime - lastAudioUploadedTime[0]));
            } else if (e.getResult().getReason() == ResultReason.NoMatch) {
                System.out.println("NOMATCH: Speech could not be recognized.");
            }
            stopRecognitionSemaphore.release();
        });

        recognizer.canceled.addEventListener((s, e) -> {
            System.out.println("CANCELED: Reason=" + e.getReason());

            if (e.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + e.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + e.getErrorDetails());
                System.out.println("CANCELED: Did you update the subscription info?");
            }

            stopRecognitionSemaphore.release();
        });

        recognizer.sessionStarted.addEventListener((s, e) -> {
            System.out.println("\n    Session started event.");
        });

        recognizer.sessionStopped.addEventListener((s, e) -> {
            System.out.println("\n    Session stopped event.");
        });

        String referenceText = "Hello world";
        // create pronunciation assessment config, set grading system, granularity and if enable miscue based on your requirement.
        PronunciationAssessmentConfig pronunciationConfig = new PronunciationAssessmentConfig(referenceText,
                PronunciationAssessmentGradingSystem.HundredMark, PronunciationAssessmentGranularity.Phoneme, true);
        pronunciationConfig.applyTo(recognizer);

        System.out.println("Assessing...");
        recognizer.recognizeOnceAsync();

        // Replace with your own audio file name.
        // The input stream the sample will read from.
        InputStream inputStream = new FileInputStream("YourAudioFile.wav");

        // Arbitrary buffer size.
        byte[] readBuffer = new byte[4096];

        // Push audio read from the file into the PushStream.
        // The audio can be pushed into the stream before, after, or during recognition
        // and recognition will continue as data becomes available.
        int bytesRead;
        while ((bytesRead = inputStream.read(readBuffer)) != -1) {
            if (bytesRead == readBuffer.length) {
                pushStream.write(readBuffer);
            } else {
                // Last buffer read from the WAV file is likely to have less bytes
                pushStream.write(Arrays.copyOfRange(readBuffer, 0, bytesRead));
            }

            if (simulateRealtimeRecording) {
                // Sleep corresponding time for the uploaded audio chunk, to simulate the natural speaking rate.
                Thread.sleep(bytesRead * 1000 / (bitsPerSample / 8) / samplesPerSecond / channels);
            }
        }

        inputStream.close();
        // Signal the end of stream to stop assessment
        pushStream.close();

        lastAudioUploadedTime[0] = System.currentTimeMillis();

        stopRecognitionSemaphore.acquire();

        System.out.println("Press any key to stop");
        new Scanner(System.in).nextLine();

        config.close();
        audioInput.close();
        recognizer.close();
    }

}
