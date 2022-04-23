package com.example.blog.service;

import com.example.blog.dao.pojo.Article;
import com.example.blog.vo.Result;
import com.example.blog.vo.TagVo;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/16 20:35
 * @describe
 */

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    Result tags();

    Result tagDetailById(Long id);
}
