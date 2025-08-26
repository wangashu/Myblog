package com.wangashu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wangashu.domain.ResponseResult;
import com.wangashu.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2025-08-20 13:33:25
 */
public interface CategoryService  extends IService<Category> {


    ResponseResult categoryList();
}
