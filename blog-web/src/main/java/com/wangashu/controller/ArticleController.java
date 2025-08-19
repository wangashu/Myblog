package com.wangashu.controller;

import com.wangashu.entity.Article;
import com.wangashu.service.ArticleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping
@RestController
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    //更具id查询
    @GetMapping("/test")
    public List<Article> test(){
        return articleService.list();
    }



}
