package com.example.blog.vo;

import java.util.List;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/16 19:50
 * @describe  view object，对应web页面文章消息
 */


@Data
public class ArticleVo {
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private String authorId;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo  category;

    private String authorAvatar;
}