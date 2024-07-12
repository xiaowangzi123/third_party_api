package com.example.huiyan.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.huiyan.huiyan.entity.table.SrcLangSeg;
import com.example.huiyan.huiyan.service.CutWavService;
import com.example.huiyan.huiyan.service.FfmpegService;
import com.example.huiyan.huiyan.service.FilePathService;
import com.example.huiyan.huiyan.service.SrcLangSegService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wyq
 * @date 2024/7/12
 * @desc
 */

@Service
public class CutWavServiceImpl implements CutWavService {
    @Resource
    private SrcLangSegService srcLangSegService;
    @Resource
    private FfmpegService ffmpegService;
    @Resource
    private FilePathService filePathService;

    @Override
    public void cutWav(String jobId) {
        List<SrcLangSeg> segList = srcLangSegService.list(new LambdaQueryWrapper<SrcLangSeg>()
                .eq(SrcLangSeg::getJobId, jobId).orderByAsc(SrcLangSeg::getStartTimecode));
        if (CollectionUtils.isEmpty(segList)) {
            return;
        }
        String src_wav_path = "D:\\ru_compare\\" + jobId + System.lineSeparator() + ".wav";
        for (SrcLangSeg seg : segList) {

            srcLangSegService.updateById(seg);
        }
    }
}
