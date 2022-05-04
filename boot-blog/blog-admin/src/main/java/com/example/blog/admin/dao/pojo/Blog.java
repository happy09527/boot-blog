package com.example.blog.admin.dao.pojo;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/24 19:42
 * @describe 博客表
 */

@Data
public class Blog {
    private Integer id;
    private Long runtime;
    private String message;
    private String auth;
    private String ip;
}
