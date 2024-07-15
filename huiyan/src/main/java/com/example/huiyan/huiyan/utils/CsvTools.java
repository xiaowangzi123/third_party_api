package com.example.huiyan.huiyan.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author wyq
 * @date 2024/7/15
 * @desc
 */
public class CsvTools {

    public static void create(String filePath, String delimiter, List<List<String>> data) {
        if(StringUtils.isBlank(delimiter)){
            delimiter = "\t";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (List<String> row : data) {
                // 将当前行的所有元素用制表符连接起来
                String rowString = String.join(delimiter, row);
                // 写入行，并添加换行符
                writer.write(rowString + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
