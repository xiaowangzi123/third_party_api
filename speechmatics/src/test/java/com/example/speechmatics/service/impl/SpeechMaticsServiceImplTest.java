package com.example.speechmatics.service.impl;

import com.example.speechmatics.service.SpeechMaticsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@Slf4j
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
public class SpeechMaticsServiceImplTest {

    @Resource
    private SpeechMaticsService speechMaticsService;

    @Test
    public void test01() {
        String filePath = "D:\\TestAudioVideo\\20Eng.wav";
        String langCode = "en";
        speechMaticsService.createNewJob(filePath, langCode);
    }
}