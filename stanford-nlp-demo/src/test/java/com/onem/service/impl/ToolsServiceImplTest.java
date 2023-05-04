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
        String content = "Danger comes in many forms. For Turkey's long-time leader Recep Tayyip Erdogan, it comes in the shape of a former civil servant, given to making heart emojis with his hands." +
                "Kemal Kilicdaroglu, backed by a six-party opposition alliance, says if he wins he will bring freedom and democracy to Turkey, whatever it takes." +
                "The youth want democracy, he told the BBC. They don't want the police to come to their doors early in the morning just because they tweeted." +
                "He is the Islamist leader's main rival in elections on 14 May and has a narrow lead in opinion polls. This tight race is expected to go to a second round two weeks later." +
                "Currently Turks can go to jail for \"insulting the president.\" Many have." +
                "I am telling young people they can criticise me freely. I will make sure they have this right," +
                "says the 74 - year - old, who leads the main opposition Republican People 's Party (CHP)." +
                "Some of Mr Kilicdaroglu's supporters fear for his safety but he says it comes with the territory." +
                "\"Being in politics in Turkey means choosing a life with risks. I will walk my path whatever Erdogan and his allies do. They can't put me off. They can't scare me. I made a promise to this nation.\"" +
                "President Erdogan, 69, has mocked his rival in the past saying he \"couldn't even herd a sheep\". But he's harder to dismiss now. ";
        String s = toolsService.syntaxTag2(content);
        System.out.println(s);
    }
}