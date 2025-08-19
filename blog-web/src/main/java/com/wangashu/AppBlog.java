package com.wangashu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wangashu.mapper")
public class AppBlog {
    public static void main(String[] args) {
        SpringApplication.run(AppBlog.class,args);
    }
}
