package com.example.huiyan.huiyan.service;

import com.example.huiyan.huiyan.entity.DownVideoSegVo;
import com.example.huiyan.huiyan.entity.FfmpegRes;
import com.example.huiyan.huiyan.utils.ffmpeg.AudioSegDto;

import java.util.List;

/**
 * @author wyq
 * @date 2022/3/15
 * @desc
 */
public interface FfmpegService {

    /**
     * 获取视频时长
     */
    Integer getVideoDuration(String fileName);

    /**
     * 视频文件转化为音频WAV文件
     */
    void videoConvertToWav(String sourceFilePath, String targetFilePath, String format);

    /**
     * mp4转mp3,或wav
     */
    FfmpegRes mp4ToWav(String srcObsPath, String extension);

    /**
     * 视频截取
     */
    int cutVideo(DownVideoSegVo downVideoSegVo);

    int cutAudio(String srcPath, String targetPath, int start, int end);


    List<AudioSegDto> audioCut(String filePath);

}
