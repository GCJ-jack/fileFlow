package com.intern.design.action;


import cn.hutool.extra.spring.SpringUtil;
import com.intern.design.annotation.StatesOnTransition;
import com.intern.design.bean.Article;
import com.intern.design.enums.ArticleState;
import com.intern.design.event.BaseEvent;
import com.intern.design.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@WithStateMachine( name = "stateMachine")
@RequiredArgsConstructor
public class ArticleStateAction {

    private final ArticleService articleService;

    @StatesOnTransition(source = ArticleState.TEMP, target = ArticleState.PROCESSING)
    public void apply(@EventHeaders Map<String, Object> headers){
        Article article = (Article) headers.get("article");
        article.setState(ArticleState.PROCESSING);
        articleService.updateById(article);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo("3218818005g@gmail.com");
        simpleMailMessage.setSubject("文件导出到数据库完成");
        simpleMailMessage.setText("文件导出到数据库完成了, 请及时查收");

        SpringUtil.publishEvent(new BaseEvent<>(simpleMailMessage,this));
    }

    @StatesOnTransition(source = ArticleState.PROCESSING, target = ArticleState.APPROVED)
    public void agree(@EventHeaders Map<String, Object> headers){
        Article article = (Article) headers.get("article");
        article.setState(ArticleState.APPROVED);
        articleService.updateById(article);
    }

    @StatesOnTransition(source = ArticleState.PROCESSING, target = ArticleState.REJECTED)
    public void disagree(@EventHeaders Map<String, Object> headers) {
        Article article = (Article) headers.get("article");
        article.setState(ArticleState.REJECTED);
        articleService.updateById(article);
    }

    @StatesOnTransition(source = {ArticleState.TEMP, ArticleState.REJECTED, ArticleState.APPROVED}, target = ArticleState.TEMP)
    public void edit(@EventHeaders Map<String, Object> headers) {
        Article article = (Article) headers.get("article");
        article.setState(ArticleState.TEMP);

        boolean res = articleService.updateById(article);
        if (!res) {
            throw new RuntimeException("保存失败");
        }
    }
}
