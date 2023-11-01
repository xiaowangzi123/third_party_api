package com.onem.paypal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@Slf4j
@RestController
@RequestMapping(value = "/paypal")
public class PaypalController {

    @GetMapping("/payment/callback")
    public void callback(HttpServletRequest request){
        log.info("----------callback-----");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            log.info("name:value---->{}:{}",paramName,paramValue);
        }
    }
}
