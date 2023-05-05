package com.cyx.point.task;

import com.cyx.point.service.AddPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

//清除防重表的定时任务
@Component
public class DumpPointAddDataTask {
    @Autowired
    AddPointService addPointService;

    @Scheduled(cron = "0 0 0/25 * * *") // 每小时执行一次
    public void act() {
        // 执行具体的任务逻辑
        addPointService.delete(new Date());
    }

}


