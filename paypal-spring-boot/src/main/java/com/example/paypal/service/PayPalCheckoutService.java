package com.example.paypal.service;

import java.util.Map;

public interface PayPalCheckoutService {
    /**
     * 回调
     * @param map
     */
    String callback(@SuppressWarnings("rawtypes") Map map);
}
