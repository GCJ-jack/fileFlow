package com.intern.design.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@TableName("user")
public class User {
    private Long id;
    private String username;
    private String name;
}
