package com.unisound.iot.util;

import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * @author :wyq
 * @date ：Created in 2022/3/19
 * @description :
 */
public class FilePropertyUtil {
    /**
     * 获取音视频时长
     *
     * @param file 音视频文件
     * @return 音视频时长（单位：秒）
     */
    public static String readVideoTime(File file) throws EncoderException {
        MultimediaObject object = new MultimediaObject(file);
        MultimediaInfo info = object.getInfo();
        long time = info.getDuration() / 1000;
        return String.valueOf(time);
    }

    /**
     * 视频时长
     *
     * @param fileUrl
     * @return
     */
    public static String readVideoTime(String fileUrl) {
        File source = new File(fileUrl);
        String length = "";
        try {
            MultimediaObject instance = new MultimediaObject(source);
            MultimediaInfo result = instance.getInfo();
            long ls = result.getDuration() / 1000;
            int hour = (int) (ls / 3600);
            int minute = (int) (ls % 3600) / 60;
            int second = (int) (ls - hour * 3600 - minute * 60);
            String hr = Integer.toString(hour);
            String mi = Integer.toString(minute);
            String se = Integer.toString(second);
            if (hr.length() < 2) {
                hr = "0" + hr;
            }
            if (mi.length() < 2) {
                mi = "0" + mi;
            }
            if (se.length() < 2) {
                se = "0" + se;
            }
            length = hr + ":" + mi + ":" + se;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }


    private static File getRemoteFile(String path) throws Exception {
        URL url = new URL(path);
        //链接url
        URLConnection uc = url.openConnection();
        InputStream in = uc.getInputStream();
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        String prefix = "." + suffix;
        File file = File.createTempFile(UUID.randomUUID().toString(), prefix);
        OutputStream os = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int readTmp;
        while ((readTmp = in.read(b)) != -1) {
            os.write(b, 0, readTmp);
            os.flush();
        }
        return file;
    }



    /**
     * 视频大小
     *
     * @param source
     * @return
     */
    public static String readVideoSize(File source) {
        FileChannel fc = null;
        String size = "";
        try {
            FileInputStream fis = new FileInputStream(source);
            fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            size = fileSize.divide(new BigDecimal(1024 * 1024), 2, RoundingMode.HALF_UP) + "MB";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }
}