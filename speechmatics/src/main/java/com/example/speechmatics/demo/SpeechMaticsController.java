package com.example.speechmatics.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyq
 * @date 2023/10/17
 * @desc
 */

@RestController
@RequestMapping(value = "/v1")
public class SpeechMaticsController {

    @GetMapping("/test/01")
    public String getTest(){
        return "SpeechMatics";
    }
}
