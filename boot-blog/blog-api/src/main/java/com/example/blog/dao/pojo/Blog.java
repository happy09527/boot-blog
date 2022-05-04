package com.example.blog.dao.pojo;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/24 19:42
 * @describe 博客表
 */

@Data
public class Blog {
    private Long id;
    private Long time;
    private String content;
    private String auth;
    private String ip;
    private String avatar;
}
