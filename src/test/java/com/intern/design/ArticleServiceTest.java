package com.intern.design;

import com.intern.design.bean.Article;
import com.intern.design.enums.ArticleState;
import com.intern.design.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = com.intern.design.Main.class)
public class ArticleServiceTest {

    @Resource
    private ArticleService articleService;

    @Test
    public void createArticleData(){
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Article article = Article.builder()
                    .title("标题 " + UUID.randomUUID().toString().substring(0,8))
                    .content("内容 " + UUID.randomUUID())
                    .state(ArticleState.TEMP)
                    .creatorId(1L)
                    .updaterId(1L)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            list.add(article);
        }

        articleService.saveBatch(list);
    }
}
