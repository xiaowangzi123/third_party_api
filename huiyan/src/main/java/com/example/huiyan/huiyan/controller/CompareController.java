package com.example.huiyan.huiyan.controller;

import com.example.huiyan.huiyan.service.CompareService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 */
@RestController
public class CompareController {

    @Resource
    private CompareService compareService;


    @PostMapping("/selece/seg/save")
    public String segSave(@RequestParam("jobId") String jobId) {

        return compareService.selectSegSave(jobId);

    }
}
