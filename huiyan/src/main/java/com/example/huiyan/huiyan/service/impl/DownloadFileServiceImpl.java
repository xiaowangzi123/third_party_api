package com.example.huiyan.huiyan.service.impl;

import com.example.huiyan.huiyan.service.DownloadFileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author wyq
 * @date 2024/7/15
 * @desc
 */

@Service
public class DownloadFileServiceImpl implements DownloadFileService {
    @Override
    public void fileDownload(HttpServletResponse response, String filePath) {
        try {
            String fileName = FilenameUtils.getName(filePath);
            // 读到流中
            InputStream inStream = Files.newInputStream(Paths.get(filePath));
            // 设置输出的格式
            response.reset();
//            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setContentType("text/csv");
            response.setCharacterEncoding("UTF-8");
//            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").replace("\\+", "%20"));
//            response.addHeader("Content-Disposition", "attachment; filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            // 循环取出流中的数据
            byte[] b = new byte[1024 * 10];
            int len;
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}