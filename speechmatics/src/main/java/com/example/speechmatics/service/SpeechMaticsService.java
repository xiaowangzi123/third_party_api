package com.example.speechmatics.service;

import com.example.speechmatics.entity.Subtitle;

import java.util.List;

/**
 * SpeechMatics语音识别
 */


public interface SpeechMaticsService {

    String createNewJob(String filePath,String langCode);

    List<String> jobIdList();

    void jobProgress(String taskId);

    List<Subtitle> getSubtitles(String taskId);
}
