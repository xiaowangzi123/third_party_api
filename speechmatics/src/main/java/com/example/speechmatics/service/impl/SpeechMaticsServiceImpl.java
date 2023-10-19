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
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        JSONObject output_config = new JSONObject();
        JSONObject srt_overrides = new JSONObject();
        srt_overrides.put("max_line_length", 100);
        srt_overrides.put("max_lines", 1);
        output_config.put("srt_overrides", srt_overrides);
        config.put("output_config", output_config);

        bodyMap.add("config", config.toJSONString());
//        bodyMap.add("config", "{\"type\":\"transcription\",\"transcription_config\":{\"operating_point\":\"enhanced\",\"language\":\"en\"}}");

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

    @Override
    public List<String> jobIdList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url + "v2/jobs"));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        String response = responseEntity.getBody();
        log.info("{}", response);


        return null;
    }

    @Override
    public void jobProgress(String taskId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url + "v2/jobs/"+taskId));
        boolean flag = true;
        while (flag){
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            System.out.println("result:--->"+responseEntity);
            if (responseEntity.getStatusCode().is2xxSuccessful()){
                JSONObject jobJson = JSONObject.parseObject(responseEntity.getBody());
                String string = jobJson.getJSONObject("job").getString("status");
                if ("done".equals(string)){
                    flag = false;
                }else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }else {
                flag = false;
            }


        }
    }


    @Override
    public Objects getSubtitles(String taskId) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer HDQGKgwNjMta4YzZSxUsqQ6prFr0skTF");

        // 创建请求体（如果需要传递请求参数）
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("format", "srt");
        //没有起作用
//        requestBody.add("segmentation", "true");

        // 创建RequestEntity
        String queryUrl = String.format("%sv2/jobs/%s/transcript?format=srt", url, taskId);
        RequestEntity<?> requestEntity = new RequestEntity<>(requestBody, headers, HttpMethod.GET, URI.create(queryUrl));

        // 发送请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        // 获取响应数据
        String response = responseEntity.getBody();
        System.out.println(response);
        return null;
    }

    public static void main(String[] args) {
        JSONObject config = new JSONObject();
        config.put("type", "transcription");
        JSONObject transcription_config = new JSONObject();
        transcription_config.put("operating_point", "enhanced");
        transcription_config.put("language", "en");
        config.put("transcription_config", transcription_config);
        JSONObject output_config = new JSONObject();
        JSONObject srt_overrides = new JSONObject();
        srt_overrides.put("max_line_length", 100);
        srt_overrides.put("max_lines", 1);
        output_config.put("output_config", srt_overrides);
        config.put("output_config", output_config);

        System.out.println(config.toJSONString());

        System.out.println("of weakness,".length());
        System.out.println("And like most people in my community, I had the misconception that depression was a sign".length());
    }
}
