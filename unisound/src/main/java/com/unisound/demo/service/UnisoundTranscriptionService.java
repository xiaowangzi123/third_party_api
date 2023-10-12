package com.unisound.demo.service;

import java.util.List;
import java.util.Map;

/**
 * @author wyq
 * @date 2023/10/9
 * @desc
 */
public interface UnisoundTranscriptionService {

    List<Map<String,Object>> transcription(String filePath,String lang);
}
