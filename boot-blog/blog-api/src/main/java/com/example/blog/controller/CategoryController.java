package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.CategoryService;
import com.example.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hap
 * @date 2022/4/18 15:57
 * @describe 文章分类页
 */

@RestController
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 获取全部分类
     */
    @GetMapping
    @LogAnnotation(URL = "categorys", operator = "获取分类详情")
    public Result categories() {
        return categoryService.findAll();
    }

    /**
     * 获取分类详情
     */
    @GetMapping("/detail")
    @LogAnnotation(URL = "categorys/detail", operator = "获取分类详情")
    public Result categoriesDetail() {
        return categoryService.findAll();
    }

    /**
     * 获取单一分类
     */
    @GetMapping("/detail/{id}")
    @LogAnnotation(URL = "categorys/detail/id", operator = "获取单一分类")
    public Result categoryDetailById(@PathVariable("id") Long id) {
        return categoryService.findDetailById(id);
    }
}
