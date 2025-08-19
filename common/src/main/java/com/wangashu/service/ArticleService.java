package com.wangashu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
}
