package com.example.huiyan.huiyan.service;

import com.example.huiyan.huiyan.entity.table.SrcLangSeg;

public interface CutWavService {

    void cutWav(String jobId,String srcWavPath, SrcLangSeg seg);
}
