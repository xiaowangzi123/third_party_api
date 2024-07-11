package com.example.huiyan.huiyan.video;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 视频格式格式
 */
@Getter
public enum VideoEnum {

    mp4("mp4,MP4", "mp4"),
    mkv("mkv,MKV", "mkv");

    private final String code;
    private final String extension;

    VideoEnum(String code, String extension) {
        this.code = code;
        this.extension = extension;
    }

    public static boolean isVideo(String extension) {
        if (StringUtils.isBlank(extension)) {
            return false;
        }
        for (VideoEnum video : VideoEnum.values()) {
            if (video.getCode().contains(extension.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
