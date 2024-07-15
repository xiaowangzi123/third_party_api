package com.example.huiyan.huiyan.service.impl;

import com.example.huiyan.huiyan.service.FilePathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wyq
 * @date 2023/3/27
 * @desc
 */

@Service
@Slf4j
public class FilePathServiceImpl implements FilePathService {

    @Value("${translation.subtitle.path}")
    private String path;

    @Override
    public String getTempPath(String fileName) {
        String srtPath;
        if (System.getProperty("os.name").contains("Win")) {
            //开发环境路径
            srtPath = "C:" + File.separator + "temp" + File.separator + "temp" + File.separator + UUID.randomUUID() + File.separator + fileName;
        } else {
            //测试环境路径
            srtPath = path + File.separator + "temp" + File.separator + UUID.randomUUID() + File.separator + fileName;
        }
        createFile(new File(srtPath));
        return srtPath;
    }

    @Override
    public String getFilePath(String extension) {
        String setFilePath;
        String uuid = UUID.randomUUID().toString();
        if (System.getProperty("os.name").contains("Win")) {
            // 设置本地的视频文件名
            setFilePath = "C:" + File.separator + "temp" + File.separator + "temp" + File.separator + uuid + "." + extension;
        } else {
            //服务器保存路径
            setFilePath = path + File.separator + "temp" + File.separator + uuid + "." + extension;
        }
        createFile(new File(setFilePath));
        return setFilePath;
    }

    @Override
    public String assTempPath() {
        String assPath;
        if (System.getProperty("os.name").contains("Win")) {
            assPath = "C:/temp/temp/assSub" + System.currentTimeMillis() + ".ass";
        } else {
            assPath = path + File.separator + "temp" + System.currentTimeMillis() + ".ass";
        }
        createFile(new File(assPath));
        return assPath;
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
}
