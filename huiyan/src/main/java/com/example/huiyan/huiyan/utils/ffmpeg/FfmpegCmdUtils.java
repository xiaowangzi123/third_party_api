package com.example.huiyan.huiyan.utils.ffmpeg;

import com.alibaba.fastjson.JSON;
import com.example.huiyan.huiyan.entity.FfmpegRes;
import com.example.huiyan.huiyan.entity.PauseTimeDto;
import com.example.huiyan.huiyan.utils.TimeConvertUtil;
import com.example.huiyan.huiyan.utils.VideoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wyq
 * @date 2022/4/2
 * @desc
 */

@Slf4j
public class FfmpegCmdUtils {
    /**
     * 获取视频时长
     */
    public static Long getVideoDuration(String sourcePath) {
        Long duration = null;
        long start = System.currentTimeMillis();
        String cmdFfmpeg = " -i " + sourcePath;
        String dosText = FFMPEGUtil.exeDos(cmdFfmpeg);
        System.out.println(dosText);
        System.out.println("耗时：" + (System.currentTimeMillis() - start) / 1000);
        if (StringUtils.isNotBlank(dosText)) {
            int index = dosText.indexOf("Duration");
            int index2 = dosText.indexOf(",", index);
            String timeText = dosText.substring(index + 9, index2).trim();
            System.out.println("时长：" + timeText);
            String[] arr = timeText.split(":|\\.");
            if (arr.length == 4) {
                duration = Long.parseLong(arr[0]) * 3600000 + Long.parseLong(arr[1]) * 60000 + Long.parseLong(arr[2]) * 1000 + Long.parseLong(arr[3]) * 10;
            }
            if (arr.length == 3) {
                duration = Long.parseLong(arr[0]) * 3600000 + Long.parseLong(arr[1]) * 60000 + Long.parseLong(arr[2]) * 1000;
            }
        }
        return duration;
    }

    /**
     * 传入的是音频文件存放的绝对路径
     *
     * @param srcPath
     * @param tgtPath
     * @param operate
     * @return
     */
    public static int modifyVoice(String srcPath, String tgtPath, String operate) {
        FfmpegRes res = new FfmpegRes();
        String cmd;
        if ("+".equals(operate)) {
            cmd = " -i " + srcPath + " -vcodec copy -af \"volume=20dB\" " + tgtPath;
        } else {
            cmd = " -i " + srcPath + " -vcodec copy -af \"volume=-10dB\" " + tgtPath;
        }
        return FFMPEGUtil.cmdExecut(cmd);
    }

    public static int addBgm(String srcPath, String bgmPath, String tgtPath) {
//        ffmpeg -i D:\TestAudioVideo\music\dubbed6_54.mp4 -i D:\TestAudioVideo\music\bgmsub20.mp3 -filter_complex amix=inputs=2:duration=first:dropout_transition=2  D:\TestAudioVideo\music\outputsub20.mp4
//        String cmd = " -i " + srcPath + " -i " + bgmPath + " -filter_complex amix=inputs=2:duration=first:dropout_transition=2 " + tgtPath;
//        ffmpeg -i D:\TestAudioVideo\music\dubbed6_54.mp4 -i D:\TestAudioVideo\music\backgroundmusic1.mp3 -filter_complex "[0:a]volume=10dB[a0];[1:a]volume=-5dB[a1];[a0][a1]amix=inputs=2[a]" -map 0:v -map "[a]" -c:v copy -c:a aac -shortest  D:\TestAudioVideo\music\outputsub040203.mp4
//        String cmd = " -i " + srcPath + " -i " + bgmPath + " -filter_complex " + "\"[0:a]volume=10dB[a0];[1:a]volume=-5dB[a1];[a0][a1]amix=inputs=2[a]\"" + " -map 0:v -map " + "\"[a]\"" + " -c:v copy -c:a aac -shortest " + tgtPath;
        String cmd = " -i " + srcPath + " -i " + bgmPath + " -filter_complex amix=inputs=2:duration=first:dropout_transition=2 " + tgtPath;
        System.out.println("添加背景音--->" + cmd);
        return FFMPEGUtil.cmdExecut(cmd);
    }

    public static int addBgmLoop(String srcPath, String bgmPath, String tgtPath, String duration) {
        String cmd = " -i " + srcPath + " -i " + bgmPath + " -filter_complex [1:a]aloop=loop=-1:size=2e+09[out];[out][0:a]amix -ss 0 -t " + duration + " " + tgtPath;
        System.out.println("添加循环背景音--->" + cmd);
        return FFMPEGUtil.cmdExecut(cmd);
    }

    public static int addBgmLoop(String srcPath, String bgmPath, String tgtPath) {
//        long duration = VideoUtils.getVideoDuration(srcPath);
        long duration = VideoUtils.getVideoDurationSec(srcPath);
        String time = TimeConvertUtil.timeConvert((int) duration);
        String cmd = " -i " + srcPath + " -i " + bgmPath + " -filter_complex [1:a]aloop=loop=-1:size=2e+09[out];[out][0:a]amix -ss 0 -t " + time + " " + tgtPath;
        System.out.println("添加循环背景音--->" + cmd);
        return FFMPEGUtil.cmdExecut(cmd);
    }


    /**
     * @param path     wav格式文件路径，其他格式不行
     * @param db       噪音容限值，小于此值的默认为静音开始时间点，一般建议设置-30
     * @param duration 持续时间，一般为2，单位：秒；
     * @return 超过duration，且音量小于容限值，视为一条静音记录。返回静音开始时间和结束时间列表
     */
    public static List<PauseTimeDto> getPauseTimeList(String path, String db, double duration) {
        List<PauseTimeDto> res = new ArrayList<>();
        if (StringUtils.isBlank(path) || !path.endsWith("wav")) {
            log.info("停顿点检测，文件路径为空或不是wav文件{}", path);
            return res;
        }
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            log.info("停顿点检测，文件不存在：{}", path);
            return res;
        }
        String cmd = " -nostats -i " + path + " -af silencedetect=noise=" + db + "dB:d=" + duration + " -f null /dev/null";
        String dos = FFMPEGUtil.exeDos(cmd);
        String[] split = dos.split("\r\n");
        List<Double> startTime = new ArrayList<>();
        List<Double> endTime = new ArrayList<>();
        for (String s : split) {
            if (s.contains("silence_start")) {
                String s1 = s.split(": ")[1];
                startTime.add(formatStr(s1));

            }
            if (s.contains("silence_end")) {
                String s2 = s.split(": |\\|")[1];
                endTime.add(formatStr(s2));
            }
        }
        if (startTime.size() == endTime.size()) {
            for (int i = 0; i < startTime.size(); i++) {
                PauseTimeDto pauseTime = new PauseTimeDto();
                pauseTime.setStartTime(startTime.get(i));
                pauseTime.setEndTime(endTime.get(i));
                res.add(pauseTime);
            }
        }
        return res;
    }


    /**
     * 保留三位小数
     *
     * @param s
     * @return
     */
    public static double formatStr(String s) {
        //转换成Double
        Double number = Double.parseDouble(s);
        //格式化
        DecimalFormat df = new DecimalFormat("#.000");
        String result = df.format(number);
        return Double.parseDouble(result);
    }

    public static int videoCut(String filePath, AudioSegDto segDto) {
        //ffmpeg -ss 00:00:00 -i D:\TestAudioVideo\wav\eng_12M48S.wav -c copy -t 52 D:\TestAudioVideo\wav\eng_12M48S-1.wav
        String cmd = " -ss " + TimeConvertUtil.timeConvert((int) (segDto.getPointTime()*1000)) + " -i " + filePath + " -c copy -t " + segDto.getDuration() + " " + segDto.getAudioPath();
        return FFMPEGUtil.cmdExecut(cmd);
    }


    public static void main(String[] args) {
        String path = "D:\\TestAudioVideo\\7_49En.mp4";
//        AudioInfo audio = VideoUtils.getAudio(path);
        int videoDurationSec = VideoUtils.getVideoDurationSec(path);
        long videoDuration = VideoUtils.getVideoDuration(path);
//        System.out.println(JSON.toJSONString(audio));
        System.out.println(videoDurationSec);
        //用这个
        System.out.println(videoDuration);

        List<PauseTimeDto> pauseTimeList = getPauseTimeList(path, "-30", 2);
        System.out.println(JSON.toJSONString(pauseTimeList));

        String s = TimeConvertUtil.timeConvert(videoDurationSec);

        List<Double> pointList = new ArrayList<>();
        pointList.add((double) 0);
        pauseTimeList.forEach(t -> {
            pointList.add(formatStr(String.format("%.3f", (t.getEndTime() + t.getStartTime()) / 2)));
        });
        pointList.add(((double) videoDuration / 1000));
        System.out.println(JSON.toJSONString(pointList));

        List<Double> pointTime = getPointTime(pointList, 60);
        System.out.println(JSON.toJSONString(pointTime));
    }

    public static String formatDouble(double d) {
        // 格式化保留三位小数
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return decimalFormat.format(d);
    }

    public static List<Double> getPointTime(List<Double> numbers, double threshold) {
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
