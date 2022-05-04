package com.example.blog.service;

import com.example.blog.vo.Result;
import com.example.blog.vo.params.ArticleParam;
import com.example.blog.vo.params.PageParams;

/**
 * @author hap
 * @date 2022/4/16 19:16
 * @describe
 */
public interface ArticleService {
     Result listArticle(PageParams pageParams);

     Result hotArticle(int limit);

     Result newArticles(int limit);

     Result listArchives();

    Result findArticleById(Long articleId);

    Result publish(ArticleParam articleParam);

    Result edit(ArticleParam articleParam);

    Result delete(String token,String articleId);

    Result search(String title);
}
