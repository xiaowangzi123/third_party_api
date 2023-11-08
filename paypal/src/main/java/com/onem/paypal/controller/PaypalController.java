package com.onem.paypal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@Slf4j
@RestController
@RequestMapping(value = "/paypal")
public class PaypalController {


    @GetMapping("/cancel/callback")
    public void cancelCallback(HttpServletRequest request) {
        log.info("----------cancel callback-----");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            log.info("name:value---->{}:{}", paramName, paramValue);
        }
    }

    @GetMapping("/payment/callback")
    public void callback(HttpServletRequest request, @RequestParam("token") String token, @RequestParam("PayerID") String payerId) {
        log.info("----------success callback-----");
        log.info("{},{}", token, payerId);
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            log.info("name:value---->{}:{}", paramName, paramValue);
        }
    }
}
