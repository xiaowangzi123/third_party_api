package com.example.huiyan.huiyan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.huiyan.huiyan.entity.table.SrcLangSeg;
import com.example.huiyan.huiyan.mapper.SrcLangSegMapper;
import com.example.huiyan.huiyan.service.SrcLangSegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SrcLangSegServiceImpl extends ServiceImpl<SrcLangSegMapper, SrcLangSeg> implements SrcLangSegService {


    @Async(value = "huiyanasr")
    @Override
    public void asyncTest() {
        log.info("开始:{}", Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("结束:{}", Thread.currentThread().getName());
    }
}