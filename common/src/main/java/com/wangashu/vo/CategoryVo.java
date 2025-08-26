package com.wangashu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {

    private Long id;
    /**
     * 标题
     */

    private  String name;

}
