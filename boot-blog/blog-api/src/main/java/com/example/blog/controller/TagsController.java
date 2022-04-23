package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.TagService;
import com.example.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hap
 * @date 2022/4/16 21:43
 * @describe 最热标签，热门文章
 */

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    TagService tagService;

    /**
     * 最热标签
     */
    @GetMapping("/hot")
    @LogAnnotation(URL = "/tags/hot", operator = "发布文章")
    public Result hot() {
        int limit = 5;
        return tagService.hots(limit);
    }

    /**
     * 标签列表
     */
    @GetMapping
    @LogAnnotation(URL = "/tags", operator = "标签列表")
    public Result tags() {
        return tagService.tags();
    }

    /**
     * 标签列表
     */
    @GetMapping("/detail")
    @LogAnnotation(URL = "/tags/detail", operator = "标签列表")
    public Result tagsDetail() {
        return tagService.tags();
    }

    /**
     * 单一标签
     */
    @GetMapping("/detail/{id}")
    @LogAnnotation(URL = "/tags/detail/id", operator = "单一标签")
    public Result tagDetailById(@PathVariable("id") Long id) {
        return tagService.tagDetailById(id);
    }
}
