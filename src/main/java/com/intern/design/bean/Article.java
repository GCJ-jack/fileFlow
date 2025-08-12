package com.intern.design.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.intern.design.enums.ArticleState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("article")
public class Article {

    private Long id;

    private String title;

    private String content;

    private ArticleState state;

    private Long creatorId;
    private Long updaterId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
