package com.example.blog.dao.pojo;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/18 20:08
 * @describe
 */

@Data
public class ArticleTag {
    private Long id;
    private Long articleId;
    private Long tagId;
}
