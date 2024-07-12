package com.example.huiyan.huiyan.service.impl;

import com.example.huiyan.huiyan.entity.table.SrcLangSeg;
import com.example.huiyan.huiyan.service.CutWavService;
import com.example.huiyan.huiyan.service.FfmpegService;
import com.example.huiyan.huiyan.service.SrcLangSegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public void cutWav(String jobId, String srcWavPath, SrcLangSeg seg) {
        String tgtPath = "D:\\ru_compare\\" + jobId + System.lineSeparator() + seg.getId() + ".wav";
        int i = ffmpegService.cutAudio(srcWavPath, tgtPath, seg.getStartTimecode() - 10, seg.getStartTimecode() + 10);
        log.info("音频文件切分：{}，切分结果：{}", seg, i);
    }
}
