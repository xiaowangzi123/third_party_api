package com.example.huiyan.huiyan.service;

import javax.servlet.http.HttpServletResponse;

public interface CompareService {

    String selectSegSave(String jobId);

    String cutAudioSlice(String jobId);

    String huiyanAsr(String jobId);

    void exportSeg(HttpServletResponse response, String jobId);
}
