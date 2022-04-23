package com.example.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.blog.controller.ArticleController;
import com.example.blog.dao.mapper.ArticleMapper;
import com.example.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author hap
 * @date 2022/4/17 22:15
 * @describe 引入多线程
 */

@Component
public class ThreadService {
    /**
     * 创建线程任务来更新浏览次数
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        Integer viewCount = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCount + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        updateWrapper.eq(Article::getViewCounts, viewCount);
        articleMapper.update(articleUpdate,updateWrapper);
    }
}
