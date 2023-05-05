package com.onem.utils;

import com.alibaba.fastjson.JSON;
import com.onem.Constants;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.international.pennchinese.ChineseGrammaticalStructure;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wyq
 * @date 2023/5/4
 * @desc
 */
public class ConstituentExample {

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
        Annotation annotation = new Annotation(Constants.CONTENT01);
        // annotate
        pipeline.annotate(annotation);


        // get tree
        Tree tree = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0)
                .get(TreeCoreAnnotations.TreeAnnotation.class);
        String s = tree.toString();
        System.out.println("句法--->" + s);

        //句法分析树
        String s1 = tree.pennString();
        System.out.println("句法分析树--->");
        System.out.println(s1);
        System.out.println(JSON.toJSONString(s1));

//        tree.pennPrint();

        ChineseGrammaticalStructure gs = new ChineseGrammaticalStructure(tree);
        Collection<TypedDependency> tdl = gs.typedDependenciesCollapsed();

        System.out.println("中文依存句法分析--->");
        System.out.println(tdl.toString());

        EnglishGrammaticalStructure egs = new EnglishGrammaticalStructure(tree);
        Collection<TypedDependency> td = egs.typedDependenciesCollapsed();
        System.out.println("英语依存句法分析--->");
        String s2 = td.toString();
        System.out.println(s2);
        System.out.println(JSON.toJSONString(s2));
        List<String> collect = td.stream().map(Object::toString).collect(Collectors.toList());

        System.out.println(collect);
        for (TypedDependency t : td) {
            System.out.println(t.toString());
        }

        Set<Constituent> treeConstituents = tree.constituents(new LabeledScoredConstituentFactory());
        for (Constituent constituent : treeConstituents) {
            if (constituent.label() != null
                    && (constituent.label().toString().equals("VP") ||
                    constituent.label().toString().equals("NP"))) {
                System.err.println("found constituent: " + constituent.toString());
                System.err.println(tree.getLeaves().subList(constituent.start(), constituent.end() + 1));
            }
        }
    }
}
