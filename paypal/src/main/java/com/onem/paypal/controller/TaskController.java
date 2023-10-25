package com.onem.paypal.controller;

import com.onem.paypal.task.ScheduleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    private final ScheduleTask scheduleTask;

    @Autowired
    public TaskController(ScheduleTask scheduleTask) {
        this.scheduleTask = scheduleTask;
    }

    @GetMapping("/info")
    public String getCron(){
        log.info("---获取表达式----");
        return scheduleTask.getCron();
    }


    @GetMapping("/updateCron")
    public String updateCron(String cron) {
        log.info("1---->{}", scheduleTask.getCron());

        log.info("new cron :{}", cron);
        scheduleTask.setCron(cron);

        log.info("2---->{}", scheduleTask.getCron());

        return "ok";
    }


}
