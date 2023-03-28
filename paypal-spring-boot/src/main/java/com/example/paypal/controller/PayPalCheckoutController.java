package com.example.paypal.controller;

import com.example.paypal.service.PayPalCheckoutService;
import com.example.paypal.utils.RequestToMapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(description = "PayPalCheckout接口")
public class PayPalCheckoutController {

    @Autowired
    private PayPalCheckoutService payPalCheckoutService;

    @ApiOperation(value = "ipn异步回调")
    @PostMapping(value = "/paypal/ipn/back")
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        return payPalCheckoutService.callback(RequestToMapUtil.getParameterMap(request));
    }
}


