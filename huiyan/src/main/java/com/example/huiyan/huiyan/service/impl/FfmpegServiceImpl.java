package com.example.huiyan.huiyan.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.huiyan.huiyan.entity.DownVideoSegVo;
import com.example.huiyan.huiyan.entity.FfmpegRes;
import com.example.huiyan.huiyan.entity.PauseTimeDto;
import com.example.huiyan.huiyan.service.FfmpegService;
import com.example.huiyan.huiyan.service.FilePathService;
import com.example.huiyan.huiyan.utils.TimeConvertUtil;
import com.example.huiyan.huiyan.utils.VideoUtils;
import com.example.huiyan.huiyan.utils.ffmpeg.AudioSegDto;
import com.example.huiyan.huiyan.utils.ffmpeg.FFMPEGUtil;
import com.example.huiyan.huiyan.utils.ffmpeg.FfmpegCmdUtils;
import com.example.huiyan.huiyan.video.VideoEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.info.MultimediaInfo;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@RefreshScope
@Slf4j
@Service
public class FfmpegServiceImpl implements FfmpegService {

    @Resource
    private FilePathService filePathService;

    @Value("${translation.subtitle.path}")
    private String path;

    public static void main(String[] args) {
        FFMPEGUtil.cmdExecut(" -f concat -safe 0 -i \"c:/TestAudioVideo/concat.txt\" -c copy \"c:/TestAudioVideo/aaa.mp3\"");
    }

    /**
     * 获取视频时长(秒)
     */
    @Override
    public Integer getVideoDuration(String fileName) {
        int realDuration = FFMPEGUtil.getRealDuration(" -i " + fileName);
        if (realDuration > 0) {
            return realDuration;
        }

        File tempFile = new File(fileName);
        int duration = 0;
        try {
            MultimediaObject multimediaObject = new MultimediaObject(tempFile);
            MultimediaInfo info = multimediaObject.getInfo();
            duration = (int) Math.floor((double) info.getDuration() / 1000);
        } catch (Exception e) {
            log.error("获取视频时长失败", e);
        }
        return duration;
    }


    @Override
    public void videoConvertToWav(String sourceFilePath, String targetFilePath, String format) {
        File source = new File(sourceFilePath);
        File target = new File(targetFilePath);

        // 设置音频属性
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(16000));

        // 设置转码属性
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat(format);
        attrs.setAudioAttributes(audio);

        try {
            Encoder encoder = new Encoder();
            MultimediaObject mediaObject = new MultimediaObject(source);
            encoder.encode(mediaObject, target, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FfmpegRes mp4ToWav(String srcObsPath, String extension) {
        String tgtFilePath = filePathService.getFilePath(extension);
//        String tgtFilePath = getPath(extension);
        String cmdFfmpeg;
        if ("mp3".equals(extension)) {
            cmdFfmpeg = " -i " + srcObsPath + " " + tgtFilePath;
        } else if (VideoEnum.mkv.getCode().contains(FilenameUtils.getExtension(srcObsPath))) {
            cmdFfmpeg = "-i " + srcObsPath + " -vn -acodec pcm_s16le -f s16le -ar 16000 -f wav -y " + tgtFilePath;
        } else {
            cmdFfmpeg = " -i " + srcObsPath + " -acodec pcm_s16le -f s16le -ac 1 -ar 16000 -f wav -y " + tgtFilePath;
        }
        int resCode = FFMPEGUtil.cmdExecut(cmdFfmpeg);

        FfmpegRes ffmpegRes = new FfmpegRes();
        ffmpegRes.setCode(resCode);
        ffmpegRes.setTgtFilePath(tgtFilePath);
        return ffmpegRes;
    }

    @Override
    public int cutVideo(DownVideoSegVo vo) {
        //ffmpeg -i D:\TestAudioVideo\cut\test.mp4 -vcodec copy -acodec copy -ss 00:01:00 -to 00:01:30 D:\TestAudioVideo\cut\out040605.mp4
        String cmdFfmpeg = " -i " + vo.getSrcVideoPath() + "  -vcodec copy -acodec copy -ss " + TimeConvertUtil.timeConvert(vo.getStartTimecode()) + " -to " + TimeConvertUtil.timeConvert(vo.getEndTimecode()) + " " + vo.getPath();
        return FFMPEGUtil.cmdExecut(cmdFfmpeg);
    }

    @Override
    public int cutAudio(String srcPath, String targetPath, int start, int end) {
        String cmdFfmpeg = " -i " + srcPath + " -acodec copy -ss " + TimeConvertUtil.timeConvert(start) + " -to " + TimeConvertUtil.timeConvert(end) + " -y " + targetPath;
        return FFMPEGUtil.cmdExecut(cmdFfmpeg);
    }


    @Override
    public List<AudioSegDto> audioCut(String filePath) {
        log.info("audio cut start->{}", filePath);
        List<AudioSegDto> res = new ArrayList<>();
        try {
            List<PauseTimeDto> pauseTimeList = FfmpegCmdUtils.getPauseTimeList(filePath, "-30", 2);
            //找出时间点
            getPointTime(filePath, res, pauseTimeList);
            //音频分割
            res.forEach(a -> {
                int code = FfmpegCmdUtils.videoCut(filePath, a);
                log.info("音频分割：{}, 结果：{}", JSON.toJSONString(a), code);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("audio cut end->{}", JSON.toJSONString(res));
        return res;
    }

    public void getPointTime(String filePath, List<AudioSegDto> res, List<PauseTimeDto> pauseTimeList) {

        List<Double> pointList = new ArrayList<>();
        pointList.add((double) 0);
        pauseTimeList.forEach(t -> {
            pointList.add(FfmpegCmdUtils.formatStr(String.format("%.3f", (t.getEndTime() + t.getStartTime()) / 2)));
        });
        pointList.add(((double) VideoUtils.getVideoDuration(filePath) / 1000));

        List<Double> pointTime = getPointTime(pointList, 60);
        for (int i = 0; i < pointTime.size() - 1; i++) {
            AudioSegDto dto = new AudioSegDto();
            dto.setPointTime(pointTime.get(i));
            dto.setDuration((int) (pointTime.get(i + 1) - pointTime.get(i)));
            dto.setTotalSegs(pointTime.size() - 1);
            dto.setCurrentSeg(i);
            dto.setAudioPath(filePathService.getFilePath("wav"));
            res.add(dto);
        }
    }

    public List<Double> getPointTime(List<Double> numbers, double threshold) {
        List<Double> result = new ArrayList<>();
        if (numbers.size() > 0) {
            result.add(numbers.get(0));
            for (int i = 1; i < numbers.size() - 1; i++) {
                double current = numbers.get(i);
                double previous = numbers.get(i - 1);
                if (Math.abs(current - previous) > threshold) {
                    result.add(current);
                }
            }
            result.add(numbers.get(numbers.size() - 1));
        }
        return result;
    }

}
