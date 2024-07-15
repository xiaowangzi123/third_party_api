package com.example.huiyan.huiyan.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wyq
 * @date 2024/7/12
 * @desc
 */
public class StringTools {

    public static void main(String[] args) {
        String s1 = "Добрый день";
        String s2 = "добрый день";
        System.out.println(formatSrt(s1));

        String s11 = formatSrt(s1).toLowerCase();
        String s22 = formatSrt(s2).toLowerCase();
        for (int i = 0; i < Math.min(s11.length(), s22.length()); i++) {
            System.out.println(s11.charAt(i) + " " + s22.charAt(i));
            System.out.println(s11.charAt(i) == s22.charAt(i));
        }
        System.out.println(isSame(s1, s2));
    }

    public static boolean isSame(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return false;
        }
        return s1.toLowerCase().equalsIgnoreCase(s2.toLowerCase());
//        return Objects.equals(formatSrt(s1), formatSrt(s2));
    }

    // 标准化字符串（去除前后空格和末尾标点）
    public static String formatSrt(String str) {
        // 去除前后空格
        String trimmed = str.trim();
        // 去除末尾标点
        return trimmed.replaceAll("[\\p{Punct}\\s]+$", "");
    }
}
