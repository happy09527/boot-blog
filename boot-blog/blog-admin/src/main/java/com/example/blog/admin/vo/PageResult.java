package com.example.blog.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/20 15:05
 * @describe
 */
@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;
}