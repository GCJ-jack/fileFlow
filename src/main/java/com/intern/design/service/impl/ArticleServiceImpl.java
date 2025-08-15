package com.intern.design.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.intern.design.bean.Article;
import com.intern.design.mapper.ArticleMapper;
import com.intern.design.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


}
