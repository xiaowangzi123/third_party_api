package com.example.huiyan.huiyan.service.impl;

import com.example.huiyan.huiyan.entity.table.TextCompare;
import com.example.huiyan.huiyan.service.CutWavService;
import com.example.huiyan.huiyan.service.FfmpegService;
import com.example.huiyan.huiyan.service.SrcLangSegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

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
    public void cutWav(String jobId, String srcWavPath, TextCompare seg) {
        String tgtPath = "D:\\ru_compare\\" + jobId + File.separator+ seg.getId() + ".wav";
        File file = new File(tgtPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        int i = ffmpegService.cutAudio(srcWavPath, tgtPath, seg.getStartTimecode() - 10, seg.getEndTimecode() + 10);
        log.info("音频文件切分：{}，切分结果：{}", seg, i);
    }
}
