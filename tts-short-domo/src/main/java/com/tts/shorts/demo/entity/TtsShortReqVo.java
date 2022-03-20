package com.tts.shorts.demo.entity;

import lombok.Data;

/**
 * @author :wyq
 * @date ：Created in 2022/3/20
 * @description :
 */
@Data
public class TtsShortReqVo {

    /**
     * 音频格式: pcm,mp3
     */
    private String format;

    /**
     * 采样率（单位Hz）
     */
    private String sample;

    /**
     * 发音人
     */
    private String vcn;

    /**
     * 语速
     */
    private Integer speed = 50;

    /**
     * 音量
     */
    private Integer volume = 50;

    /**
     * 音高
     */
    private Integer pitch=50;

    /**
     * 亮度
     */
    private Integer bright=50;

    /**
     * 需要合成的文本
     */
    private String text;

    /**
     * 用户标识
     */
    private String user_id;
}