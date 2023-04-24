package com.onem.opennlp.utils;

/**
 * @author wyq
 * @date 2023/4/24
 * @desc
 */

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import java.io.InputStream;

public class EntityRecognizer {

    public String[] findEntities(String[] tokens) throws Exception {
        InputStream modelIn = getClass().getResourceAsStream("/en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
        NameFinderME nameFinder = new NameFinderME(model);
        Span[] spans = nameFinder.find(tokens);
        String[] entities = Span.spansToStrings(spans, tokens);
        return entities;
    }

}
