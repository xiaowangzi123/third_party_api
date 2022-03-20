package com.tts.shorts.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :wyq
 * @date ：Created in 2022/3/17
 * @description :
 */
@RestController
@RequestMapping("/tts/short")
public class TtsShortController {

    @PostMapping("/001")
    public void test001(){

    }

}