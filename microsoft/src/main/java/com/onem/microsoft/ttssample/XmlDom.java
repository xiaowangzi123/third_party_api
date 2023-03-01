package com.microsoft.demo.ttssample;

/**
 * @author wyq
 * @date 2022/4/24
 * @desc
 */

import com.microsoft.demo.entity.SpeakDom;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.Scanner;

public class XmlDom {
    private static final String ZH_TEXT ="北大红楼一楼西头的阅览室，是青年毛泽东曾经工作过的地方。当年，这位操着湖南口音的年轻人，在忙碌地为大家办理借阅手续之余，还通过阅读“迅速地朝着马克思主义的方向发展”。后来，毛泽东同志在延安接受埃德加·斯诺采访时说：“我热心地搜寻那时候能找到的为数不多的用中文写的共产主义书籍”“我一旦接受了马克思主义是对历史的正确解释以后，我对马克思主义的信仰就没有动摇过”。读书的魅力与作用，可见一斑。";
    private static final String EN_TEXT = "Race is an issue that unites and divides America. There is no doubt that blacks have been the victims of prejudice. Enslavement for hundreds of years is a fact that will not go away. But the U.S. Constitution calls for “equal protection,” which means everyone is treated the same way. Color of skin or gender does not matter.";

    public static void main(String[] args) {
//        String str = createDom("locale1",Gender.Male,"en-US-JennyNeural",EN_TEXT);
//        String str = createDom("locale1",Gender.Male,"zh-CN-YunyangNeural",ZH_TEXT);
//        System.out.println(str);
//        SpeakDom speakDom = new SpeakDom();
//        System.out.println(createDom(speakDom));
        String ssml = xmlToString("C:\\TestAudioVideo\\audio\\ssml.xml");
        System.out.println(ssml);
    }
    public static String createDom(SpeakDom speakDom) {
        Document doc = null;
        Element speak, voice, expressAs, prosody;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            doc = builder.newDocument();
            if (doc != null) {
                speak = doc.createElement("speak");
                speak.setAttribute("version", "1.0");
                speak.setAttribute("xmlns", "http://www.w3.org/2001/10/synthesis");
                speak.setAttribute("xmlns:mstts", "https://www.w3.org/2001/mstts");
                speak.setAttribute("xml:lang", speakDom.getLocaleLang());
                voice = doc.createElement("voice");
                voice.setAttribute("name", speakDom.getVoiceDom().getVoiceName());
//                voice.appendChild(doc.createTextNode(EN_TEXT));
                //设置说话风格
                expressAs = doc.createElement("mstts:express-as");
                expressAs.setAttribute("style",speakDom.getExpressStyle().getStyle());
                expressAs.setAttribute("styledegree",speakDom.getExpressStyle().getStyledegree());
                expressAs.setAttribute("role",speakDom.getExpressStyle().getRole());
//                expressAs.appendChild(doc.createTextNode(EN_TEXT));

                //设置音量音速音高
                prosody = doc.createElement("prosody");
                prosody.setAttribute("rate",speakDom.getProsody().getRate());
                prosody.setAttribute("volume",speakDom.getProsody().getVolume());
                prosody.setAttribute("pitch",speakDom.getProsody().getPitch());
                prosody.appendChild(doc.createTextNode(speakDom.getText()));
                expressAs.appendChild(prosody);
                voice.appendChild(expressAs);
                speak.appendChild(voice);
                doc.appendChild(speak);
            }
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return transformDom(doc);
    }
    public static String createDom(String locale, String genderName, String voiceName, String textToSynthesize) {
        Document doc = null;
        Element speak, voice;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            doc = builder.newDocument();
            if (doc != null) {
                speak = doc.createElement("speak");
                speak.setAttribute("version", "1.0");
                speak.setAttribute("xmlns", "http://www.w3.org/2001/10/synthesis");
                speak.setAttribute("xml:lang", "en-US");
                voice = doc.createElement("voice");
                voice.setAttribute("name", voiceName);
//                voice.setAttribute("xml:lang", locale);
//                voice.setAttribute("xml:gender", genderName);
                voice.appendChild(doc.createTextNode(textToSynthesize));
                speak.appendChild(voice);
                doc.appendChild(speak);
            }
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return transformDom(doc);
    }

    private static String transformDom(Document doc) {
        StringWriter writer = new StringWriter();
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }


    public static String xmlToString(String filePath) {
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
