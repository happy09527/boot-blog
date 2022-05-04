package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.common.cache.Cache;
import com.example.blog.service.ArticleService;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.ArticleParam;
import com.example.blog.vo.params.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hap
 * @date 2022/4/16 19:02
 * @describe 主页的文章消息
 */
@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 首页文章列表
     */

    @PostMapping
    @LogAnnotation(URL = "/articles", operator = "获取文章列表")
    @Cache(expire = 5 * 60 * 1000, name = "list_article")
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页，最热文章
     */
    @PostMapping("/hot")
    @LogAnnotation(URL = "/articles/hot", operator = "获取最热文章")
    @Cache(expire = 5 * 60 * 1000, name = "hot_article")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页，最新文章
     */
    @PostMapping("/new")
    @LogAnnotation(URL = "/articles/new", operator = "获取最新文章")
    @Cache(expire = 5 * 60 * 1000, name = "new_articles")
    public Result newArticles() {
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     */
    @PostMapping("/listArchives")
    @LogAnnotation(URL = "/articles/listArchives", operator = "文章归档")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 文章详情页展示
     */
    @PostMapping("/view/{id}")
    @LogAnnotation(URL = "/articles/view/id", operator = "文章详情页")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 发布文章
     */
    @PostMapping("/publish")
    @LogAnnotation(URL = "/articles/publish", operator = "发布文章")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }

    /**
     * 编辑修改文章
     */
    @PostMapping("/edit")
    @LogAnnotation(URL = "/article/edit", operator = "修改文章")
    public Result edit(@RequestBody ArticleParam articleParam) {
        return articleService.edit(articleParam);
    }
    /**
     * 删除文章
     */
    @PostMapping("/delete")
    @LogAnnotation(URL = "/article/delete" ,operator = "删除文章")
    public Result delete(@RequestHeader("Authorization") String token,
                         @RequestBody String articleId){
        return articleService.delete(token,articleId);
    }

    /**
     * 搜索文章
     */
    @PostMapping("/search")
    @LogAnnotation(URL = "/articles/search",operator = "搜索文章")
    public Result search(@RequestBody ArticleParam articleParam){
        log.info(articleParam+"aaaaaaaaaa");
        return articleService.search(articleParam.getTitle());
    }
}