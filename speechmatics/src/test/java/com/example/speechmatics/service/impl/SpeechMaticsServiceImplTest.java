package com.example.speechmatics.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.speechmatics.service.SpeechMaticsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
public class SpeechMaticsServiceImplTest {

    @Resource
    private SpeechMaticsService speechMaticsService;

    @Test
    public void test01() {
//        String filePath = "D:\\TestAudioVideo\\20Eng.wav";
        String filePath = "D:\\TestAudioVideo\\7_49En.mp4";
        String langCode = "en";
        String newJob = speechMaticsService.createNewJob(filePath, langCode);
        System.out.println("taskId--->" + newJob);
        if (StrUtil.isNotBlank(newJob)){
            speechMaticsService.jobProgress(newJob);
        }

    }

    @Test
    public void listAllJobs() {
        List<String> list = speechMaticsService.jobIdList();
        System.out.println(list);
    }

    @Test
    public void getTest() {
        speechMaticsService.getSubtitles("808mq47t8f");
    }

}