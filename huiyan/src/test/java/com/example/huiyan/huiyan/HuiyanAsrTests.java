package com.example.huiyan.huiyan;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.huiyan.huiyan.entity.table.TextCompare;
import com.example.huiyan.huiyan.service.TextCompareService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class HuiyanAsrTests {

    @Resource
    private TextCompareService textCompareService;

    @Test
    public void formatStr() {
        List<TextCompare> list = textCompareService.list(new LambdaQueryWrapper<TextCompare>()
                .isNotNull(TextCompare::getMicroSrcText));
        for (TextCompare compare : list){
            if (StringUtils.isNotBlank(compare.getMicroSrcText()) && StringUtils.isNotBlank(compare.getHuiyanSrcText())) {
                continue;
            }
            String wavPath = "D:\\ru_compare\\" + compare.getJobId() + "\\" + compare.getId() + ".wav";
            textCompareService.saveAsrResult(wavPath, compare);
        }
    }



}
