package com.example.blog.service;

import com.example.blog.vo.params.BlogParam;
import com.example.blog.vo.Result;

/**
 * @author hap
 * @date 2022/4/24 19:56
 * @describe
 */
public interface BlogService {
    Result getTime();

    Result getMessage();

    Result publishMessage(BlogParam blogParam);
}
