package com.example.huiyan.huiyan.entity.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 源语言句段
 *
 * @author ${author}
 * @date 2024-07-11 16:27:15
 */
@Data
@TableName("src_lang_seg")
public class SrcLangSeg implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @TableField(value = "id")
    private String id;
    /**
     * 作业ID
     */
    @TableField(value = "job_id")
    private String jobId;
    /**
     * 语种ID
     */
    @TableField(value = "lang_id")
    private String langId;
    /**
     * 句段号
     */
    @TableField(value = "sequence")
    private Integer sequence;
    /**
     * 下一个句段
     */
    @TableField(value = "next_seg")
    private String nextSeg;
    /**
     * 开始时间码
     */
    @TableField(value = "start_timecode")
    private Integer startTimecode;
    /**
     * 结束时间码
     */
    @TableField(value = "end_timecode")
    private Integer endTimecode;
    /**
     *
     */
    @TableField(value = "dub_type")
    private Integer dubType;
    /**
     *
     */
    @TableField(value = "subtitle_type")
    private Integer subtitleType;
    /**
     * 阶段
     */
    @TableField(value = "stage")
    private Integer stage;
    /**
     * 源语言语音
     */
    @TableField(value = "src_speech_file_id")
    private String srcSpeechFileId;
    /**
     * 转写文本
     */
    @TableField(value = "transcribed_src_text")
    private String transcribedSrcText;
    /**
     * 转写时间
     */
    @TableField(value = "transcribed")
    private Integer transcribed;
    /**
     * 是否已转写
     */
    @TableField(value = "is_transcribed")
    private Boolean isTranscribed;
    /**
     * 审校文本
     */
    @TableField(value = "revised_src_text")
    private String revisedSrcText;
    /**
     * 审校时间
     */
    @TableField(value = "revised")
    private Integer revised;
    /**
     * 是否已审校
     */
    @TableField(value = "is_revised")
    private Boolean isRevised;
    /**
     * 质检文本
     */
    @TableField(value = "qa_src_text")
    private String qaSrcText;
    /**
     * 质检时间
     */
    @TableField(value = "assured")
    private Integer assured;
    /**
     * 是否已质检
     */
    @TableField(value = "is_assured")
    private Boolean isAssured;


}
