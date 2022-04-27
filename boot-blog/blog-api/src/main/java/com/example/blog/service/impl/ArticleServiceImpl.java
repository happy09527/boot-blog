package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.controller.ArticleController;
import com.example.blog.dao.dos.Archives;
import com.example.blog.dao.mapper.*;
import com.example.blog.dao.pojo.*;
import com.example.blog.service.*;
import com.example.blog.utils.CacheUtils;
import com.example.blog.utils.UserThreadLocal;
import com.example.blog.vo.*;
import com.example.blog.vo.params.ArticleParam;
import com.example.blog.vo.params.PageParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author hap
 * @date 2022/4/16 19:19
 * @describe 业务层
 */
@Slf4j
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    TagService tagService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    ArticleBodyMapper articleBodyMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ThreadService threadService;
    @Autowired
    ArticleTagMapper articleTagMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    LoginService loginService;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    CacheUtils cacheUtils;

    /**
     * 分页查询article数据库的表，得到结果
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());

        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(), true, true));
    }

    /**
     * 分页查询article数据库的表，得到结果
     */
//    @Override
//    public Result listArticle(PageParams pageParams) {
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if (pageParams.getCategoryId() != null) {
//            //and category_id=#{categoryId}
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if(pageParams.getTagId()!=null) {
//            //加入标签条件查询
//            //article表中并没有tag字段 一篇文章有多个标签
//            //article_tog article_id 1：n tag_id
//            //我们需要利用一个全新的属于文章标签的queryWrapper将这篇文章的article_Tag查出来，保存到一个list当中。
//            // 然后再根据queryWrapper的in方法选择我们需要的标签即可。
//
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleTags.size() > 0) {
//                // and id in(1,2,3)
//                queryWrapper.in(Article::getId, articleIdList);
//            }
//        }
//        queryWrapper.orderByDesc(Article::getWeight);//置顶排序
//
//        queryWrapper.orderByDesc(Article::getCreateDate);//排序
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//
//        List<Article> articleList = articlePage.getRecords();
//        List<ArticleVo> articleVoList = copyList(articleList, true, true);
//        return Result.success(articleVoList);
//    }

    /**
     * 首页 最热文章
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 最新文章
     */
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 文章归档
     */
    @Override
    public Result listArchives() {
        List<Archives> archives = articleMapper.listArchives();
        return Result.success(archives);
    }

    /**
     * 通过id查询文章，返回给前端，同时要增加浏览次数
     */
    @Override
    public Result findArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        Long authorId = article.getAuthorId(); //通过用户id拿到用户信息，之后返回文章信息时也要返回用户avatar
        UserVo userVo = sysUserService.findUserVoById(authorId);
        ArticleVo articleVo = copy(article, true, true, true, true);
        articleVo.setAuthorAvatar(userVo.getAvatar());
        articleVo.setAuthorId(String.valueOf(authorId));
//        articleVo.setCategory();
        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }


    /**
     * 发布文章
     * 将articleParam转为article对象，存入article表
     * 要存入articleTag、 ArticleBody
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.valueOf(articleParam.getCategory().getId()));
        article.setBodyId(-1L);
        articleMapper.insert(article);

        List<TagVo> tags = articleParam.getTags();

        if (tags != null) {
            for (TagVo tagVo : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.valueOf(tagVo.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }

        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        cacheUtils.deleteCache("list_article*");
        return Result.success(articleVo);
    }

    /**
     * 修改文章
     */
    @Override
    public Result edit(ArticleParam articleParam) {
        Long articleId = articleParam.getId();
        Article articleDB = articleMapper.selectById(articleId);
        articleDB.setTitle(articleParam.getTitle());
        articleDB.setCategoryId(Long.valueOf(articleParam.getCategory().getId()));
        articleDB.setSummary(articleParam.getSummary());
        articleMapper.updateById(articleDB);

        ArticleBody articleBodyDB = articleBodyMapper.selectById(articleDB.getBodyId());
        articleBodyDB.setContent(articleParam.getBody().getContent());
        articleBodyDB.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.updateById(articleBodyDB);

        List<TagVo> tags = articleParam.getTags();
        articleTagMapper.deleteByArticleId(articleId);
        if (tags != null) {
            for (TagVo tagVo : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.valueOf(tagVo.getId()));
                articleTag.setArticleId(articleId);
                articleTagMapper.insert(articleTag);
            }
        }
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(articleDB.getId()));
        cacheUtils.deleteCache("list_article*");

        return Result.success(articleVo);
    }

    /**
     * 删除文章
     */
    @Override
    public Result delete(String token, String id) {
        Long articleId = Long.valueOf(id.substring(0, id.length() - 1));
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        articleMapper.deleteById(articleId);
        articleBodyMapper.deleteByArticleId(articleId);
        articleTagMapper.deleteByArticleId(articleId);
        commentMapper.deleteByArticleId(articleId);
        cacheUtils.deleteCache("list_article*");
        return Result.success(token);
    }

    /**
     * 将entity对象集合转换为vo对象集合
     */
    private List<ArticleVo> copyList(List<Article> articleList, boolean isTags, boolean isAuthor) {
        List<ArticleVo> list = new ArrayList<>();
        for (Article article : articleList) {
            ArticleVo articleVo = copy(article, isTags, isAuthor, false, false);
            articleVo.setId(String.valueOf(article.getId()));
            list.add(articleVo);
        }
        return list;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    /**
     * 将entity对象转换 为vo对象
     */
    private ArticleVo copy(Article article, boolean isTags, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isAuthor) {
            articleVo.setAuthor(sysUserService.findUserById(article.getAuthorId()).getNickname());
        }
        if (isTags) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    /**
     * 通过id查找分类
     */
    private CategoryVo findCategory(Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }

    /**
     * 通过id查文章内容
     */
    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
