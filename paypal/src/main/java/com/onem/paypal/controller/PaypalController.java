package com.onem.paypal.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/paypal")
public class PaypalController {

    @PostMapping("/payment/callback")
    public String callback(@RequestBody JSONObject requestParams){
        log.info("----------callback-----");
        return requestParams.toJSONString();
    }
}
