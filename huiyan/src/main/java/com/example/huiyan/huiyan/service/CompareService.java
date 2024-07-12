package com.example.huiyan.huiyan.service;

public interface CompareService {

    String selectSegSave(String jobId);

    String cutAudioSlice(String jobId);

    String huiyanAsr(String jobId);
}
