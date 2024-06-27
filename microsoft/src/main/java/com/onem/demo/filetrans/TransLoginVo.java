package com.onem.demo.filetrans;

import lombok.Data;

import java.io.Serializable;

/**
 * 云译翻译接口登录请求参数
 */
@Data
public class TransLoginVo implements Serializable {
    private String account;//账号
    private String password;//密码
}
