package com.onem.utils;

import com.alibaba.fastjson.JSON;
import com.onem.constant.ContentConstants;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author wyq
 * @date 2023/5/4
 * @desc
 */
public class SegSplitExample {

    public static void main(String[] args) {
        // set up pipeline properties
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
//        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,sentiment");

//         StanfordCoreNLP pipeline = new StanfordCoreNLP(
//        PropertiesUtils.asProperties(
//            "annotators", "tokenize,ssplit",
//            "tokenize.language", "en"));
//        props.setProperty("pos.model", "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
        // set up Stanford CoreNLP pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // build annotation for a review
//        Annotation annotation = new Annotation("The small red car turned very quickly around the corner.");
        Annotation annotation = new Annotation(ContentConstants.CONTENT01);
//        Annotation annotation = new Annotation(ContentConstants.CONTENT02);
        // annotate
        pipeline.annotate(annotation);


        List<CoreMap> coreMaps = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        //获取分句
        List<String> collect = annotation.get(CoreAnnotations.SentencesAnnotation.class).stream().map(a -> a.get(CoreAnnotations.TextAnnotation.class)).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));

        for (CoreMap map : coreMaps) {
            System.out.println(map.keySet());
            String s = map.get(CoreAnnotations.TextAnnotation.class);
            System.out.println(s);
            List<CoreLabel> labelList = map.get(CoreAnnotations.TokensAnnotation.class);
            System.out.println(labelList.get(0));
        }
    }
}
