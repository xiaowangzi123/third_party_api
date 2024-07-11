package com.example.huiyan.huiyan.controller;

import com.example.huiyan.huiyan.entity.table.SrcLangSeg;
import com.example.huiyan.huiyan.service.SrcLangSegService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    // http://127.0.0.1:8080/javabeat/somewords
    @RequestMapping(value = "/javabeat/{regexp1:[a-z-]+}", method = RequestMethod.GET)
    @ResponseBody
    public String getRegExp(@PathVariable("regexp1") String regexp1) {
        return "URI Part : " + regexp1;
    }
}
