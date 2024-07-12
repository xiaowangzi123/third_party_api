package com.example.huiyan.huiyan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.huiyan.huiyan.entity.table.SrcLangSeg;
import com.example.huiyan.huiyan.service.CutWavService;
import com.example.huiyan.huiyan.service.FfmpegService;
import com.example.huiyan.huiyan.service.SrcLangSegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 音频切割
 */

@Slf4j
@Service
public class CutWavServiceImpl implements CutWavService {
    @Resource
    private SrcLangSegService srcLangSegService;
    @Resource
    private FfmpegService ffmpegService;

    @Async("production")
    @Override
    public void cutWav(String jobId) {
        List<SrcLangSeg> segList = srcLangSegService.list(new LambdaQueryWrapper<SrcLangSeg>()
                .eq(SrcLangSeg::getJobId, jobId).orderByAsc(SrcLangSeg::getStartTimecode));
        if (CollectionUtils.isEmpty(segList)) {
            return;
        }
        String srcWavPath = "D:\\ru_compare\\" + jobId + ".wav";
        if (!new File(srcWavPath).exists()) {
            log.info("源文件不存在:{}", srcWavPath);
        }
        for (SrcLangSeg seg : segList) {
            String tgtPath = "D:\\ru_compare\\" + jobId + System.lineSeparator() + seg.getId() + ".wav";
            int i = ffmpegService.cutAudio(srcWavPath, tgtPath, seg.getStartTimecode(), seg.getStartTimecode());
            log.info("句子：{}，切分结果：{}", seg, i);
        }
    }
}
