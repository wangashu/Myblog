package com.wangashu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangashu.constants.SystemCanstants;
import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.Article;
import com.wangashu.entity.Category;
import com.wangashu.mapper.ArticleMapper;
import com.wangashu.service.ArticleService;
import com.wangashu.service.CategoryService;
import com.wangashu.utils.BeanCopyUtils;
import com.wangashu.vo.ArticleListDetailVo;
import com.wangashu.vo.ArticleListVo;
import com.wangashu.vo.HotArticleVo;
import com.wangashu.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService{
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //查询已经发布的文章
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        //按照浏览了排序
        queryWrapper.orderByDesc(Article::getViewCount);

        //最多展示10条数据
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

        List<HotArticleVo> list = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(list);

    }

    @Override
    public ResponseResult articleList(Integer pageCurrent,Integer pageSize, Long categoryId) {

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询的categoryId 根据前端如果传 就携带 不传就不卸载
         queryWrapper.eq(categoryId!=null&&categoryId>0,Article::getCategoryId,categoryId);

         // 查询的为已发布
        queryWrapper.eq(Article::getStatus,SystemCanstants.ARTICLE_STATUS_NORMAL);

        //置顶文章需要放在最上面
        queryWrapper.orderByDesc(Article::getIsTop);
        queryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> articlePage = new Page<>(pageCurrent,pageSize);
        page(articlePage, queryWrapper);


        //封装数据
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListVo.class);


        List<Long> collect = articlePage.getRecords().stream().map(article -> article.getCategoryId()).collect(Collectors.toList());
        for (Long catogeryId:collect
        ) {
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                for (ArticleListVo vo : articleListVos) {
                    vo.setCategoryName(categoryName);
                }

            }

        }


        PageVo pageVo = new PageVo(articleListVos,articlePage.getTotal());


        return ResponseResult.okResult(pageVo);
    }

    //更具id查询 文章的详细信息
    @Override
    public ResponseResult articleListDetail(Long id) {
        //根据分类id查询文章
        Article article = getById(id);

        //封装为Vo
        ArticleListDetailVo articleListDetailVo = BeanCopyUtils.copyBean(article, ArticleListDetailVo.class);

        //根据分类id 查询分类名称
        Long categoryId= article.getCategoryId();
        Category category = categoryService.getById(categoryId);

        if (category!=null){
            articleListDetailVo.setCategoryName(category.getName());
        }

        return ResponseResult.okResult(articleListDetailVo);
    }


}
