package com.example.huiyan.huiyan.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载相关服务
 */
public interface DownloadFileService {



    void fileDownload(HttpServletResponse response, String filePath);
}
