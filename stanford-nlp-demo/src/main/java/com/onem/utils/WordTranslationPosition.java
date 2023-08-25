package com.onem.utils;

import com.alibaba.fastjson.JSON;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

/**
 * @author wyq
 * @date 2023/6/5
 * @desc
 */
public class WordTranslationPosition {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,sentiment");
        props.setProperty("ner.model", "edu/stanford/nlp/models/ner/chinese.misc.distsim.crf.ser.gz");
        props.setProperty("parse.model", "edu/stanford/nlp/models/lexparser/chinesePCFG.ser.gz");
        props.setProperty("depparse.model", "edu/stanford/nlp/models/parser/nndep/UD_Chinese.gz");
        //自定义功能(3)
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // 原文本
        String text = "一年来，改革开放不断催生发展活力。";

        // 创建一个Annotation对象，并将文本添加到该对象中
        Annotation annotation = new Annotation(text);

        // 使用分析管道对文本进行处理，得到表示文本的CoreMap对象
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        CoreMap sentence = sentences.get(0);

        // 获取语义依存分析结果，将每个单词的依存关系转换为IndexedWord对象
//        SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
        SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
        List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
        IndexedWord[] iwords = new IndexedWord[tokens.size() + 1];
        for (CoreLabel token : tokens) {
            int index = token.get(CoreAnnotations.IndexAnnotation.class);
            iwords[index] = new IndexedWord(token);
        }

        // 在原文中查找需要翻译的词汇所对应的IndexedWord，获取该词在句子中的位置
        String targetWord = "改革开放";
        IndexedWord targetIword = null;
        for (IndexedWord iword : iwords) {
            if (iword != null && iword.originalText().equals(targetWord)) {
                targetIword = iword;
                break;
            }
        }

        // 查找目标词汇所在的短语，并确定该短语在翻译后的位置
        /*StringBuilder sb = new StringBuilder();
        GrammaticalRelation rel = dependencies.getParent(targetIword).getRelation();
        while (!rel.equals(GrammaticalRelation.ROOT)) {
      .insert(0, targetIword.backingLabel().word() + " ");
            targetIword = dependencies.getParent(targetIword);
            rel = dependencies.getParent(targetIword).getRelation();
        }
    .insert(0, targetIword.backingLabel().word() + " ");
        System.out.println(.toString() + " will be translated to position " + targetIword.index() + " in the translated sentence.")
*/
        System.out.println(JSON.toJSONString(targetIword));
    }
}

