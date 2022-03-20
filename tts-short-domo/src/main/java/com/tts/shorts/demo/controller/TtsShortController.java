package com.tts.shorts.demo.controller;

import com.tts.shorts.demo.service.TtsWebsocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * @author :wyq
 * @date ：Created in 2022/3/17
 * @description :
 */
@RestController
@RequestMapping("/tts/short")
public class TtsShortController {

    @Autowired
    private TtsWebsocketService ttsWebsocketService;

    @GetMapping("/001")
    public void test001() throws Exception {
        ttsWebsocketService.ttsWebsocket();
    }

}