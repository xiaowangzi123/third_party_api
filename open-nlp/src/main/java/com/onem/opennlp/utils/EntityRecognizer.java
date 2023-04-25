
package com.onem.opennlp.utils;


/**
 * @author wyq
 * @date 2023/4/24
 * @desc
 */


import com.onem.opennlp.constants.ContentsConstants;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

public class EntityRecognizer {

    public static void main(String[] args) {

        String[] split = ContentsConstants.CONTENT03.split(" +");
        EntityRecognizer obj = new EntityRecognizer();
        System.out.println(Arrays.toString(obj.findEntities(split)));
    }

    /**
     * 人名识别不准确
     * @param tokens
     * @return
     */
    public String[] findEntities(String[] tokens){
        try {
//            InputStream modelIn = getClass().getResourceAsStream("/en-ner-person.bin");
            InputStream modelIn = Files.newInputStream(new File("D:\\github\\third_party_api\\open-nlp\\src\\main\\resources\\static\\en-ner-person.bin").toPath());
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);
            Span[] spans = nameFinder.find(tokens);
            String[] entities = Span.spansToStrings(spans, tokens);
            return entities;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}

