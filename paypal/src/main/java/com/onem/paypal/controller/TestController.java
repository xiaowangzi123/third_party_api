package com.onem.paypal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @PostMapping("/01")
    public String test01(){
        log.info("----------test-----");
        return "success";
    }
}
