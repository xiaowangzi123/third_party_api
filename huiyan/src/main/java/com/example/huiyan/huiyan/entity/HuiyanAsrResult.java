package com.example.huiyan.huiyan.entity;

import lombok.Data;

import java.util.List;

@Data
public class HuiyanAsrResult {

    private String status;
    private Integer progress;
    private List<SpeechSubtitle> srtList;
}
