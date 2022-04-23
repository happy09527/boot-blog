package com.example.blog.vo.params;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/18 13:50
 * @describe
 */

@Data
public class CommentParam {
    private Long articleId;
    private String content;
    private Long parent;
    private Long toUserId;
}
