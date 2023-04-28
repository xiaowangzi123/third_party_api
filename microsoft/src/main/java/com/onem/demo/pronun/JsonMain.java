package com.onem.demo.pronun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyq
 * @date 2022/7/20
 * @desc
 */
public class JsonMain {
    public static void main(String[] args) {
        String strJson = "{\n" +
                "    \"Id\": \"e8d6942d5a67443695b35cb1593c541c\",\n" +
                "    \"RecognitionStatus\": \"Success\",\n" +
                "    \"Offset\": 12200000,\n" +
                "    \"Duration\": 192900000,\n" +
                "    \"DisplayText\": \"Today was a beautiful day. We had a great time taking long walk outside in the morning. The countryside was in full bloom, yet the air was crisp and cold towards the end of the day, clouds came in forecasting much needed rain.\",\n" +
                "    \"SNR\": 9.236484,\n" +
                "    \"NBest\": [\n" +
                "        {\n" +
                "            \"Confidence\": 0.94172144,\n" +
                "            \"Lexical\": \"today was a beautiful day we had a great time taking long walk outside in the morning the countryside was in full bloom yet the air was crisp and cold towards the end of the day clouds came in forecasting much needed rain\",\n" +
                "            \"ITN\": \"today was a beautiful day we had a great time taking long walk outside in the morning the countryside was in full bloom yet the air was crisp and cold towards the end of the day clouds came in forecasting much needed rain\",\n" +
                "            \"MaskedITN\": \"today was a beautiful day we had a great time taking long walk outside in the morning the countryside was in full bloom yet the air was crisp and cold towards the end of the day clouds came in forecasting much needed rain\",\n" +
                "            \"Display\": \"Today was a beautiful day. We had a great time taking long walk outside in the morning. The countryside was in full bloom, yet the air was crisp and cold towards the end of the day, clouds came in forecasting much needed rain.\",\n" +
                "            \"PronunciationAssessment\": {\n" +
                "                \"AccuracyScore\": 80.0,\n" +
                "                \"FluencyScore\": 81.0,\n" +
                "                \"CompletenessScore\": 91.0,\n" +
                "                \"PronScore\": 82.4\n" +
                "            },\n" +
                "            \"Words\": [\n" +
                "                {\n" +
                "                    \"Word\": \"air\",\n" +
                "                    \"Offset\": 115200000,\n" +
                "                    \"Duration\": 3100000,\n" +
                "                    \"PronunciationAssessment\": {\n" +
                "                        \"AccuracyScore\": 57.0,\n" +
                "                        \"ErrorType\": \"None\"\n" +
                "                    },\n" +
                "                    \"Syllables\": [\n" +
                "                        {\n" +
                "                            \"Syllable\": \"ehr\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 57.0\n" +
                "                            },\n" +
                "                            \"Offset\": 115200000,\n" +
                "                            \"Duration\": 3100000\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"Phonemes\": [\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"eh\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 48.0\n" +
                "                            },\n" +
                "                            \"Offset\": 115200000,\n" +
                "                            \"Duration\": 1500000\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"r\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 66.0\n" +
                "                            },\n" +
                "                            \"Offset\": 116800000,\n" +
                "                            \"Duration\": 1500000\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Word\": \"towards\",\n" +
                "                    \"Offset\": 142800000,\n" +
                "                    \"Duration\": 5100000,\n" +
                "                    \"PronunciationAssessment\": {\n" +
                "                        \"AccuracyScore\": 72.0,\n" +
                "                        \"ErrorType\": \"None\"\n" +
                "                    },\n" +
                "                    \"Syllables\": [\n" +
                "                        {\n" +
                "                            \"Syllable\": \"taordz\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 48.0\n" +
                "                            },\n" +
                "                            \"Offset\": 142800000,\n" +
                "                            \"Duration\": 5100000\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"Phonemes\": [\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"t\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 91.0\n" +
                "                            },\n" +
                "                            \"Offset\": 142800000,\n" +
                "                            \"Duration\": 1000000\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"ao\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 3.0\n" +
                "                            },\n" +
                "                            \"Offset\": 143900000,\n" +
                "                            \"Duration\": 1000000\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"r\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 42.0\n" +
                "                            },\n" +
                "                            \"Offset\": 145000000,\n" +
                "                            \"Duration\": 900000\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"d\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 4.0\n" +
                "                            },\n" +
                "                            \"Offset\": 146000000,\n" +
                "                            \"Duration\": 900000\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"z\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 98.0\n" +
                "                            },\n" +
                "                            \"Offset\": 147000000,\n" +
                "                            \"Duration\": 900000\n" +
                "                        }\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"Word\": \"in\",\n" +
                "                    \"Offset\": 166600000,\n" +
                "                    \"Duration\": 3900000,\n" +
                "                    \"PronunciationAssessment\": {\n" +
                "                        \"AccuracyScore\": 58.0,\n" +
                "                        \"ErrorType\": \"None\"\n" +
                "                    },\n" +
                "                    \"Syllables\": [\n" +
                "                        {\n" +
                "                            \"Syllable\": \"ihn\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 58.0\n" +
                "                            },\n" +
                "                            \"Offset\": 166600000,\n" +
                "                            \"Duration\": 3900000\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"Phonemes\": [\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"ih\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 49.0\n" +
                "                            },\n" +
                "                            \"Offset\": 166600000,\n" +
                "                            \"Duration\": 2100000\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"Phoneme\": \"n\",\n" +
                "                            \"PronunciationAssessment\": {\n" +
                "                                \"AccuracyScore\": 69.0\n" +
                "                            },\n" +
                "                            \"Offset\": 168800000,\n" +
                "                            \"Duration\": 1700000\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        List<WordInfo> wordInfoList = new ArrayList<>();
        JSONObject resJson = JSON.parseObject(strJson);
        JSONArray nBest = (JSONArray) resJson.get("NBest");
        System.out.println("JSONResult-- NBest-->:" + nBest.get(0));
//        JSONObject n0 = (JSONObject) nBest.get(0);
//        JSONArray words = (JSONArray) n0.get("Words");
        JSONArray words= (JSONArray) ((JSONObject) nBest.get(0)).get("Words");

        System.out.println("words-->" + words);

        words.forEach(w -> {
            WordInfo wordInfo = new WordInfo();
            wordInfo.setWord((String) ((JSONObject) w).get("Word"));
            JSONObject pronunAssess=  ((JSONObject) w).getJSONObject("PronunciationAssessment");
            wordInfo.setScore(Double.parseDouble(String.valueOf(pronunAssess.get("AccuracyScore"))));
            wordInfo.setErrorType(String.valueOf(pronunAssess.get("ErrorType")));

            wordInfoList.add(wordInfo);
        });

        System.out.println(wordInfoList);

    }
}
