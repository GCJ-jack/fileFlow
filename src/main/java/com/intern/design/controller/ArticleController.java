package com.intern.design.controller;

import com.intern.design.bean.Article;
import com.intern.design.enums.ArticleState;
import com.intern.design.enums.StateEvent;
import com.intern.design.handler.ArticleStateHandler;
import com.intern.design.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    @Autowired
    private final ArticleService articleService;

    @GetMapping("/{id}")
    public Article get(@PathVariable Long id){
        return articleService.getById(id);
    }

    @PostMapping("/save")
    public Article save(@RequestBody Article articleReq) {
        Article article = Article.builder().title(articleReq.getTitle())
                .content(articleReq.getContent())
                .state(ArticleState.TEMP).build();

        articleService.save(article);

        return articleService.getById(article.getId());
    }

    @PostMapping("/update")
    public Article update(@RequestBody Article articleReq){
        Article article = articleService.getById(articleReq.getId());
        article.setTitle(articleReq.getTitle());
        article.setContent(articleReq.getContent());

        ArticleStateHandler.handleArticleState(article, StateEvent.EDIT);

        return articleService.getById(articleReq.getId());
    }

    @PostMapping("/publish/{id}")
    public Article publish(@PathVariable Long id) {
        Article article = articleService.getById(id);

        if(Objects.isNull(article)){
            throw new RuntimeException("article does not exist");
        }
        ArticleStateHandler.handleArticleState(article,StateEvent.APPLY);
        return article;
    }

    @PostMapping("/applyResult")
    public Article applyResult(@RequestParam Long articleId,
                               @RequestParam Boolean agree){

        Article article = articleService.getById(articleId);

        if(Objects.isNull(article)){
            throw new RuntimeException("article does not exist");
        }

        if(agree){
            ArticleStateHandler.handleArticleState(article,StateEvent.AGREE);
        }else{
            ArticleStateHandler.handleArticleState(article,StateEvent.DISAGREE);
        }

        return article;
    }

    @PostMapping("/last_month")
    public String exportLastMonth(){
        articleService.export();
        return "上个月数据导出";
    }


}
