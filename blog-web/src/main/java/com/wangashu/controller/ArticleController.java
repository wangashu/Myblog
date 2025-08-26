package com.wangashu.controller;

import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.Article;
import com.wangashu.service.ArticleService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //分页查询文章
    @GetMapping("/articleList")
    public  ResponseResult articleList( Integer pageNum, Integer pageSize, Long categoryId){

        return   articleService.articleList(pageNum,pageSize,categoryId);
    }


   //根据分页id 查询文章的详细信息
    @GetMapping("/{id}")
    public  ResponseResult articleListDetail(@PathVariable("id") Long id){

        return  articleService.articleListDetail(id);
    }



}
