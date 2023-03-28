package com.example.paypal.service;

public interface PayPalCheckoutService {
    /**
     * 回调
     * @param map
     */
    String callback(@SuppressWarnings("rawtypes") Map map);
}
