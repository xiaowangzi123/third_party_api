package com.onem.entity;

import lombok.Data;

import java.util.List;



@Data
public class SyntaxResultDto {

    /**
     * 成分句法分析结果
     */
    private List<String> constituency;
    /**
     * 依存句法分析
     */
    private List<List<String>> dependencyList;
}
