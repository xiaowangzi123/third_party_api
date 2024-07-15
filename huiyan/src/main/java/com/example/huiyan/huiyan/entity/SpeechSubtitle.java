package com.example.huiyan.huiyan.entity;

import lombok.Data;

/**
 * speechmastic语音识别返回
 */

@Data
public class SpeechSubtitle {
    private Integer startTime;
    private Integer endTime;
    private Integer sequence;
    private String text;
}
