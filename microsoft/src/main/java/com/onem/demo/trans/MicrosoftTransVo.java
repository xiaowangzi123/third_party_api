package com.onem.demo.trans;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MicrosoftTransVo {
    private List<TranslationInfo> translations;
    @Data
    @ToString
   public static class TranslationInfo{
        private String text;
        private String to;
    }
}
