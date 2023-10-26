package com.example.speechmatics.entity;

import lombok.Data;

/**
 * @author wyq
 * @date 2023/10/19
 * @desc
 */

@Data
public class Subtitle {
    private Integer startTime;
    private Integer endTime;
    private Integer sequence;
    private String text;
}
