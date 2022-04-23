package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.CommentsService;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hap
 * @date 2022/4/18 10:59
 * @describe
 */

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentsService commentsService;

    /**
     * 获取评论
     */
    @GetMapping("/article/{id}")
    @LogAnnotation(URL = "/comments/article/{id}", operator = "获取评论")
    public Result comments(@PathVariable("id") Long id) {
        return commentsService.commentByArticleId(id);
    }

    /**
     * 发布评论
     */
    @PostMapping("/create/change")
    @LogAnnotation(URL = "/comments/create/change", operator = "发布评论")
    public Result createComment(@RequestBody CommentParam commentParam) {
        return commentsService.createComment(commentParam);
    }
}
