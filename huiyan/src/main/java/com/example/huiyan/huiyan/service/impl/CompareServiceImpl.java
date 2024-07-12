package com.example.huiyan.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.huiyan.huiyan.entity.table.SrcLangSeg;
import com.example.huiyan.huiyan.entity.table.TextCompare;
import com.example.huiyan.huiyan.service.CompareService;
import com.example.huiyan.huiyan.service.SrcLangSegService;
import com.example.huiyan.huiyan.service.TextCompareService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyq
 * @date 2024/7/12
 * @desc
 */

@Service
public class CompareServiceImpl implements CompareService {
    @Resource
    private SrcLangSegService srcLangSegService;
    @Resource
    private TextCompareService textCompareService;

    @Override
    public String selectSegSave(String jobId) {
        List<SrcLangSeg> segList = srcLangSegService.list(new LambdaQueryWrapper<SrcLangSeg>()
                .eq(SrcLangSeg::getJobId, jobId).orderByAsc(SrcLangSeg::getStartTimecode));
        if (CollectionUtils.isEmpty(segList)) {
            return "没有查询到句段";
        }
        List<TextCompare>  textCompareList = new ArrayList<>();
        for (SrcLangSeg seg : segList) {
            TextCompare textCompare = new TextCompare();
            textCompare.setId(seg.getId());
            textCompare.setJobId(jobId);
            textCompare.setStartTimecode(seg.getStartTimecode());
            textCompare.setEndTimecode(seg.getEndTimecode());
            textCompare.setMicroSrcText(seg.getTranscribedSrcText());
            textCompareList.add(textCompare);
        }
        textCompareService.saveOrUpdateBatch(textCompareList);
        return "操作成功";
    }
}
