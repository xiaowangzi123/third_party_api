package com.example.evaluate.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 参考答案
 * </p>
 *
 * @author kimi
 * @since 2022-09-16
 */
@Data
public class QuestionKey implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String quId;

    private String content;

    private Integer sortOrder;

    private Integer srcType;

    private String tchId;

    private String stuId;

}
