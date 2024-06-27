package com.onem.demo.filetrans;


import com.alibaba.excel.EasyExcel;
import com.cloudtranslation.transwai.common.core.utils.file.ReadExcelUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TranslatorText2 {
    private static String key = "548c2913c38e4bbe840380c9067a5282";
    private static String location = "chinaeast2";


    OkHttpClient client = new OkHttpClient();

    public String Post(List<String> texts) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");

        JSONArray jsonArray = new JSONArray();

        for (String text : texts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Text", getFormat(text));
            jsonArray.put(jsonObject);
        }
        RequestBody body = RequestBody.create(mediaType, jsonArray.toString());
        HttpUrl httpUrl = new HttpUrl.Builder().scheme("https").host("api.translator.azure.cn")
                .addPathSegment("translate")
                .addQueryParameter("api-version", "3.0")
                .addQueryParameter("from", "zh-cn")
                .addQueryParameter("to", "hi").build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                // location required if you're using a multi-service or regional (not global) resource.
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String getFormat(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        return text.replaceAll("\\s+", "").trim();
    }

    // This function prettifies the json response.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static void main(String[] args) throws Exception {
        try {
            String folderPath = "D:\\translation\\刘巧";
            String foldPath = "D:\\translation\\刘巧翻译\\";
            List<String> paths = getPaths(folderPath);
            System.out.println(paths);
            for (String excelPath : paths) {
                List<ExcelDto> excelDtos = ReadExcelUtils.readExcel2Bean(Files.newInputStream(new File(excelPath).toPath()), FilenameUtils.getName(excelPath), ExcelDto.class, 0);
                System.out.println(excelPath);
                System.out.println(excelDtos.size());
                Thread.sleep(2000);
                List<String> tgtList = getTgtList(excelDtos.stream().map(ExcelDto::getSrcText).collect(Collectors.toList()));
                if (excelDtos.size() != tgtList.size()) {
                    continue;
                }

                List<ExcelVo> dataList = new ArrayList<>();
                for (int i = 0; i < excelDtos.size(); i++) {
                    ExcelVo vo = new ExcelVo();
                    vo.setSrcText(excelDtos.get(i).getSrcText());
                    vo.setTgtText(tgtList.get(i));
                    dataList.add(vo);
                }

                String outPath = foldPath + FilenameUtils.getName(excelPath);
                EasyExcel.write(outPath, ExcelVo.class)
                        .sheet("sheet")
                        .head(Collections.emptyList())
                        .doWrite(dataList);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static List<String> getTgtList(List<String> srcList) {
        List<String> tgtList = new ArrayList<>();

        try {
            TranslatorText2 translateRequest = new TranslatorText2();
            String response = translateRequest.Post(srcList);
            System.out.println(prettify(response));
            // 解析JSON字符串为JSONArray
            JSONArray jsonArray = new JSONArray(response);

            // 遍历JSONArray中的每个JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // 获取translations JSONArray
                JSONArray translationsArray = jsonObject.getJSONArray("translations");
                String tgtText = translationsArray.getJSONObject(0).getString("text");
                System.out.println(tgtText);
                tgtList.add(tgtText);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tgtList;
    }


    public static List<String> getPaths(String folderPath) {
        List<String> excelPaths = new ArrayList<>();

        // 创建文件对象
        File folder = new File(folderPath);

        // 检查文件夹是否存在以及是否是目录
        if (folder.exists() && folder.isDirectory()) {
            // 使用FilenameFilter来过滤Excel文件
            FilenameFilter excelFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".xlsx");
                }
            };

            // 获取所有Excel文件
            File[] excelFiles = folder.listFiles(excelFilter);

            // 打印所有Excel文件的名称
            if (excelFiles != null && excelFiles.length > 0) {
                for (File file : excelFiles) {
                    System.out.println("Found Excel file: " + file.getName());
                    excelPaths.add(file.getAbsolutePath());
                }
            } else {
                System.out.println("No Excel files found in the specified folder.");
            }
        } else {
            System.out.println("The specified folder does not exist or is not a directory.");
        }
        return excelPaths;
    }
}
