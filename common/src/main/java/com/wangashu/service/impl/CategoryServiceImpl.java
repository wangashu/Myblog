package com.wangashu.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangashu.constants.SystemCanstants;
import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.Article;
import com.wangashu.entity.Category;
import com.wangashu.mapper.CategoryMapper;
import com.wangashu.service.ArticleService;
import com.wangashu.service.CategoryService;
import com.wangashu.utils.BeanCopyUtils;
import com.wangashu.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author     ：王阿书

 * @ DateTime       :2025/8/20 13:40

 * @ Description:

 * @program :

 */


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult categoryList() {
         //查询文章列表 为以发布状态的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);

        List<Article> list = articleService.list(queryWrapper);
        //获取文章的分类id 并且去重
        Set<Long> categoryIds = list.stream().map(Article::getCategoryId).collect(Collectors.toSet());

        //查询分类
        List<Category> categoryList = listByIds(categoryIds);

        //
        List<Category> categories = categoryList.stream().filter(category -> SystemCanstants.CATEGORY_STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
