package com.unisound.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unisound.demo.entity.Result;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 音频文件转写demo
 * <p>
 * # 新版Unisound URl #云之声url
 * NEWURL = 'ws://112.47.13.106:8080/v1/ws?'
 * # NEWURL = 'ws://192.168.3.169:8080/v1/ws?'
 * <p>
 * NEWAPPKEY = "45gn7md5n44aak7a57rdjud3b5l4xdgv75saomys"
 * NEWSECRET = "ba24a917a38e11e49c6fb82a72e0d896"
 * DOMAIN = 'general'
 */
public class UtserviceDemo2 {
    //    public static String host = "http://112.47.13.106:8080/utservice/v2";
//    public static String appkey = "45gn7md5n44aak7a57rdjud3b5l4xdgv75saomys";
//    public static String secret = "ba24a917a38e11e49c6fb82a72e0d896";
//    private static final String userId = "test_123123";
    public static String host = "http://af-asr.hivoice.cn//utservice/v2";
    //    private static final String userId = UUID.randomUUID().toString();
//    static String appkey = "urnl24i6alfv7o7eiq3emdgt7gkn7xseksbxsqqe";
//    static String secret = "3babad6e0507b1a86ec7037c09c56e1b";
    public static final String PARAM_NAME_ERROR_CODE = "error_code";

    static String appkey = "jyve65pax7cnb4srrb2e7zq3ccstb6gphxgcwmqd";
    static String secret = "7409a837341262bdeac90ab35907d9c9";

    // 待转写文件路径
//	static  String file = "audio/";
//    static String file = "D:\\gitee\\asr-file-transfer-master\\asr-audio-file-demo-java\\audio\\unisound.wav";
//    static String file = "D:\\TestAudioVideo\\a0002.wav";
    static String file = "D:\\TestAudioVideo\\20Eng.wav";

    // 输出日志路径
    private static final String logPath = "";
    // 待转写文件格式
    private static final String format = "wav";

    public static void main(String[] args) {
        try {
            testAppendUpload(file, format);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long testAppendUpload(String file, String format) throws IOException {

        String userId = UUID.randomUUID().toString();


        String ret = appendUploadInit(host, userId);
        //{"task_id":"d2e70c0afebf437d91a39a2d993ed8c2","error_code":0,"message":"OK"}
        System.out.println("初始化转写任务，返回：" + ret);
        JSONObject initRet = JSONObject.parseObject(ret);
        Integer integer = initRet.getInteger(PARAM_NAME_ERROR_CODE);
        String textId = JSONObject.parseObject(ret).getString(PARAM_NAME_TEXTID);

        System.out.println("-----code>"+integer);

        String md5 = null;
        try {
            md5 = SystemUtils.getMd5Hex(new FileInputStream(file));
            System.out.println("--->文件md5:" + md5);
            String uploadRes = appendUploadUpload(host, file, textId, format, md5, userId);
            System.out.println("uploadRes--->" + uploadRes);
            ret = appendUploadGetStatus(host, textId);
            System.out.println(ret);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ret = appendUploadFinish(host, textId, format, "other", md5,userId);
        System.out.println(ret);

        String result = getText(host, textId);
        System.out.println(result);
        JSONObject jo = JSONObject.parseObject(result);

        Result resultEntity = Result.fromJson(result);

        while (!isEnd(jo)) {
            SystemUtils.sleep(3000);
            result = getText(host, textId);
            System.out.println(result);
            jo = JSONObject.parseObject(result);
        }

        // 打印结果到文件
        String[] tmp = file.split(File.pathSeparator);
        String[] s = tmp[tmp.length - 1].split("\\.");
        File o = new File(logPath + s[0] + ".txt");

        JSONArray array = jo.getJSONArray("results");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            builder.append(array.getJSONObject(i).getString("text"));
        }
        builder.append("\r\n\r\n")
                .append("********带时间戳的详细信息**********\r\n")
                .append(result);

//        FileUtils.writeStringToFile(o, builder.toString(), "UTF-8");

        //生成字幕文件
//        File srtFile = new File(logPath + s[0] + ".srt");
//        srtWriter(jo, srtFile);
        System.out.println(builder);
        return jo.getLongValue("cost_time");

    }

    private static void srtWriter(JSONObject result, File srtFile) {
        JSONArray array = result.getJSONArray("results");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            builder.append(array.getJSONObject(i).getInteger("index") + 1);
            builder.append("\n");
            int start = array.getJSONObject(i).getInteger("start");
            int end = array.getJSONObject(i).getInteger("end");
            builder.append(convertTimeFormat(start));
            builder.append(" --> ");
            builder.append(convertTimeFormat(end));
            builder.append("\n");
            builder.append(array.getJSONObject(i).getString("text"));
            builder.append("\n");
            builder.append("\n");
        }
        try {
            FileUtils.writeStringToFile(srtFile, builder.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertTimeFormat(int millisecond) {
        int ms = millisecond % 1000;
        int s = millisecond / 1000 % 60;
        int min = millisecond / 1000 / 60 % 60;
        int hour = millisecond / 1000 / 60 / 60;
        DecimalFormat df = new DecimalFormat("00");
        DecimalFormat dfms = new DecimalFormat("000");
        String builder = df.format(hour) +
                ":" +
                df.format(min) +
                ":" +
                df.format(s) +
                "," +
                dfms.format(ms);
        return builder;
    }

    public static boolean isEnd(JSONObject jo) {
        if (null == jo) {
            return false;
        }
        long progress = jo.getLongValue("progress");
        long duration = jo.getLongValue("duration");
        String status = jo.getString("status");

        return "done".equals(status) && (progress == duration);
    }

    public static String getText(String baseUrl, String textId) {
        String url = baseUrl + "/trans/text?";

        Map<String, String> params = new TreeMap<String, String>();

        params.put(PARAM_NAME_TEXTID, textId);
        params.put(PARAM_NAME_APPKEY, appkey);
        params.put(PARAM_NAME_TIMESTAMP, System.currentTimeMillis() + "");
        params.put(PARAM_NAME_SIGNATURE, buildSign(secret, params));

        String reqUrl = buildReqUrl(url, params);

        byte[] ret = new byte[500];
        String strRet = new String(SystemUtils.httpGet(reqUrl, ret, true));
        return strRet;
    }

    public static String appendUploadInit(String baseUrl, String userId) {
        try {
            String url = baseUrl + "/trans/append_upload/init?";

            Map<String, String> params = new TreeMap<String, String>();

            params.put(PARAM_NAME_APPKEY, appkey);
            params.put(PARAM_NAME_USER, userId);
            params.put(PARAM_NAME_TIMESTAMP, System.currentTimeMillis() + "");
            params.put(PARAM_NAME_SIGNATURE, buildSign(secret, params));

            String reqUrl = buildReqUrl(url, params);

            byte[] data = SystemUtils.httpPost(reqUrl, null, null, true);

            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String appendUploadUpload(String baseUrl, String fileName, String textId, String format, String md5, String userId) {
        try {
            String url = baseUrl + "/trans/append_upload/upload?";

            Map<String, String> params = new TreeMap<String, String>();

            params.put(PARAM_NAME_APPKEY, appkey);
            params.put(PARAM_NAME_USER, userId);
            params.put(PARAM_NAME_TEXTID, textId);
            params.put(PARAM_NAME_MD5, md5);
            params.put(PARAM_NAME_AUDIOTYPE, format);
            params.put(PARAM_NAME_TIMESTAMP, System.currentTimeMillis() + "");
            params.put(PARAM_NAME_SIGNATURE, buildSign(secret, params));

            String reqUrl = buildReqUrl(url, params);

            byte[] data = SystemUtils.httpPost(reqUrl, SystemUtils.stream2Bytes(new FileInputStream(fileName)), null, true);

            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String appendUploadFinish(String baseUrl, String textId, String format, String domain, String md5, String userId) {
        try {
            String url = baseUrl + "/trans/transcribe?";

            Map<String, String> params = new TreeMap<String, String>();

            params.put(PARAM_NAME_APPKEY, appkey);
            params.put(PARAM_NAME_USER, userId);
            params.put(PARAM_NAME_TEXTID, textId);
            params.put(PARAM_NAME_AUDIOTYPE, format);
            params.put(PARAM_NAME_DOMAIN, "other");
            params.put(PARAM_NAME_MD5, md5);
            params.put(PARAM_NAME_PUNCTION, "beauty");
            params.put(PARAM_NAME_NUM_CONVERT, "true");
            params.put(PARAM_NAME_TIMESTAMP, System.currentTimeMillis() + "");
            params.put(PARAM_NAME_SIGNATURE, buildSign(secret, params));

            String reqUrl = buildReqUrl(url, params);
            byte[] data = SystemUtils.httpPost(reqUrl, null, null, true);

            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String appendUploadGetStatus(String baseUrl, String textid) {
        try {
            String url = baseUrl + "/trans/append_upload/status?";

            Map<String, String> params = new TreeMap<String, String>();

            params.put(PARAM_NAME_APPKEY, appkey);
            params.put(PARAM_NAME_TEXTID, textid);
            params.put(PARAM_NAME_TIMESTAMP, System.currentTimeMillis() + "");
            params.put(PARAM_NAME_SIGNATURE, buildSign(secret, params));

            String reqUrl = buildReqUrl(url, params);

            byte[] data = SystemUtils.httpPost(reqUrl, null, null, true);

            return new String(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建请求的 url
     *
     * @param url
     * @param params
     * @return
     */
    public static String buildReqUrl(String url, Map<String, String> params) {
        StringBuffer ret = new StringBuffer(url);

        for (String key : params.keySet()) {
            ret.append(key).append('=').append(params.get(key)).append('&');
        }

        if (ret.charAt(ret.length() - 1) == '&') {
            return ret.substring(0, ret.length() - 1);
        }

        return ret.toString();
    }

    private static List<String> getListFromDirectory(File file) {
        List<String> list = new ArrayList<String>();
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                list.add(files[i].getPath());
            }
        }
        return list;
    }

    /**
     * 签名校验
     *
     * @param
     * @param params
     * @throws
     */
    public static String buildSign(String secret, Map<String, String> params) {
        StringBuffer signStr = new StringBuffer(secret);
        for (Entry<String, String> entry : params.entrySet()) {
            if (!PARAM_NAME_SIGNATURE.equals(entry.getKey())) {
                signStr.append(entry.getValue());
            }
        }

        signStr.append(secret);

        return SystemUtils.encryptSHA1(signStr.toString()).toUpperCase();

    }

    public static final String PARAM_NAME_USER = "userid";

    public static final String PARAM_NAME_AUDIOTYPE = "audiotype";

    public static final String PARAM_NAME_DOMAIN = "domain";

    public static final String PARAM_NAME_APPKEY = "appkey";

    public static final String PARAM_NAME_TIMESTAMP = "timestamp";

    public static final String PARAM_NAME_SIGNATURE = "signature";

    public static final String PARAM_NAME_TEXTID = "task_id";

    public static final String PARAM_NAME_MD5 = "md5";

    public static final String PARAM_NAME_CALL_BACK_URL = "callbackurl";

    public static final String PARAM_NAME_WORD_INFO = "word_info";

    public static final String PARAM_NAME_PUNCTION = "punction";

    public static final String PARAM_NAME_NUM_CONVERT = "num_convert";

}
