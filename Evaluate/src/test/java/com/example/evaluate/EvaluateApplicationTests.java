package com.example.evaluate;

import com.alibaba.fastjson.JSON;
import com.example.evaluate.entity.AnswerInfoDto;
import com.example.evaluate.entity.QuestionKey;
import com.example.evaluate.entity.TransQltyEstResultDto;
import com.example.evaluate.service.TransQltyEstService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EvaluateApplicationTests {

    @Resource
    private TransQltyEstService queryQltyEstService;

    @Test
    public void test(){
        AnswerInfoDto dto = new AnswerInfoDto();
        dto.setHypothesis("我国发展外部环境发生明显变化，经济已由高速增长阶段转向高质量发展阶段，发展不平衡不充分问题。");
        dto.setLangCode("en-US");

        List<QuestionKey> questionKeyList = new ArrayList<>();
        QuestionKey questionKey = new QuestionKey();
        questionKey.setContent("Obvious changes have taken place in our external environment for development. Our economy has shifted from a phase of rapid growth to one of high quality, and development is unbalanced and inadequate." +
                "The external environment of China's development has changed obviously, the economy has changed from the stage of rapid growth to the stage of high-quality development, and the problem of unbalanced and inadequate development.");
        questionKeyList.add(questionKey);

        dto.setQuestionKeyList(questionKeyList);
        TransQltyEstResultDto eval = queryQltyEstService.eval(dto);
        System.out.println("评估结果："+ JSON.toJSONString(eval));
    }

}
