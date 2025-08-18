package com.intern.design.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.intern.design.bean.Article;
import com.intern.design.dto.ArticleDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ArticleService extends IService<Article> {

    void export();
}