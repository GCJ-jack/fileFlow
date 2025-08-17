package com.intern.design.dto;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.intern.design.bean.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("状态")
    private String state;

    @ExcelProperty("创建时间")
    private String createTime;

    @ExcelProperty("修改时间")
    private String updateTime;

    public static ArticleDTO from(Article article){
        return ArticleDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .state(article.getState().getDesc())
                .createTime(LocalDateTimeUtil.format(article.getCreateTime(),"yyyy-MM-dd HH:mm:ss"))
                .updateTime(LocalDateTimeUtil.format(article.getUpdateTime(),"yyyy-MM-dd HH:mm:ss"))
                .build();
    }
}
