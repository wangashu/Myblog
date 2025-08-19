package com.wangashu.controller;

import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.Article;
import com.wangashu.service.ArticleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/article")
@RestController
public class ArticleController {


    @Autowired
    private ArticleService articleService;


    @GetMapping("/hotArticleList")
    public  ResponseResult hotArticleList(){

        ResponseResult result=articleService.hotArticleList();

       return result;


    }



}
