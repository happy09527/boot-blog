package com.example.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hap
 * @date 2022/4/18 11:05
 * @describe
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
