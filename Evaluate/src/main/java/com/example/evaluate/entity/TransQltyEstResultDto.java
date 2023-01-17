package com.example.evaluate.entity;

import lombok.Data;

import java.util.List;

@Data
public class TransQltyEstResultDto {

    private String reference;
    private Double fidelityScore = 0.0;
    private Double fluencyScore = 0.0;
    List<QualityEstimationDto> qualityEstimationDtoList;
}
