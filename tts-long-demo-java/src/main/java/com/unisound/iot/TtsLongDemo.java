package com.unisound.iot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 长文本语音合成
 */
public class TtsLongDemo {
    private static String host = "http://ltts.hivoice.cn";
    private static String userId = "unisound-long-text-demo";

    static String appkey = "45gn7md5n44aak7a57rdjud3b5l4xdgv75saomys";
    static String secret = "ba24a917a38e11e49c6fb82a72e0d896";


    //    private static String format = "wav";
    private static String format = "mp3";
    private static String vcn = "kiyo-base";

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\Github\\yun_zhi_sheng\\tts-long-demo-java\\text\\unisound.txt");
        String text = FileUtils.readFileToString(file, "utf-8");
        testTTS(text);
    }

    private static long testTTS(String text) {
        String taskId;
        String ret;
        //上传文本
        ret = start(host, text);
        System.out.println("start result:" + ret);
        taskId = JSON.parseObject(ret).getString(PARAM_NAME_TASKID);
        SystemUtils.sleep(3000);
        //获取合成状态
        String result = getProgress(host, taskId);
        System.out.println("status result: " + result);
        JSONObject res = JSON.parseObject(result);
        //循环获取状态
        while (!isEnd(res)) {
            SystemUtils.sleep(3000);
            result = getProgress(host, taskId);
            System.out.println("while status result: " + result);
            res = JSON.parseObject(result);
        }
        try {
            //下载语音
            String audio_address = res.getString("audio_address");
            byte[] bytes = SystemUtils.httpGet(audio_address, null, true);
            FileUtils.writeByteArrayToFile(new File("audio/unisound.mp3"), bytes);
            System.out.println("download audio success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private static boolean isEnd(JSONObject json) {
        if (null == json) {
            return false;
        }
        String status = json.getString("task_status");

        return "done".equals(status);
    }


    private static String getProgress(String host, String taskId) {
        String url = host + "/progress?";

        String time = System.currentTimeMillis() + "";
        String builder = appkey + time + secret;
        String sign = SystemUtils.encryptSHA256(builder);
        Map<String, String> params = new TreeMap<String, String>();
        params.put(PARAM_NAME_TASKID, taskId);
        params.put(PARAM_NAME_APPKEY, appkey);
        params.put(PARAM_NAME_TIMESTAMP, time);
        params.put(PARAM_NAME_SIGNATURE, sign);
        byte[] data = SystemUtils.httpPost(url, JSON.toJSONBytes(params), null, true);
        if (data != null) {
            return new String(data);
        }
        return null;
    }

    private static String start(String host, String text) {
        try {
            String url = host + "/start?";
            String time = System.currentTimeMillis() + "";
            String builder = appkey + time + secret;
            String sign = SystemUtils.encryptSHA256(builder);
            Map<String, Object> params = new TreeMap<String, Object>();
            params.put(PARAM_NAME_APPKEY, appkey);
            params.put(PARAM_NAME_TIMESTAMP, time);
            params.put(PARAM_NAME_SIGNATURE, sign);
            params.put(PARAM_NAME_USER, userId);
            params.put(PARAM_NAME_FORMAT, format);
            params.put(PARAM_NAME_VCN, vcn);
            params.put(PARAM_NAME_SAMPLE, 16000);
            params.put(PARAM_NAME_TEXT, text);

            byte[] data = SystemUtils.httpPost(url, JSON.toJSONBytes(params), null, true);

            if (data != null) {
                return new String(data);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static final String PARAM_NAME_USER = "user_id";

    private static final String PARAM_NAME_FORMAT = "format";

    private static final String PARAM_NAME_VCN = "vcn";

    private static final String PARAM_NAME_TEXT = "text";

    private static final String PARAM_NAME_APPKEY = "appkey";

    private static final String PARAM_NAME_TIMESTAMP = "time";

    private static final String PARAM_NAME_SIGNATURE = "sign";

    private static final String PARAM_NAME_TASKID = "task_id";

    private static final String PARAM_NAME_SAMPLE = "sample";


}
