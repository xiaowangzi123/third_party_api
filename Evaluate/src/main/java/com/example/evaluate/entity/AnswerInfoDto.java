package com.example.evaluate.entity;

import lombok.Data;

/**
 * @author wyq
 * @date 2022/7/29
 * @desc
 */
@Data
public class AnswerInfoDto {
    private String stuId;
    private String ansId;
    private String fileId;
    private String audioObsPath;
    private String examId;
    private String langCode;
    /**
     * 音视频识别出的文字
     */
    private String hypothesis;
    private String reference;
}
