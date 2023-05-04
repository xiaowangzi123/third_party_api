package com.onem.service.impl;

import com.onem.service.ToolsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ToolsServiceImplTest {

    @Resource
    private ToolsService toolsService;

    @Test
    public void syntaxTag2() {
        String content = "";
        String s = toolsService.syntaxTag2(content);
        System.out.println(s);
    }
}