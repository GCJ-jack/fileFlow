package com.intern.design.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.intern.design.bean.Article;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectByLimit(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize);
}
