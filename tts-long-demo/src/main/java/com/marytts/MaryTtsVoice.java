package com.marytts;

import marytts.LocalMaryInterface;

/**
 * @author wyq
 * @date 2025/1/16
 * @desc
 */
public class MaryTtsVoice {
    public static void main(String[] args) {
        try {
            // 创建MaryTTS客户端
            LocalMaryInterface maryClient = new LocalMaryInterface();

            // 获取并打印所有可用的语音模型
            System.out.println("-----------------------------Available voices-----------------------");
            for (String voice : maryClient.getAvailableVoices()) {
                System.out.println(voice);
            }
            System.out.println("-----------------------------Available voices-----------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
