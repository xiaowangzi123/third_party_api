package com.onem.service;

import java.util.List;

public interface ToolsService {

    /**
     * 英文文章句子切分
     */
    List<String> contentSegSplit(String content);

    /**
     * 实体标注格式：<ENTITY></ENTITY>
     */
    String entityTag(String content);


    /**
     * 句法标注格式：_
     */
    String syntaxTag(String content) ;

    /**
     * 词性标注格式:_
     */
    String posTagging(String content);
}
