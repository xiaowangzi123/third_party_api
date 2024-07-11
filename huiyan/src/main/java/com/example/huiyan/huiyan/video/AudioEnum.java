package com.example.huiyan.huiyan.video;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 音频格式格式
 */
@Getter
public enum AudioEnum {

    mp3("mp3,MP3", "mp3");

    private final String code;
    private final String extension;

    AudioEnum(String code, String extension) {
        this.code = code;
        this.extension = extension;
    }

    public static boolean isAudio(String extension) {
        if (StringUtils.isBlank(extension)) {
            return false;
        }
        for (AudioEnum video : AudioEnum.values()) {
            if (video.getCode().contains(extension.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
