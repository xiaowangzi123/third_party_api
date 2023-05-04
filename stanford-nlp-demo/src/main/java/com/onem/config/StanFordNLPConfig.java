package com.onem.config;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
public class StanFordNLPConfig {

   /* @Bean
    public LexicalizedParser getLexicalizedParser(){
       return LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
    }*/

    @Bean
    public StanfordCoreNLP getStanfordCoreNLP(){
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
        return new StanfordCoreNLP(props);
    }
}
