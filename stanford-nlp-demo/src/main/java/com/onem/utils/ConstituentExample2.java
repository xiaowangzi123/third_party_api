package com.onem.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.onem.constant.ContentConstants;
import com.onem.entity.SyntaxResultDto;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.EnglishGrammaticalStructure;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author wyq
 * @date 2023/5/4
 * @desc
 */
public class ConstituentExample2 {

    public static void main(String[] args) {
        // set up pipeline properties
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
//        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,sentiment");

//        props.setProperty("pos.model", "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
        // set up Stanford CoreNLP pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // build annotation for a review
//        Annotation annotation = new Annotation("The small red car turned very quickly around the corner.");
        Annotation annotation = new Annotation(ContentConstants.CONTENT01);
        // annotate
        pipeline.annotate(annotation);

        SyntaxResultDto dto = new SyntaxResultDto();
        List<String> constituency = new ArrayList<>();
        List<List<String>> dependencyList = new ArrayList<>();
        List<CoreMap> coreMapList = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap coreMap : coreMapList) {
            //成分句法分析
            Tree tree = coreMap.get(TreeCoreAnnotations.TreeAnnotation.class);
            constituency.add(tree.pennString());
            //句法依存分析
            EnglishGrammaticalStructure egs = new EnglishGrammaticalStructure(tree);
            Collection<TypedDependency> td = egs.typedDependenciesCollapsed();
            dependencyList.add(td.stream().map(Object::toString).collect(Collectors.toList()));
        }
        dto.setConstituency(constituency);
        dto.setDependencyList(dependencyList);

        String s = JSON.toJSONString(dto.getConstituency());
        String s2 = JSON.toJSONString(dto.getDependencyList());

        List<String> l1 = JSON.parseArray(s, String.class);

        List<List> l2 = JSONArray.parseArray(s2,List.class);
        List<List<String>> l3 = JSON.parseObject(s2, new com.alibaba.fastjson.TypeReference<List<List<String>>>(){});
        System.out.println(l1);
        System.out.println(l2);
        List<String> list = l2.get(0);
        System.out.println(list);
        System.out.println("----------");
        System.out.println(l3);


    }
}
