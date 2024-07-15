package com.example.huiyan.huiyan;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.huiyan.huiyan.entity.table.TextCompare;
import com.example.huiyan.huiyan.service.TextCompareService;
import com.example.huiyan.huiyan.utils.StringTools;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class HuiyanApplicationTests {

    @Resource
    private TextCompareService textCompareService;

    @Test
    public void formatStr() {
        List<TextCompare> list = textCompareService.list(new LambdaQueryWrapper<TextCompare>()
                .isNotNull(TextCompare::getMicroSrcText));
        for (TextCompare textCompare : list){
            textCompare.setMicroSrcText(StringTools.formatSrt(textCompare.getMicroSrcText()));
            textCompareService.updateById(textCompare);
        }
    }

    @Test
    public void contextLoads() {
        List<TextCompare> list = textCompareService.list(new LambdaQueryWrapper<TextCompare>()
                .isNotNull(TextCompare::getHuiyanSrcText).orderByAsc(TextCompare::getStartTimecode));
        for (TextCompare textCompare : list){
            textCompare.setSame(StringTools.isSame(textCompare.getMicroSrcText(),textCompare.getHuiyanSrcText()));
            textCompareService.updateById(textCompare);
        }
    }

}
