package com.microsoft.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wyq
 * @date 2022/5/11
 * @desc
 */
public class TestUnit {
    //    public static final String PAUSE_TIME_REGEX = "[^0-9]";
//    public static final String PAUSE_TIME_REGEX = "^\\d\\.\\d";//不行
    public static final String PAUSE_TIME_REGEX = "[^(0-9).]";
    private static final String ZH_TEXT_SHORT = "北大红楼一楼西头的阅览室，是青年毛泽东曾经工作过的地方。当年，这位操着湖南口音的年轻人，在忙碌地为大家办理借阅手续之余，还通过阅读“迅速地朝着马克思主义的方向发展”。";
    //    private static final String BREAK_REGEX = "\\[停顿(：|:)\\d{2,4}毫秒\\]";
    private static final String BREAK_REGEX = "\\[停顿(：|:)\\d\\.\\d秒\\]";
    private static final Pattern BREAK_PATTERN = Pattern.compile(BREAK_REGEX);
    private static final Pattern PAUSE_TIME_PATTERN = Pattern.compile(PAUSE_TIME_REGEX);

    public static void main(String[] args) {

//        String regex="\\[停顿：\\d{2,4}毫秒\\]";
        String text = "[停顿：5.0秒][停顿:1.5秒]北大红楼一楼西头的阅览室，[停顿:1.0秒]是青年毛泽东曾经工作过的地方。[停顿[停顿：2.0秒]最后";
//        String text = "[停顿：50毫秒][停顿:500毫秒]北大红楼一楼西头的阅览室，1000毫秒][停顿:1500毫秒]是青年毛泽东曾经工作过的地方。[停顿[停顿：2000毫秒]最后";
//        String text = "北大红楼一楼西头的阅览室，是青年毛泽东曾经工作过的地方。";
//        String text = "[停顿:50毫秒]北大红楼一楼西头的阅览室，[停顿：500毫秒]是青年毛泽东[停顿:50毫秒]曾经工作过的地方。[停顿[停顿:50毫秒]";
//        String text = "北大红楼一楼西头的阅览室，[停顿:50ms]是青年毛泽东曾经工作过的地方。";

//        String[] strs = text.split("\\[停顿(：|:)\\d{2,4}毫秒\\]");
        String[] strs = text.split(BREAK_REGEX);
//        String[] strs = text.split("(\\[停顿：\\d{2,4}毫秒\\])|(\\[停顿:\\d{2,4}毫秒\\])");
        System.out.println("size: " + strs.length);
        for (String s : strs) {
            System.out.println(s);
        }

//        Pattern p = Pattern.compile(regex);
        Pattern p = Pattern.compile(BREAK_REGEX);
//        Pattern p = Pattern.compile("(\\[停顿：\\d{2,4}毫秒\\])(\\[停顿:\\d{2,4}毫秒\\])");
//        Pattern p = Pattern.compile("(\\[停顿：\\d{2,4}毫秒\\])|(\\[停顿:\\d{2,4}毫秒\\])");
        Matcher m = p.matcher(text);
        System.out.println("groupCount: " + m.groupCount());
        while (m.find()) {
            System.out.println(m.group());
        }
        System.out.println("-----------");
        List<String> te = getPauseList(text);
        System.out.println(te.size());
        System.out.println(te);

        System.out.println("------------------");
        String s = "qwe1.23werfadsf56";
//        s = s.replaceAll("[^\\d.]+",""); //^\\d+\\.\\d
        s = s.replaceAll("[^\\d.]","");
        System.out.println(s);
    }

    public static List<String> getPauseList(String text) {
        List<String> res = new ArrayList<>();
        Pattern p = Pattern.compile(BREAK_REGEX);
        Matcher m = p.matcher(text);
        System.out.println("groupCount: " + m.groupCount());
        while (m.find()) {
            String ps = m.group();
            res.add(PAUSE_TIME_PATTERN.matcher(ps).replaceAll("").trim() + "s");
//            res.add(ps.substring(ps.length() - 5, ps.length() - 2).trim() + "s");
        }
        return res;
    }
}
