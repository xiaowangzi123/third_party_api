package com.example.speechmatics.service;

import com.example.speechmatics.entity.Subtitle;

import java.util.List;

/**
 * @author wyq
 * @date 2023/10/19
 * @desc
 */


public interface SpeechMaticsService {

    String createNewJob(String filePath,String langCode);

    List<String> jobIdList();

    void jobProgress(String taskId);

    List<Subtitle> getSubtitles(String taskId);
}
