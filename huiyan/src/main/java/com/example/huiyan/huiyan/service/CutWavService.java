package com.example.huiyan.huiyan.service;

import com.example.huiyan.huiyan.entity.table.TextCompare;

public interface CutWavService {

    void cutWav(String jobId,String srcWavPath, TextCompare seg);
}
