package com.example.blog.service;

import com.example.blog.vo.Result;
import com.example.blog.vo.params.CommentParam;

/**
 * @author hap
 * @date 2022/4/18 11:02
 * @describe
 */
public interface CommentsService {
    Result commentByArticleId(Long id);

    Result createComment(CommentParam commentParam);

}
