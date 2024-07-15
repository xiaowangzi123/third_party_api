package com.example.huiyan.huiyan.utils.ffmpeg;

import lombok.Data;

/**
 * 音频片段属性
 */

@Data
public class AudioSegDto {

    private String audioPath;
    private double pointTime;
    private Integer duration;
    private Integer totalSegs;
    private Integer currentSeg;
}
