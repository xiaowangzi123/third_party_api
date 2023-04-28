package com.onem.demo.utils;

import lombok.Data;

/**
 * @author wyq
 * @date 2022/5/12
 * @desc
 */

@Data
public class JsonFileDto {
    private String website_address;
    private String country;
    private String language;
    private String crawl_date;
    private String title;
    private String url;
    private String content;
    private String website_name;
    private String release_date;
    private String id;
    private int content_length;
}
