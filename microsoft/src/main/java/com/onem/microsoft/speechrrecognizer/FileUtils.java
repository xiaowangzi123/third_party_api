package com.microsoft.demo.speechrrecognizer;

import com.alibaba.fastjson.JSON;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoInfo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author wyq
 * @date 2022/4/11
 * @desc
 */
public class FileUtils {
    public static long getByteSize(File file) {
        return file.length();
    }

    public static BigDecimal getKbSize(File file) {
        return new BigDecimal(file.length() + "").divide(new BigDecimal("1024"), BigDecimal.ROUND_UP);
    }

    public static int getMbSize(File file) {
        return (int) (file.length() / 1024 / 1024);
    }

    public static String getFormat(File file) throws EncoderException {
        MultimediaObject instance = new MultimediaObject(file);
        MultimediaInfo result = instance.getInfo();
        return result.getFormat();
    }

    public static long getDuration(File file) {
        MultimediaObject instance = new MultimediaObject(file);
        MultimediaInfo result = null;
        try {
            result = instance.getInfo();
        } catch (EncoderException e) {
            e.printStackTrace();
        }

        return result == null ? 0 : result.getDuration();
    }

    public static VideoInfo getVideoInfo(String srcPath) {
        MultimediaObject instance;
        MultimediaInfo result = null;
        try {
            if (srcPath.startsWith("http")) {
                instance = new MultimediaObject(new URL(srcPath));
            } else {
                instance = new MultimediaObject(new File(srcPath));
            }
            result = instance.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? null : result.getVideo();
    }

    //获取视频时长，毫秒级
    public static long getVideoDuration(String srcPath) {
        MultimediaObject instance;
        MultimediaInfo result = null;
        try {
            if (srcPath.startsWith("http")) {
                instance = new MultimediaObject(new URL(srcPath));
            } else {
                instance = new MultimediaObject(new File(srcPath));
            }
            result = instance.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return result == null ? null : result.getVideo();
        if (result != null) {
            return result.getDuration();
        } else {
            return 0;
        }
    }


    public static long getRemoteDuration(String url) {
/*//        String url = "http://192.168.31.10:8888/dfs1/M00/00/02/wKgfCl6azIyAE4rXAABcIN-F5V4141.WAV";
        File mediaFile = new File(url);
        FFmpegFileInfo ffmpegFileInfo = new FFmpegFileInfo(mediaFile);
        MultimediaInfo multimediaInfo = null;
        multimediaInfo = ffmpegFileInfo.getInfo(url);
        long playTime = multimediaInfo.getDuration();
        System.out.println(playTime);*/
        return 0;
    }


    /**
     * 获取远程文件大小
     *
     * @param urlPath
     * @return
     */
    public static int getRemoteSize(String urlPath) {
        int size;
        URL url = null;
        URLConnection conn = null;
        try {
            url = new URL(urlPath);
            conn = url.openConnection();
            size = conn.getContentLength();
            if (size >= 0) {
                return size;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;

    }

    public static VideoInfo getVideo(String srcPath) {
        MultimediaObject instance;
        MultimediaInfo result = null;
        try {
            if (srcPath.startsWith("http")) {
                instance = new MultimediaObject(new URL(srcPath));
            } else {
                instance = new MultimediaObject(new File(srcPath));
            }
            result = instance.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? null : result.getVideo();
    }

    public static AudioInfo getAudio(String srcPath) {
        MultimediaObject instance;
        MultimediaInfo result = null;
        try {
            if (srcPath.startsWith("http")) {
                instance = new MultimediaObject(new URL(srcPath));
            } else {
                instance = new MultimediaObject(new File(srcPath));
            }
            result = instance.getInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? null : result.getAudio();
    }


    public static void videoConvertToAudio(String sourceFilePath, String targetFilePath, String format) {
        File source = new File(sourceFilePath);
        File target = new File(targetFilePath);

        // 设置音频属性
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
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

    /**
     * 向文件中追加数据
     */
    public static void appendSrtToFile(String path, String data) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                createFile(file);
            }
            org.apache.commons.io.FileUtils.writeStringToFile(file, data, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断文件是否存在，不存在就创建
     */
    public static void createFile(File file) {
        try {
            //getParentFile() 获取上级目录(包含文件名时无法直接创建目录的)
            if (!file.getParentFile().exists()) {
                //创建上级目录
                file.getParentFile().mkdirs();
            }
            //在上级目录里创建文件
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
//        String srcPath = "C:\\Users\\云译048\\Desktop\\subtitle\\驯龙高手.mp4";
//        String srcPath = "C:\\TestAudioVideo\\988fdfdc800a41f9b0f110799cf18405.mp4";
        String tgtPath = "D:\\audio\\驯龙高手yinpin.mp3";
//        String srcPath = "D:\\TestAudioVideo\\transcode\\src.mpg";
//        String srcPath = "https://cloudtranslation.obs.cn-south-1.myhuaweicloud.com/bee432b332a749dbb464b05cc3d4679a.mp4";
        String srcPath = "D:\\TestAudioVideo\\1600\\src1600.mp4";
        VideoInfo videoInfo = getVideo(srcPath);
        System.out.println("video--> " + JSON.toJSONString(videoInfo));
        System.out.println("video--> " + videoInfo.getBitRate());
//        System.out.println("video info--> " + videoInfo.getSize().getWidth());
//        System.out.println(videoInfo.getFrameRate() >= 25);
        AudioInfo audioInfo = getAudio(srcPath);
        System.out.println("audio--> " + JSON.toJSONString(audioInfo));

//        videoConvertToAudio(srcPath,tgtPath,"mp3");
       /* File file = new File(srcPath);
        file.delete();*/
        System.out.println(getVideoDuration(srcPath));
//        System.out.println(FileUtils.getDuration(new File("C:\\Users\\CloudTranslation\\Downloads\\faa9bc75-f4bb-4d76-a7b6-f3916d06cfb5.wav")));
    }
}
