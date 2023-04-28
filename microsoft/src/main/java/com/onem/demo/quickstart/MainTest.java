package com.onem.demo.quickstart;

import com.alibaba.fastjson.JSON;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.demo.entity.ExpressStyle;
import com.microsoft.demo.entity.Prosody;
import com.microsoft.demo.entity.SpeakDom;
import com.microsoft.demo.entity.VoiceDom;
import com.onem.demo.utils.DateUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;


/**
 * Quickstart: synthesize speech using the Speech SDK for Java.
 * 微软语音生成
 */
public class MainTest {
    private static final String SPEECH_SUBSCRIPTION_KEY = "ed7790c795eb47d3a7f1a272ecea684c";
//    private static final String SPEECH_SUBSCRIPTION_KEY = "a6bfa21a746648caaf84f977fb07f45f";
    private static final String SERVICE_REGION = "chinaeast2";
    private static final String ZH_TEXT = "北大红楼一楼西头的阅览室，是青年毛泽东曾经工作过的地方。当年，这位操着湖南口音的年轻人，在忙碌地为大家办理借阅手续之余，还通过阅读“迅速地朝着马克思主义的方向发展”。后来，毛泽东同志在延安接受埃德加·斯诺采访时说：“我热心地搜寻那时候能找到的为数不多的用中文写的共产主义书籍”“我一旦接受了马克思主义是对历史的正确解释以后，我对马克思主义的信仰就没有动摇过”。读书的魅力与作用，可见一斑。";
    private static final String ZH_TEXT_SHORT = "我热心地搜寻那时候能找到的为数不多的用中文写的共产主义书籍，我一旦接受了马克思主义是对历史的正确解释以后，我对马克思主义的信仰就没有动摇过。";
    private static final String EN_TEXT = "Race is an issue that unites and divides America. There is no doubt that blacks have been the victims of prejudice. Enslavement for hundreds of years is a fact that will not go away. But the U.S. Constitution calls for “equal protection,” which means everyone is treated the same way. Color of skin or gender does not matter.";
    private static final String EN_TEXT_SHORT = "There is no doubt that blacks have been the victims of prejudice.";


    public static void main(String[] args) {
//        outputSpeaker();
//        textToSpeech();
//
       /* for (int i = 0; i < 10; i++) {
        }*/
        XmlToSpeech();


    }

    /**
     * 输出到扬声器
     */
    public static void outputSpeaker() {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        AudioConfig audioConfig = AudioConfig.fromDefaultSpeakerOutput();
        //文本如果文本内容是中文，需要响应的语言设置
        speechConfig.setSpeechSynthesisLanguage("zh-CN");
        speechConfig.setSpeechSynthesisVoiceName("zh-CN-YunxiNeural");

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
        SpeechSynthesisResult result = synthesizer.SpeakText(ZH_TEXT_SHORT);
        AudioDataStream stream = AudioDataStream.fromResult(result);
        System.out.println(stream.getStatus());
    }

    public static void textToSpeech() {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        // https://docs.microsoft.com/azure/cognitive-services/speech-service/language-support
        speechConfig.setSpeechSynthesisLanguage("zh-CN");
        speechConfig.setSpeechSynthesisVoiceName("zh-CN-YunxiNeural");

        // set the output format
//        speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Riff24Khz16BitMonoPcm);
        speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Audio16Khz64KBitRateMonoMp3);

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, null);
        SpeechSynthesisResult result = synthesizer.SpeakText(ZH_TEXT_SHORT);
        AudioDataStream stream = AudioDataStream.fromResult(result);
        System.out.println(stream.getStatus());
        stream.saveToWavFile("C:/TestAudioVideo/audio/out" + System.currentTimeMillis() + ".mp3");

    }

    public static void XmlToSpeech() {
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION);
        speechConfig.setProperty(PropertyId.Speech_LogFilename, "C:\\TestAudioVideo\\audio\\log123.txt");

        //
        speechConfig.setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Audio48Khz192KBitRateMonoMp3);
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, null);
        SpeakDom speakDom = new SpeakDom();
        speakDom.setLocaleLang("zh-CN");
        speakDom.setText(ZH_TEXT_SHORT);

        VoiceDom voiceDom = new VoiceDom();
        voiceDom.setVoiceName("zh-CN-YunyeNeural");
//        voiceDom.setVoiceName("en-US-GuyNeural");
        speakDom.setVoiceDom(voiceDom);

        ExpressStyle style = new ExpressStyle();
//        style.setStyle("calm");
        style.setStyle("Default");
//        style.setRole("YoungAdultMale");
        style.setRole("Default");
//        style.setStyledegree("1.4");
        style.setStyledegree("1");

        speakDom.setExpressStyle(style);

        Prosody prosody = new Prosody();
        prosody.setRate("10%");
        prosody.setPitch("0%");
        prosody.setVolume("-100.00%");

        speakDom.setProsody(prosody);

//        String ssml = XmlDom.createDom(speakDom);
//        String ssml = XmlDom.createDom("","","en-US-JennyNeural",EN_TEXT_SHORT);

//        System.out.println("xml--> "+ssml);
//        String ssml = xmlToString("C:\\TestAudioVideo\\audio\\ssml_en.xml");
        String ssml = xmlToString("C:\\TestAudioVideo\\audio\\ssml_zh_yunxi.xml");
//        String ssml = xmlToString("C:\\TestAudioVideo\\audio\\ssml_en_en-jenny.xml");
        System.out.println(ssml);
        SpeechSynthesisResult result = synthesizer.SpeakSsml(ssml);
        if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
            if (ArrayUtils.isNotEmpty(result.getAudioData())) {
                AudioDataStream stream = AudioDataStream.fromResult(result);
                //"AllData".equals(stream.getStatus())
                System.out.println("stream--> " + JSON.toJSONString(stream));
                System.out.println("status--> " + stream.getStatus());
                stream.saveToWavFile("C:\\TestAudioVideo\\audio\\out_" + DateUtil.getDateDefStr(new Date()) + ".mp3");
            } else {
                System.out.println("result--> " + JSON.toJSONString(result));
//                {"audioData":"","audioDuration":0,"audioLength":0,"impl":{"value":2743249730080},"properties":{},"reason":"SynthesizingAudioCompleted","resultId":"4bc533a6949c43809ef69e36073b7b42"}
                System.out.println("参数错误");
            }
        } else {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(result);
            System.out.println("cancel1: " + JSON.toJSONString(cancellation));
        }


    }

    public static String xmlToString(String filePath) {
        File file = new File(filePath);
        StringBuilder fileContents = new StringBuilder((int) file.length());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + System.lineSeparator());
            }
            return fileContents.toString().trim();
        } catch (FileNotFoundException ex) {
            return "File not found.";
        }
    }

}

