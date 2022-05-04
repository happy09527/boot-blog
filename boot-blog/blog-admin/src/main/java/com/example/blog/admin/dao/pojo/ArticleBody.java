package com.example.blog.admin.dao.pojo;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/17 17:46
 * @describe
 */
@Data
public class ArticleBody {
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
