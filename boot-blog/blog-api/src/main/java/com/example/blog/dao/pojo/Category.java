package com.example.blog.dao.pojo;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/17 17:47
 * @describe
 */
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
