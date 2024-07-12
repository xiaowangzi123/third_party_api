package com.example.huiyan.huiyan.entity.table;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 句段对比
 *
 * @author ${author}
 * @date 2024-07-11 16:27:15
 */
@Data
@TableName("text_compare")
public class TextCompare implements Serializable {
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
     * 转写文本
     */
    @TableField(value = "micro_src_text")
    private String microSrcText;
    /**
     * 审校文本
     */
    @TableField(value = "huiyan_src_text")
    private String huiyanSrcText;

    @TableField(value = "same")
    private Boolean same;

}
