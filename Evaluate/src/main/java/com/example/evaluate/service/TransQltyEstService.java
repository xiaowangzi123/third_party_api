package com.example.evaluate.service;


import com.example.evaluate.entity.AnswerInfoDto;
import com.example.evaluate.entity.TransQltyEstResultDto;

/**
 * 评估指标服务
 */
public interface TransQltyEstService {
    /**
     * 信息忠实度
     */
    TransQltyEstResultDto eval(AnswerInfoDto dto);
}
