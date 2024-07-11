package com.example.huiyan.huiyan.service;

import com.example.huiyan.huiyan.entity.HuiyanAsrResult;

public interface HuiyanAsrService {

    String createTask(String filePath, String langCode);

    HuiyanAsrResult getSubtitles(String taskId);

    String deleteTask(String taskId);
}
