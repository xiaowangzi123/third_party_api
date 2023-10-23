package com.example.paypal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.paypal.*"})
public class PaypalApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaypalApplication.class, args);
    }

}
