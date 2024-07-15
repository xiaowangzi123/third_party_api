package com.example.huiyan.huiyan.entity;

import lombok.Data;

/**
 * @author wyq
 * @date 2022/4/2
 * @desc
 */

@Data
public class FfmpegRes {
    private int code;
    private String srcFilePath;
    private String tgtFilePath;
    private String jobId;
}
