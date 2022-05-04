package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.BlogService;
import com.example.blog.vo.params.BlogParam;
import com.example.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hap
 * @date 2022/4/24 19:47
 * @describe
 */

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    /**
     * 获取运行时间戳
     * @return
     */
    @GetMapping("/blog/time")
    @LogAnnotation(URL = "/blog/time", operator = "获取运行时间")
    public Result getTime() {
        return blogService.getTime();
    }
    /**
     * 获取留言信息
     */
    @GetMapping("/message/detail")
    @LogAnnotation(URL = "/message/detail",operator = "获取留言信息")
    public Result getMessage(){
        return blogService.getMessage();
    }

    /**
     * 发布留言
     */
    @PostMapping("/message/publish")
    @LogAnnotation(URL = "/message/publish",operator = "获取留言信息")
    public Result publishMessage(@RequestBody BlogParam blogParam){
        return blogService.publishMessage(blogParam);
    }
}
