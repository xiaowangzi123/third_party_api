package com.unisound.iot;

import com.unisound.iot.util.FilePropertyUtil;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author :wyq
 * @date ：Created in 2022/3/19
 * @description :
 */
public class Main {
    public static void main(String[] args) throws EncoderException {
        String path = "F:\\Github\\yun_zhi_sheng\\1647689772834.mp3";
        File file =new File(path);
        System.out.println(file.length());
        
        String time = FilePropertyUtil.readVideoTime(file);
        System.out.println(time);
    }
}