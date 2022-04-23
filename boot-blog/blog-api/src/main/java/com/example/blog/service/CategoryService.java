package com.example.blog.service;

import com.example.blog.vo.CategoryVo;
import com.example.blog.vo.Result;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/17 18:13
 * @describe
 */
public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findDetailById(Long id);
}
