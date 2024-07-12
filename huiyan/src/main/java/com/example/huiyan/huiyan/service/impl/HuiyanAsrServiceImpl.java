package com.example.huiyan.huiyan.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.huiyan.huiyan.entity.HuiyanAsrResult;
import com.example.huiyan.huiyan.entity.SpeechSubtitle;
import com.example.huiyan.huiyan.service.HuiyanAsrService;
import com.example.huiyan.huiyan.utils.TimeConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 慧言语音识别api
 */
@Slf4j
@Service
public class HuiyanAsrServiceImpl implements HuiyanAsrService {
    @Value("${huiyan.asr.create}")
    private String createUrl;
    @Value("${huiyan.asr.result}")
    private String resultUrl;
    @Value("${huiyan.asr.delete}")
    private String deleteUrl;
    @Resource
    private RestTemplate restTemplate;


    @Override
    public String createTask(String filePath, String langCode) {
        log.info("慧言创建语音识别任务：{}，lang:{}", filePath, langCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("lang_type", langCode);
        body.add("file", new FileSystemResource(filePath));
        body.add("format", "wav");
        body.add("output", "subtitle");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(createUrl, HttpMethod.POST, requestEntity, String.class);
        log.info("创建慧言语音识别任务返回：{}", responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String response = responseEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(response);
            return jsonObject.getJSONObject("data").getString("task_id");
        } else {
            log.info("Request failed with status code: {}", responseEntity.getStatusCode());
        }
        return null;
    }


    @Override
    public HuiyanAsrResult getSubtitles(String taskId) {
        HuiyanAsrResult result = new HuiyanAsrResult();
        String subtitleUrl = resultUrl + "?task_id=" + taskId;
        String asrResult = doGet(subtitleUrl);
        if (Objects.isNull(asrResult)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(asrResult);
        String status = jsonObject.getString("status");
        if ("00000".equals(status)) {
            result.setProgress(100);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            result.setSrtList(dataConvert(jsonArray));
        } else {
            Integer progress = jsonObject.getJSONObject("data").getInteger("progress");
            result.setProgress(progress);
        }
        result.setStatus(status);
        return result;
    }

    @Async
    @Override
    public void deleteTask(String taskId) {
        log.info("删除任务ID：{}", taskId);
        if (StringUtils.isBlank(taskId)) {
            log.info("taskId不存在，无法删除任务");
        }
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 请求体为空字符串
        String requestBody = "";

        // 构建 HTTP 请求实体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // 请求 URL
        String url = deleteUrl + "?task_id=" + taskId;

        // 发送 POST 请求
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        // 获取响应数据
        log.info("删除任务返回：{}", responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info( "删除成功：" + taskId);
        } else {
            log.info( "调用接口失败");
        }
    }

    public String doGet(String subtitleUrl) {
        try {
            URL url = new URL(subtitleUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            InputStream inStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append(System.lineSeparator());
            }
            reader.close();
            log.info("慧言语音识别结果：{}", response);
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SpeechSubtitle> dataConvert(JSONArray jsonArray) {
        List<SpeechSubtitle> subtitles = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            SpeechSubtitle subtitle = new SpeechSubtitle();
            Integer begin = TimeConvertUtil.dateParseRegExp(json.getString("begin").replace(",", "."));
            subtitle.setStartTime(begin);
            Integer end = TimeConvertUtil.dateParseRegExp(json.getString("end").replace(",", "."));
            subtitle.setEndTime(end);
            subtitle.setSequence(json.getInteger("seg_num"));
            subtitle.setText(json.getString("transcript"));
            subtitles.add(subtitle);
        }
        return subtitles;
    }
}
