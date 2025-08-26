package com.wangashu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDetailVo {


    private Long id;
    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 所属分类Id
     */
    private Long categoryId;
    /**
     * 所属分类Name
     */
    private String categoryName;
    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 所属分类content
     */
    private String content;

    /**
     * 访问量
     */
    private Long viewCount;


    private Date createTime;


}
