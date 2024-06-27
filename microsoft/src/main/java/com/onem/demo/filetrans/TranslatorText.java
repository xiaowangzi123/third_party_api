package com.onem.demo.filetrans;


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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class TranslatorText {
    private static String key = "548c2913c38e4bbe840380c9067a5282";
    private static String location = "chinaeast2";


    OkHttpClient client = new OkHttpClient();

    public String Post() throws Exception {
        MediaType mediaType = MediaType.parse("application/json");

        List<String> texts = new ArrayList<>();
        texts.add("中文翻译");
        texts.add("印地语翻译");
        JSONArray jsonArray = new JSONArray();

        for (String text : texts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Text", text);
            jsonArray.put(jsonObject);
        }
        /*RequestBody body = RequestBody.create(mediaType,
                "[{\"Text\": \"中文翻译\"}," +
                        "{\"Text\": \"印地语翻译\"}]");*/
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

    // This function prettifies the json response.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static void main(String[] args) throws Exception {
        try {
            TranslatorText translateRequest = new TranslatorText();
            String response = translateRequest.Post();
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
                // 遍历translations JSONArray中的每个JSONObject
                /*for (int j = 0; j < translationsArray.length(); j++) {
                    JSONObject translationObject = translationsArray.getJSONObject(j);
                    String toValue = translationObject.getString("text");
                    System.out.println(toValue);
                }*/
            }

            /*List<MicrosoftTransVo> microsoftTransVoList = JSONObject.parseArray(response, MicrosoftTransVo.class);
            for (MicrosoftTransVo transVo: microsoftTransVoList){
                System.out.println(transVo);
                String tgtText = transVo.getTranslations().get(0).getTo();
            }*/

        } catch (Exception e) {
            System.out.println(e);
        }
        /*String folderPath = "D:\\translation\\王运庆";
        String foldPath = "D:\\translation\\王运庆翻译\\";
        List<String> paths = getPaths(folderPath);
        System.out.println(paths);
        for (String excelPath : paths) {
            List<ExcelDto> excelDtos = ReadExcelUtils.readExcel2Bean(Files.newInputStream(new File(excelPath).toPath()), FilenameUtils.getName(excelPath), ExcelDto.class, 0);
            System.out.println(excelPath);
            System.out.println(excelDtos.size());
            System.out.println(excelDtos);

            List<ExcelVo> dataList = new ArrayList<>();
            for (ExcelDto excelDto :excelDtos){
                ExcelVo vo = new ExcelVo();
                vo.setSrcText(excelDto.getSrcText());
                vo.setTgtText(excelDto.getSrcText());
                dataList.add(vo);
            }

            String outPath = foldPath + FilenameUtils.getName(excelPath);
            EasyExcel.write(outPath, ExcelVo.class)
                    .sheet("sheet")
                    .head(Collections.emptyList())
                    .doWrite(dataList);

        }*/
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
