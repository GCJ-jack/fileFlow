package com.intern.design.task;

import com.intern.design.service.ArticleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TaskService {
    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void exportTask() {
        articleService.export();
    }
}