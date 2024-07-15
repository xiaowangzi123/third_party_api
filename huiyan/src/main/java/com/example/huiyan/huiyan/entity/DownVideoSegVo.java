package com.example.huiyan.huiyan.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wyq
 * @date 2022/4/6
 * @desc
 */

@Data
public class DownVideoSegVo {

    @NotNull
    private Integer startTimecode;
    @NotNull
    private Integer endTimecode;
    @NotBlank
    private String jobId;
    private String path;
    private String srcVideoPath;
}
