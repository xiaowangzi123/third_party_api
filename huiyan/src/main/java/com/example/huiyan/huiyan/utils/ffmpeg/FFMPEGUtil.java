package com.example.huiyan.huiyan.utils.ffmpeg;


import com.example.huiyan.huiyan.utils.TimeConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Slf4j
public class FFMPEGUtil {
    /**
     * @throws
     * @Description: (执行ffmpeg自定义命令)
     * @param: @param cmdStr
     * @param: @return
     * @return: Integer
     */
    public static int cmdExecut(String cmdStr) {
        //code=0表示正常
        Integer code = null;
        FfmpegCmd ffmpegCmd = new FfmpegCmd();

        //错误流
        InputStream errorStream = null;
        try {
            //destroyOnRuntimeShutdown表示是否立即关闭Runtime
            //如果ffmpeg命令需要长时间执行，destroyOnRuntimeShutdown = false

            //openIOStreams表示是不是需要打开输入输出流:
            //	       inputStream = processWrapper.getInputStream();
            //	       outputStream = processWrapper.getOutputStream();
            //	       errorStream = processWrapper.getErrorStream();
            ffmpegCmd.execute(false, true, cmdStr);
            errorStream = ffmpegCmd.getErrorStream();
            //打印过程
            int len = 0;
            while ((len = errorStream.read()) != -1) {
                System.out.print((char) len);
            }
            //code=0表示正常
            code = ffmpegCmd.getProcessExitCode();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            ffmpegCmd.close();
        }
        //返回
        return code;
    }

    /**
     * @param cmdStr
     * @param fileId
     * @param bgmFlag:是否压制背景音
     * @return
     */
    public static int cmdExecut(String cmdStr, String fileId, boolean bgmFlag) {
        //code=0表示正常
        Integer code = null;
        FfmpegCmd ffmpegCmd = new FfmpegCmd();
        //错误流
        InputStream errorStream = null;
        try {
            ffmpegCmd.execute(false, true, cmdStr);
            errorStream = ffmpegCmd.getErrorStream();
            BufferedInputStream in = new BufferedInputStream(errorStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                System.out.println(lineStr);
            }
            //code=0表示正常
            code = ffmpegCmd.getProcessExitCode();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            ffmpegCmd.close();
        }
        return code;
    }


    public static String exeDos(String cmdStr) {
        FfmpegCmd ffmpegCmd = new FfmpegCmd();
        //错误流
        InputStream errorStream = null;
        try {
            ffmpegCmd.execute(false, true, cmdStr);
            errorStream = ffmpegCmd.getErrorStream();
            return IOUtils.toString(errorStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            ffmpegCmd.close();
        }
        //返回
        return null;
    }


    public static int getRealDuration(String cmdStr) {
        //code=0表示正常
        int code = 0;
        FfmpegCmd ffmpegCmd = new FfmpegCmd();

        InputStream errorStream = null;
        try {
            ffmpegCmd.execute(false, true, cmdStr);
            errorStream = ffmpegCmd.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(errorStream));
            String line;
            while ((line = br.readLine()) != null) {
                log.info("{}", line);
                String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb/s";
                Pattern pattern = Pattern.compile(regexDuration);
                Matcher m = pattern.matcher(line);
                if (m.find()) {
//                    System.out.println("视频时长："+m.group(1)+", 开始时间："+m.group(2)+", 比特率："+m.group(3)+"kb/s");
                    String durationSrt = m.group(1);
                    String startSrt = m.group(2);
                    log.info("video Duration:{}, start:{}, bitrate:{}", durationSrt, startSrt, m.group(3));
                    return realDuration(durationSrt, startSrt);
                }

            }
            //code=0表示正常
            code = ffmpegCmd.getProcessExitCode();
            log.info("video infomation:{}", code);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            ffmpegCmd.close();
        }
        //返回
        return 0;
    }

    public static int realDuration(String durationSrt, String startSrt) {
        if (StringUtils.isBlank(durationSrt) || StringUtils.isBlank(startSrt)) {
            return 0;
        }
        try {
            long timeStamp = TimeConvertUtil.getTimeStamp(durationSrt);
            int start = Integer.parseInt(startSrt.split("\\.")[0]);
            if (start > 0) {
                return (int) (timeStamp / 1000 - start);
            } else {
                return (int) (timeStamp / 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}