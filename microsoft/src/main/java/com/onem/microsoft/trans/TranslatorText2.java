package com.microsoft.demo.trans;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TranslatorText2 {


    public static void main(String[] args) {
        List<String> srcList = new ArrayList<>();
        srcList.add("Returns the current value");
        srcList.add("Returns the current value");
        srcList.add("Returns the current value");
        List<String> trans = trans(srcList);
        System.out.println(JSON.toJSONString(trans));
    }


    public static List<String> trans(List<String> srcList) {
        List<String> tgtSegList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("api.translator.azure.cn")
                .addPathSegment("/translate")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("from", "en-US")
                .addQueryParameter("to", "zh-CN")
                .build();

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        // 分段翻译请求，将源语言句段按照每500句间隔进行请求。
        List<List<String>> segLists = splitList(srcList, 500);

        for (List<String> itemList : segLists) {

            /*List<TransTextInfo> tempList = new ArrayList<>();

            // 组装翻译请求参数
            for (String srcLangSeg : itemList) {
                TransTextInfo transTextInfo = new TransTextInfo();
                transTextInfo.setText(srcLangSeg);
                tempList.add(transTextInfo);
            }

            final JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(tempList));*/

            JSONArray jsonArray = new JSONArray();
            // 组装翻译请求参数
            for (String srcSeg : itemList) {
                JSONObject obj = new JSONObject();
                obj.put("text", srcSeg);
                jsonArray.add(obj);
            }

            log.info("参数：{}",jsonArray);

            RequestBody body = RequestBody.create(mediaType, jsonArray.toJSONString());

            Request request = new Request.Builder().url(httpUrl).post(body)
                    .addHeader("Ocp-Apim-Subscription-Key", "548c2913c38e4bbe840380c9067a5282")
                    .addHeader("Ocp-Apim-Subscription-Region", "chinaeast2")
                    .addHeader("Content-type", "application/json")
                    .build();

            String tgtText = null;

            boolean tansFlag = true;
            for (int i = 1; i <= 10 && tansFlag; i++) {
                try (Response response = client.newCall(request).execute()) {
                    log.info("返回代码：{}，请求时间：{}。", response.code(), LocalDateTime.now());
                    // 如API常见错误代码为429001，则请求次数过多
                    if (response.code() != 200) {
                        // 线程睡眠 millis 毫秒。
                        Thread.sleep(i * 5000L);
                        log.info("翻译接口返回代码：{}，请求时间：{}。", response.code(), LocalDate.now());
                    } else {
                        tgtText = response.body().string();
                        tansFlag = false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 翻译接口响应的数据
            List<MicrosoftTransVo> microsoftTransVoList = JSONObject.parseArray(tgtText, MicrosoftTransVo.class);

            if (!CollectionUtils.isEmpty(microsoftTransVoList)) {
                for (int i = 0; i < itemList.size(); i++) {
                    MicrosoftTransVo microsoftTransVo = microsoftTransVoList.get(i);
                    MicrosoftTransVo.TranslationInfo translationInfo = microsoftTransVo.getTranslations().get(0);
                    String translateText = translationInfo.getText();
                    tgtSegList.add(translateText);
                }
            }
        }

        return tgtSegList;
    }

    private static <T> List<List<T>> splitList(List<T> list, int subNum) {
        List<List<T>> tNewList = new ArrayList<List<T>>();
        int priIndex = 0;
        int lastPriIndex = 0;
        int insertTimes = list.size() / subNum;
        List<T> subList = new ArrayList<>();
        for (int i = 0; i <= insertTimes; i++) {
            priIndex = subNum * i;
            lastPriIndex = priIndex + subNum;
            if (i == insertTimes) {
                subList = list.subList(priIndex, list.size());
            } else {
                subList = list.subList(priIndex, lastPriIndex);
            }
            if (subList.size() > 0) {
                tNewList.add(subList);
            }
        }
        return tNewList;
    }
}
