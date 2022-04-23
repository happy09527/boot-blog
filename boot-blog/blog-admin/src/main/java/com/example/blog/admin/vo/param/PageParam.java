package com.example.blog.admin.vo.param;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/20 14:49
 * @describe
 */
@Data
public class PageParam {
    private int currentPage;
    private int pageSize;
    private String queryString;
    private int total;
}
