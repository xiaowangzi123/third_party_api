package com.onem.demo.trans;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TranslatorText3 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Returns the current value");
        list.add("Returns the current value");
        list.add("Returns the current value");
        List<String> trans = trans(list);
        System.out.println(JSON.toJSONString(trans));
    }

    public static List<String> trans(List<String> srcList) {
        List<String> tgtSegList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("api.translator.azure.cn")
                .addPathSegment("translate")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("from", "en")
//                .addQueryParameter("from", "en_US")
//                .addQueryParameter("to", "zh_CN")  //下划线不行，中划线可以
                .addQueryParameter("to", "zh-Hans")
                .build();

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        /*List<String> aaa = new ArrayList<>();
        aaa.subList(1,10);*/

        // 分段翻译请求，将源语言句段按照每500句间隔进行请求。
        List<List<String>> segLists = subList(srcList, 500);

        for (List<String> itemList : segLists) {

            JSONArray jsonArray = new JSONArray();
            // 组装翻译请求参数
            for (String srcSeg : itemList) {
                JSONObject obj = new JSONObject();
                obj.put("text", srcSeg);
                jsonArray.add(obj);
            }

            /*List<TransTextInfo> tempList = new ArrayList<>();

            // 组装翻译请求参数
            for (String srcLangSeg : itemList) {
                TransTextInfo transTextInfo = new TransTextInfo();
                transTextInfo.setText(srcLangSeg);
                tempList.add(transTextInfo);
            }

            final JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(tempList));
*/
            log.info("参数：{}",jsonArray);
            RequestBody body = RequestBody.create(mediaType, jsonArray.toJSONString());

            Request request = new Request.Builder().url(httpUrl).post(body)
                    .addHeader("Ocp-Apim-Subscription-Key", "548c2913c38e4bbe840380c9067a5282")
                    .addHeader("Ocp-Apim-Subscription-Region", "chinaeast2")
                    .addHeader("Content-type", "application/json")
                    .build();

            String tgtText = null;
            boolean transFlag = true;
            for (int i = 1; i <= 10 && transFlag; i++) {
                try (Response response = client.newCall(request).execute()) {
                    log.info("返回代码：{}，请求时间：{}。", response.code(), LocalDateTime.now());
                    // 如API常见错误代码为429001，则请求次数过多
                    if (response.code() != 200) {
                        Thread.sleep(i * 5000L);
                    } else {
                        tgtText = response.body().string();
                        transFlag = false;
                    }
                } catch (IOException | InterruptedException e) {
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

    /**
     * 截取list集合，返回list集合
     *
     * @param tList  (需要截取的集合)
     * @param subNum (每次截取的数量)
     * @return
     */
    public static <T> List<List<T>> subList(List<T> tList, Integer subNum) {
        // 新的截取到的list集合
        List<List<T>> tNewList = new ArrayList<List<T>>();
        // 要截取的下标上限
        Integer priIndex = 0;
        // 要截取的下标下限
        Integer lastIndex = 0;
        // 每次插入list的数量
        // Integer subNum = 500;
        // 查询出来list的总数目
        Integer totalNum = tList.size();
        // 总共需要插入的次数
        Integer insertTimes = totalNum / subNum;
        List<T> subNewList = new ArrayList<T>();
        for (int i = 0; i <= insertTimes; i++) {
            // [0--20) [20 --40) [40---60) [60---80) [80---100)
            priIndex = subNum * i;
            lastIndex = priIndex + subNum;
            // 判断是否是最后一次
            if (i == insertTimes) {
                subNewList = tList.subList(priIndex, tList.size());
            } else {
                // 非最后一次
                subNewList = tList.subList(priIndex, lastIndex);

            }
            if (subNewList.size() > 0) {
                //logger.info("开始将截取的list放入新的list中");
                tNewList.add(subNewList);
            }

        }

        return tNewList;

    }

}
