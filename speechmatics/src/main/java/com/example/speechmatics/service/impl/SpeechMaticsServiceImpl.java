package com.example.speechmatics.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.speechmatics.service.SpeechMaticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author wyq
 * @date 2023/10/19
 * @desc
 */

@Slf4j
@Service
public class SpeechMaticsServiceImpl implements SpeechMaticsService {
    @Value("${speech.matics.url}")
    private String url;
    @Value("${speech.matics.apiKey}")
    private String apiKey;
    @Resource
    private RestTemplate restTemplate;


    @Override
    public String createNewJob(String filePath, String langCode) {
        log.info("创建speechmatics转写作业，filePath:{}, langCode:{}", filePath, langCode);
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("data_file", new FileSystemResource(filePath));

        JSONObject config = new JSONObject();
        config.put("type", "transcription");
        JSONObject transcription_config = new JSONObject();
        transcription_config.put("operating_point", "enhanced");
        transcription_config.put("language", "en");
        config.put("transcription_config", transcription_config);
//        bodyMap.add("config", "{\"type\":\"transcription\",\"transcription_config\":{\"operating_point\":\"enhanced\",\"language\":\"en\"}}");
        bodyMap.add("config", config.toJSONString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(apiKey);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        String newJobUrl = url + "v2/jobs";
        ResponseEntity<String> responseEntity = restTemplate.exchange(newJobUrl, HttpMethod.POST, requestEntity, String.class);

        log.info("创建speechmatics转写作业返回：{}", responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JSONObject object = JSONObject.parseObject(responseEntity.getBody());
            return object.getString("id");
        }
        return null;
    }

    public static void main(String[] args) {
        JSONObject config = new JSONObject();
        config.put("type", "transcription");
        JSONObject transcription_config = new JSONObject();
        transcription_config.put("operating_point", "enhanced");
        transcription_config.put("language", "en");
        config.put("transcription_config", transcription_config);
        System.out.println(config.toJSONString());
    }
}
