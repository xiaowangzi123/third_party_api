package com.example.evaluate;

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
        dto.setHypothesis("今年是全面贯彻党的十九大精神的开局之年，也是我国发展进程中极不平凡的一年。我国发展外部环境发生明显变化，经济已由高速增长阶段转向高质量发展阶段，发展不平衡不充分问题和各种周期性、结构性、体制性因素交织叠加在一起。面对错综复杂的国际环境，面对艰巨繁重的国内改革发展稳定任务，我们之所以能保持经济持续健康发展和社会大局稳定，迈出全面建成小康社会新步伐，关键就在于以习近平同志为核心的党中央坚强领导，关键就在于我们在实践中深化了对做好新形势下经济工作的规律性认识。");
        dto.setLangCode("en-US");

        List<QuestionKey> questionKeyList = new ArrayList<>();
        QuestionKey questionKey = new QuestionKey();
        questionKey.setContent("This year is the first year to fully implement the spirit of the 19th CPC National Congress, and it is also an extraordinary year in China's development process. The external environment for China's development has changed significantly. The economy has shifted from a stage of rapid growth to a stage of high-quality development. The problem of unbalanced and inadequate development is intertwined with various cyclical, structural and institutional factors.");
        questionKeyList.add(questionKey);

        dto.setQuestionKeyList(questionKeyList);
        TransQltyEstResultDto eval = queryQltyEstService.eval(dto);
        System.out.println("评估结果："+eval);
    }

}
