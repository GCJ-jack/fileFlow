package com.intern.design.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intern.design.bean.Article;
import com.intern.design.mapper.ArticleMapper;
import com.intern.design.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private static final int PAGE_SIZE  = 1000;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);


    @Resource
    private ArticleMapper articleMapper;

//    @Resource
//    private MinioTemplate minioTemplate;

}
