package com.example.huiyan.huiyan.service.impl;

import com.example.huiyan.huiyan.entity.HuiyanAsrResult;
import com.example.huiyan.huiyan.entity.SpeechSubtitle;
import com.example.huiyan.huiyan.entity.table.SrcLangSeg;
import com.example.huiyan.huiyan.service.HuiyanAsrService;
import com.example.huiyan.huiyan.service.HuiyanTranscriptionService;
import com.example.huiyan.huiyan.utils.SystemUtils;
import com.example.huiyan.huiyan.utils.VideoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 慧言语音识别
 */
@Service
@Slf4j
public class HuiyanTranscriptionServiceImpl implements HuiyanTranscriptionService {
    @Resource
    private HuiyanAsrService huiyanAsrService;


    @Override
    public List<SrcLangSeg> transcribe(String audioFilePath, String lang, String name) {
        log.info("[{}]-慧言转写请求参数 lang：{}", name, lang);
        List<SrcLangSeg> srcSegList = new ArrayList<>();
        try {
            long videoDuration = VideoUtils.getVideoDuration(audioFilePath);
            log.info("待转写视频时长：{}秒", videoDuration / 1000);

            log.info("[{}]-慧言音频转写开始", name);
            String taskId = huiyanAsrService.createTask(audioFilePath, lang);
            log.info("创建慧言语音转写任务ID：{}", taskId);
            if (taskId == null) {
                return srcSegList;
            }
            HuiyanAsrResult asrResult = huiyanAsrService.getSubtitles(taskId);
            log.info("[{}]-慧言转写结果：{}", name, asrResult);
            String status = asrResult.getStatus();

            while (!Objects.equals("00000", status)) {
                SystemUtils.sleep(2000);
                asrResult = huiyanAsrService.getSubtitles(taskId);
                log.info("[{}]-慧言转写进度结果：{}", name, asrResult);
                status = asrResult.getStatus();

                Integer progress = asrResult.getProgress();
                if (progress == 0) {
                    log.info("【{}】慧言语音转写失败", name);
                    break;
                }
                if (progress > 0) {
                    int completeness = BigDecimal.valueOf((float) progress / 100).multiply(new BigDecimal(30)).setScale(0, RoundingMode.HALF_UP).intValue();
                    log.info("[{}]-慧言字幕转写进度：{}", name, completeness);

                }
            }
            log.info("[{}]-慧言字幕转写结束", name);

            //加入句段号
            dataConvert(asrResult.getSrtList(), srcSegList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srcSegList;
    }

    public void dataConvert(List<SpeechSubtitle> subtitles, List<SrcLangSeg> srcSegList) {
        subtitles.forEach(seg -> {
            SrcLangSeg srcSeg = new SrcLangSeg();
            srcSeg.setId(UUID.randomUUID().toString());
            srcSeg.setSequence(seg.getSequence());
            srcSeg.setStartTimecode(seg.getStartTime());
            srcSeg.setEndTimecode(seg.getEndTime());
            srcSeg.setTranscribedSrcText(seg.getText());
            srcSeg.setRevisedSrcText(seg.getText());
            srcSegList.add(srcSeg);
        });

    }


    public static void main(String[] args) {
        int i = BigDecimal.valueOf((float) 60 / 100).multiply(new BigDecimal(30)).setScale(0, RoundingMode.HALF_UP).intValue();
        System.out.println(i);
    }
}
