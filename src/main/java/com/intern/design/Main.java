package com.intern.design;


import cn.hutool.extra.spring.EnableSpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.intern.design.mapper")
@EnableSpringUtil
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}
