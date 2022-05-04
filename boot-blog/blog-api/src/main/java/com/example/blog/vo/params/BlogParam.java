package com.example.blog.vo.params;

import lombok.Data;

/**
 * @author hap
 * @date 2022/5/2 16:21
 * @describe
 */
@Data
public class BlogParam {
    private String id;
    private Long time;
    private String avatar;
    private String content;
    private String nickname;
    private String ip;
}
