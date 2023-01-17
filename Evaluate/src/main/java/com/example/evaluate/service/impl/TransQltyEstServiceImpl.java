package com.example.evaluate.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.evaluate.entity.AnswerInfoDto;
import com.example.evaluate.entity.QualityEstimationDto;
import com.example.evaluate.entity.QuestionKey;
import com.example.evaluate.entity.TransQltyEstResultDto;
import com.example.evaluate.enums.MetricEnum;
import com.example.evaluate.service.TransQltyEstService;
import com.example.evaluate.utils.AMBER;
import com.example.evaluate.utils.GTM;
import com.example.evaluate.utils.TokenizeUtils;
import com.newtranx.eval.metrics.MetricUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class TransQltyEstServiceImpl implements TransQltyEstService {

//    @Value("${iqe.temp.wordnet}")
//    private String wordnet;

//    @Autowired
//    private QuestionKeyService questionKeyService;

    @Override
    public TransQltyEstResultDto eval(AnswerInfoDto dto) {

        // 翻译质量评估
        TransQltyEstResultDto transQltyEstResultDto = new TransQltyEstResultDto();

        // 信息忠实度（有参考译文）
        // 根据答案ID获得所有参考译文
//        List<QuestionKey> questionKeyList = questionKeyService.getByAnsID(dto.getAnsId());
        List<QuestionKey> questionKeyList = dto.getQuestionKeyList();
        log.info("参考译文列表（有标点）：{}", JSON.toJSONString(questionKeyList));

        String lang = "";

        switch (dto.getLangCode()) {
            case "en-US":
                lang = "en";
                break;
            case "zh-CN":
                lang = "zh";
                break;
        }

        // 初始化集合和对象
        List<String> referenceList = new ArrayList<>();
        questionKeyList.forEach(item -> referenceList.add(TokenizeUtils.removePunctuation(item.getContent())));
        log.info("参考译文列表（去标点）：{}", JSON.toJSONString(referenceList));
        List<QualityEstimationDto> qualityEstimationDtoList = new ArrayList<>();
        QualityEstimationDto qualityEstimationDto;

//        val wordnet = MetricUtil.buildWordnet(this.wordnet);
        val wordnet = MetricUtil.buildWordnet("C:\\dict");
        val bleu = MetricUtil.buildBleuMetric(lang);
        val ter = MetricUtil.buildTerMetric(true, true);
        val meteor = MetricUtil.buildMeteorMetric(wordnet, lang);
        val nist = MetricUtil.buildNistMetric(true);

        try {
            qualityEstimationDto = new QualityEstimationDto();
            qualityEstimationDto.setMetric(MetricEnum.BLEU.getValue());
            qualityEstimationDto.setScore(bleu.sentenceScore(TokenizeUtils.removePunctuation(dto.getHypothesis()), referenceList).getScore());
            qualityEstimationDtoList.add(qualityEstimationDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            qualityEstimationDto = new QualityEstimationDto();
            qualityEstimationDto.setMetric(MetricEnum.TER.getValue());
            qualityEstimationDto.setScore(ter.sentenceScore(TokenizeUtils.removePunctuation(dto.getHypothesis()), referenceList).getScore());
            qualityEstimationDtoList.add(qualityEstimationDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            qualityEstimationDto = new QualityEstimationDto();
            qualityEstimationDto.setMetric(MetricEnum.METEOR.getValue());
            qualityEstimationDto.setScore(meteor.sentenceScore(TokenizeUtils.removePunctuation(dto.getHypothesis()), referenceList).getScore());
            qualityEstimationDtoList.add(qualityEstimationDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            qualityEstimationDto = new QualityEstimationDto();
            qualityEstimationDto.setMetric(MetricEnum.NIST.getValue());
            qualityEstimationDto.setScore(nist.sentenceScore(TokenizeUtils.removePunctuation(dto.getHypothesis()), referenceList).getScore());
            qualityEstimationDtoList.add(qualityEstimationDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (QuestionKey questionKey : questionKeyList) {

            try {
                Double score = AMBER.eval(TokenizeUtils.removePunctuation(dto.getHypothesis()), TokenizeUtils.removePunctuation(questionKey.getContent()));
                if (isNumeric(score.toString())) {
                    qualityEstimationDto = new QualityEstimationDto();
                    qualityEstimationDto.setMetric(MetricEnum.AMBER.getValue());
                    qualityEstimationDto.setScore(score * 100);
                    qualityEstimationDto.setReference(questionKey.getContent());
                    qualityEstimationDtoList.add(qualityEstimationDto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Double score = GTM.eval(TokenizeUtils.removePunctuation(dto.getHypothesis()), TokenizeUtils.removePunctuation(questionKey.getContent()));
                if (isNumeric(score.toString())) {
                    qualityEstimationDto = new QualityEstimationDto();
                    qualityEstimationDto.setMetric(MetricEnum.GTM.getValue());
                    qualityEstimationDto.setScore(score * 100);
                    qualityEstimationDto.setReference(questionKey.getContent());
                    qualityEstimationDtoList.add(qualityEstimationDto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        transQltyEstResultDto.setQualityEstimationDtoList(qualityEstimationDtoList);

        // 学生提交的答案与不同参考答案和不同算法做笛卡尔积运算。
        Optional<QualityEstimationDto> optional = qualityEstimationDtoList.stream().max(Comparator.comparing(QualityEstimationDto::getScore));

        if (optional.isPresent()) {

            QualityEstimationDto optimum = optional.get();

            // 求得最高分数。
            transQltyEstResultDto.setFidelityScore(optimum.getScore());
            transQltyEstResultDto.setReference(optimum.getReference());

            if (!Objects.equals(optimum.getMetric(), MetricEnum.AMBER.getValue()) && !Objects.equals(optimum.getMetric(), MetricEnum.GTM.getValue())) {

                qualityEstimationDtoList = new ArrayList<>();
                for (String reference : referenceList) {
                    qualityEstimationDto = new QualityEstimationDto();
                    qualityEstimationDto.setReference(reference);
                    if (optimum.getMetric().equals(MetricEnum.BLEU.getValue())) {
                        qualityEstimationDto.setScore(bleu.singleSentenceScore(dto.getHypothesis(), reference).getScore());
                    } else if (optimum.getMetric().equals(MetricEnum.TER.getValue())) {
                        qualityEstimationDto.setScore(ter.singleSentenceScore(dto.getHypothesis(), reference).getScore());
                    } else if (optimum.getMetric().equals(MetricEnum.METEOR.getValue())) {
                        qualityEstimationDto.setScore(meteor.singleSentenceScore(dto.getHypothesis(), reference).getScore());
                    } else if (optimum.getMetric().equals(MetricEnum.NIST.getValue())) {
                        qualityEstimationDto.setScore(nist.singleSentenceScore(dto.getHypothesis(), reference).getScore());
                    }

                    qualityEstimationDtoList.add(qualityEstimationDto);
                }

                // 单个句子计算。
                optional = qualityEstimationDtoList.stream().max(Comparator.comparing(QualityEstimationDto::getScore));
                if (optional.isPresent()) {
                    optimum = optional.get();
                    // 求得最匹配的句子。
                    transQltyEstResultDto.setReference(optimum.getReference());
                }
            }
        }

        return transQltyEstResultDto;
    }

    /**
     * 判断字符串是不是double型
     *
     * @param str
     * @return
     */
    private Boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
