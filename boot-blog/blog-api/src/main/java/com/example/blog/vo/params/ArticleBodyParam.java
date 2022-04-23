package com.example.blog.vo.params;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/18 19:50
 * @describe 从前端接收的参数
 */
@Data
public class ArticleBodyParam {

    private String content;

    private String contentHtml;
}
