package com.example.evaluate.enums;

import lombok.Getter;

@Getter
public enum MetricEnum {

    BLEU(0, "BLEU"),
    TER(1, "TER"),
    METEOR(2, "METEOR"),
    NIST(0, "NIST"),
    AMBER(1, "AMBER"),
    GTM(2, "GTM");

    private int key;
    private String value;

    MetricEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
