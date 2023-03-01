package com.microsoft.demo.pronun;

import lombok.Data;

/**
 * @author wyq
 * @date 2022/7/20
 * @desc
 */
@Data
public class WordInfo {
    private String word;
    private double score;
    private String errorType;
}
