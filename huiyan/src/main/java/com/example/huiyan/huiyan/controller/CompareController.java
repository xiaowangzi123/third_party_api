package com.example.huiyan.huiyan.controller;

import com.example.huiyan.huiyan.service.CompareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wyq
 */
@Slf4j
@RestController
public class CompareController {

    @Resource
    private CompareService compareService;


    @PostMapping("/select/seg/save")
    public String segSave(@RequestParam("jobId") String jobId) {
        log.info("筛选句段：{}", jobId);
        return compareService.selectSegSave(jobId);
    }

    @PostMapping("/cut/audio/slice")
    public String cutAudioSlice(@RequestParam("jobId") String jobId) {
        log.info("切割音频：{}", jobId);
        return compareService.cutAudioSlice(jobId);
    }

    @PostMapping("/huiyan/asr")
    public String huiyanAsr(@RequestParam("jobId") String jobId) {
        log.info("huiyan asr：{}", jobId);
        return compareService.huiyanAsr(jobId);
    }

    @PostMapping("/seg/export")
    public void segExport(HttpServletResponse response, @RequestParam("jobId") String jobId) {
        log.info("句段导出：{}", jobId);
        compareService.exportSeg(response, jobId);
    }

}
