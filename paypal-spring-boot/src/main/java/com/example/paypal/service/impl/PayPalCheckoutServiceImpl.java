package com.example.paypal.service.impl;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.SerializeException;
import com.paypal.http.serializer.Json;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Capture;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.payments.Refund;
import com.paypal.payments.RefundRequest;
import com.paypal.payments.RefundsGetRequest;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.orders.Payer;
import com.paypal.orders.PurchaseUnit;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetail;
import com.paypal.payments.CapturesGetRequest;
import com.paypal.payments.CapturesRefundRequest;
import com.ratta.constants.PayPalCheckoutConstant;
import com.ratta.pay.info.PayPalClient;
import com.ratta.service.PayPalCheckoutService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RefreshScope
public class PayPalCheckoutServiceImpl implements PayPalCheckoutService {

    @Value("${paypal.receiver.email}")
    private String receiverEmail;



    @Override
    public String callback(@SuppressWarnings("rawtypes") Map map) {
        log.info(map.toString());
        String outTradeNo = (String)map.get("invoice");
        String paymentStatus = (String)map.get("payment_status");
        String amount = (String)map.get("mc_gross");
        String currency = (String)map.get("mc_currency");
        String paymentId = (String)map.get("txn_id");
        String parentPaymentId = (String)map.get("parent_txn_id");
        log.info("商家订单号 = {}", outTradeNo);
        log.info("订单状态 = {}", paymentStatus);
        log.info("金额 = {}", amount);
        log.info("币种 = {}", currency);
        log.info("流水号 = {}", paymentId);
        log.info("父流水号 = {}", parentPaymentId);

        if (!receiverEmail.equals((String) map.get("receiver_email"))) {
            log.info("FAIL = 商户id错误, outTradeNo = {}", outTradeNo);
            return "failure";
        }
        if("Completed".equals(paymentStatus)) {
            //进行数据库操作
            //
            //
            log.info("支付成功,状态为=COMPLETED");
            return "success";
        }
        if("Refunded".equals(paymentStatus)) {
            //进行数据库操作
            //
            //
            log.info("退款成功");
            return "success";
        }
        if("Pending".equals(paymentStatus) && StringUtils.isEmpty(parentPaymentId)) {
            String pendingReason = String.valueOf(map.get("pending_reason"));
            //进行数据库操作
            //
            //
            log.info("订单支付成功,状态为=PENDING，产生此状态的原因是 {}", pendingReason );
            return "success";
        }
        if(StringUtils.isEmpty(parentPaymentId)) {
            if(PayPalCheckoutConstant.PAYMENT_STATUS_REVERSED.equals(paymentStatus)
                    || PayPalCheckoutConstant.PAYMENT_STATUS_CANCELED_REVERSAL.equals(paymentStatus)
                    || PayPalCheckoutConstant.PAYMENT_STATUS_DENIED.equals(paymentStatus)) {
                String reasonCode = String.valueOf(map.get("reason_code"));
                //进行数据库操作(状态修改)
                //
                //
                log.info("订单异常,请尽快查看处理，状态为={}，产生此状态的原因是 {} ", paymentStatus, reasonCode);
                return PayPalCheckoutConstant.SUCCESS;
            }
            if(PayPalCheckoutConstant.PAYMENT_STATUS_EXPIRED.equals(paymentStatus)
                    || PayPalCheckoutConstant.PAYMENT_STATUS_CREATED.equals(paymentStatus)
                    || PayPalCheckoutConstant.PAYMENT_STATUS_FAILED.equals(paymentStatus)
                    || PayPalCheckoutConstant.PAYMENT_STATUS_PROCESSED.equals(paymentStatus)
                    || PayPalCheckoutConstant.PAYMENT_STATUS_VOIDED.equals(paymentStatus)) {
                //进行数据库操作(状态修改)
                //
                //
                log.info("其他订单状态,订单异常,请尽快查看处理， 状态={}", paymentStatus);
                return PayPalCheckoutConstant.SUCCESS;
            }
        }
        return "failure";
    }
}


