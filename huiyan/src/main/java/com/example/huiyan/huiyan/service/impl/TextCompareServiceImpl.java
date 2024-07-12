package com.example.huiyan.huiyan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.huiyan.huiyan.entity.table.TextCompare;
import com.example.huiyan.huiyan.mapper.TextCompareMapper;
import com.example.huiyan.huiyan.service.HuiyanTranscriptionService;
import com.example.huiyan.huiyan.service.TextCompareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class TextCompareServiceImpl extends ServiceImpl<TextCompareMapper, TextCompare> implements TextCompareService {

    @Resource
    private HuiyanTranscriptionService huiyanTranscriptionService;

    @Async(value = "huiyanasr")
    @Override
    public void saveAsrResult(String wavPath, TextCompare textCompare) {
        log.info("异步语音识别：{}", wavPath);
        String asrText = huiyanTranscriptionService.asr(wavPath, "ru-RU", textCompare.getId());
        textCompare.setHuiyanSrcText(asrText);
        textCompare.setSame(Objects.equals(textCompare.getMicroSrcText(), asrText));
        log.info("保存数据：{}", textCompare);
        boolean flag = this.saveOrUpdate(textCompare);
        log.info("保存数据：{}，结果：{}", textCompare.getId(), flag);
    }
}