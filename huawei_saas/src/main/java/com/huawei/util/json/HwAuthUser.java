package com.huawei.util.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @author yunyi
 * 华为用户
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HwAuthUser {

    private String userName;
    private String name;
    private String orgCode;
    private String position;
    private String employeeCode;
    private String employeeType;
    private String role;
    private String mobile;
    private String email;
    private String workPlace;
    private String entryDate;
    private Date currentSyncTime;
    private Date timeStamp;
}
