package com.intern.design.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intern.design.bean.Article;
import com.intern.design.dto.ArticleDTO;
import com.intern.design.infra.minio.MinioTemplate;
import com.intern.design.mapper.ArticleMapper;
import com.intern.design.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private static final int PAGE_SIZE  = 1000;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);


    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private MinioTemplate minioTemplate;


    @Override
    public void export(){

        LocalDateTime start = YearMonth.now()
                .minusMonths(1)
                .atDay(1)
                .atTime(LocalTime.MIN);

        LocalDateTime end = YearMonth.now()
                .minusMonths(1)
                .atEndOfMonth()
                .atTime(LocalTime.MAX);

        Long count = lambdaQuery()
                .between(Article::getUpdateTime,start,end)
                .count();

        if(count <= 0){
            return;
        }

        int pages = (int) Math.ceil((double) count / PAGE_SIZE);

        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        String lastMonthStr = LocalDateTimeUtil.format(lastMonth,"yyyy-MM");

        for (int i = 1; i < pages; i++) {
            int pageNum = i;
            executorService.execute(()->{
                int cur = (pageNum - 1) * PAGE_SIZE;
                List<Article> articles = articleMapper.selectByLimit(start,end,cur,PAGE_SIZE);
                List<ArticleDTO> articleDTOS = articles.stream()
                        .map(ArticleDTO::from)
                        .collect(Collectors.toList());

                export2Local(articleDTOS,"/Users/guochaojun/Desktop/FileFlow/"
                        + lastMonthStr + "_" + pageNum + ".xlsx");

                export2Oss(articleDTOS,lastMonthStr + "_" + pageNum + ".xlsx");

            });

        }

    }

    public void export2Local(List<ArticleDTO> list, String filepath){
        EasyExcel.write(filepath,ArticleDTO.class)
        .sheet("sheet1").doWrite(list);
    }

    public void export2Oss(List<ArticleDTO> list, String filename){
        //创建字节数输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //使用easyexcel 写入数据到输出流
        EasyExcel.write(outputStream, ArticleDTO.class)
                .sheet("Sheet1")
                .doWrite(list);

        //上传到云存储
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        minioTemplate.uploadFile(filename,inputStream);
    }
}
