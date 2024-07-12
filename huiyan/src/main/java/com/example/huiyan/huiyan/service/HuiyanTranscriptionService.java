package com.example.huiyan.huiyan.service;

import com.example.huiyan.huiyan.entity.table.SrcLangSeg;

import java.util.List;

public interface HuiyanTranscriptionService {

    List<SrcLangSeg> transcribe(String audioFilePath, String lang, String name);

    String asr(String audioFilePath, String lang, String name);

}
