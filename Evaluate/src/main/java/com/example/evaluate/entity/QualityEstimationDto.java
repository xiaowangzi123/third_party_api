package com.example.evaluate.entity;

import lombok.Data;

@Data
public class QualityEstimationDto {

    /**
     * 参考答案
     */
    private String reference;

    /**
     * 算法
     */
    private String metric;

    /**
     * 分数
     */
    private Double score;
}
