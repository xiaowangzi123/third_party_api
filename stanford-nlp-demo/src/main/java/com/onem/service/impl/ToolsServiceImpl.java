/*
package com.onem.service.impl;

import com.onem.service.ToolsService;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;



@Slf4j
@Service
public class ToolsServiceImpl implements ToolsService {
    @Resource
    private StanfordCoreNLP stanfordCoreNLP;
    @Resource
    private LexicalizedParser lexicalizedParser;

    private static final String TAIWAN_STR = "Taiwan";



    @Override
    public String entityTag(String content) {
        long t1 = System.currentTimeMillis();
        Annotation document = new Annotation(content);
        stanfordCoreNLP.annotate(document);
        //处理之后的结果
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder tagContent = new StringBuilder();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);            // 获取分词
                String type = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);    // 获取命名实体识别结果

            }
            tagContent.append("\n\r");
        }
        log.info("实体标注耗时:{}", System.currentTimeMillis() - t1);
        return tagContent.toString();
    }

    @Override
    public String syntaxTag(String content) {
        long t1 = System.currentTimeMillis();
        Annotation document = new Annotation(content);
        stanfordCoreNLP.annotate(document);
        //处理之后的结果
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder tagContent = new StringBuilder();
        for (CoreMap sentence : sentences) {
            Tree tree = lexicalizedParser.parse(sentence.toString());

            tagContent.append("\n\r");
        }
        log.info("句法标注耗时:{}", System.currentTimeMillis() - t1);
        return tagContent.toString();
    }


    public String syntaxTag2(String content) {
        //去掉p标签
        long t1 = System.currentTimeMillis();
        Annotation document = new Annotation(content);
        stanfordCoreNLP.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder tagContent = new StringBuilder();
        for (CoreMap sentence : sentences) {
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            tagContent.append(tree).append("\n");
        }
        log.info("句法标注耗时:{}", System.currentTimeMillis() - t1);
        return tagContent.toString();
    }

    @Override
    public String posTagging(String content) {
        long t1 = System.currentTimeMillis();
//        //去掉p标签
        Annotation document = new Annotation(content);
        stanfordCoreNLP.annotate(document);
        //处理之后的结果
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        StringBuilder tagContent = new StringBuilder();
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // 获取分词
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                // 获取词性标注
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

            }
            tagContent.append("\n\r");
        }
        log.info("词性标注耗时:{}", System.currentTimeMillis() - t1);
        return tagContent.toString();
    }

    @Override
    public List<String> contentSegSplit(String content) {
        if (StringUtils.isEmpty(content)) {
            return Collections.emptyList();
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
            log.info("句子切分出现错误");
        }
        return Collections.emptyList();
    }


}
*/
