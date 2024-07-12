package com.example.huiyan.huiyan.controller;

import com.example.huiyan.huiyan.entity.table.SrcLangSeg;
import com.example.huiyan.huiyan.service.SrcLangSegService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@RestController
public class PathVariableController {

    @Resource
    private SrcLangSegService srcLangSegService;

    @RequestMapping(value = "/user/test", method = RequestMethod.GET)
    public String getLogin() {
        List<SrcLangSeg> list = srcLangSegService.list();
        return "size : " + list.size();
    }


    @RequestMapping(value = "/async/test", method = RequestMethod.GET)
    @ResponseBody
    public String getRegExp(@RequestParam("times") Integer times) {
        for (int i = 0; i < times; i++) {
            srcLangSegService.asyncTest();
        }
        return "success";
    }
}
