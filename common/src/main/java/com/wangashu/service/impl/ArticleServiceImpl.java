package com.wangashu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.Article;
import com.wangashu.mapper.ArticleMapper;
import com.wangashu.service.ArticleService;
import com.wangashu.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService{
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //查询已经发布的文章
        queryWrapper.eq(Article::getStatus,0);
        //按照浏览了排序
        queryWrapper.orderByDesc(Article::getViewCount);

        //最多展示10条数据
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
        //需要把前端需要的 articles 属性封装到vo
        ArrayList<HotArticleVo> list = new ArrayList<>();

        for (Article article:articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article,vo);
            list.add(vo);

        }

        return ResponseResult.okResult(list);
    }
}
