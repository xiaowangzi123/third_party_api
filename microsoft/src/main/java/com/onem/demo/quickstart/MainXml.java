package com.onem.demo.quickstart;

import com.microsoft.cognitiveservices.speech.AudioDataStream;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author wyq
 * @date 2022/4/25
 * @desc
 */
public class MainXml {
    public static void main(String[] args) {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription("<paste-your-speech-key-here>", "<paste-your-speech-location/region-here>");
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, null);

        String ssml = xmlToString("C:\\TestAudioVideo\\audio\\ssml.xml");
        System.out.println(ssml);
        SpeechSynthesisResult result = synthesizer.SpeakSsml(ssml);
        AudioDataStream stream = AudioDataStream.fromResult(result);
        stream.saveToWavFile("C:\\TestAudioVideo\\audio\\out" + System.currentTimeMillis() + ".wav");
    }

    private static String xmlToString(String filePath) {
        File file = new File(filePath);
        StringBuilder fileContents = new StringBuilder((int)file.length());

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }
            return fileContents.toString().trim();
        } catch (FileNotFoundException ex) {
            return "File not found.";
        }
    }
}
