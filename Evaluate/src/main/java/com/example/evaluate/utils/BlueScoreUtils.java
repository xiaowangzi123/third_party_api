package com.example.evaluate.utils;

import com.alibaba.fastjson.JSON;
import com.newtranx.eval.metrics.IEvaluate;
import com.newtranx.eval.metrics.MetricUtil;
import com.newtranx.eval.metrics.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author wyq
 * @date 2024/4/3
 * @desc
 */
public class BlueScoreUtils {

    public static void main(String[] args) {

//        String lang = "zh";
        String lang = "en";
        String trans = "Going to play basketball in the afternoon ?";
        List<String> referenceList = new ArrayList<>(Arrays.asList("Going to play basketball this afternoon ?"
                , "Going to play basketball afternoon ?"));

        getBlueScore(lang, trans, referenceList);
        getBaseScore(lang,trans,referenceList);
    }

    public static void getBlueScore(String langCode, String trans, List<String> referenceList) {
        IEvaluate evaluate = MetricUtil.buildBleuMetric(langCode);
        Score score = evaluate.sentenceScore(trans, referenceList);
        System.out.println(score);
    }

    public static void getBaseScore(String langCode, String trans, List<String> referenceList) {
//        IEvaluate evaluate = MetricUtil.buildNistMetric();
        IEvaluate evaluate = MetricUtil.buildNistMetric(true);
        Score score = evaluate.sentenceScore(trans, referenceList);
        System.out.println(JSON.toJSONString(score,true));
    }
}
