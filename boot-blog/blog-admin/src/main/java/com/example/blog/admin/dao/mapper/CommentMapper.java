package com.example.blog.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.admin.dao.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hap
 * @date 2022/4/26 20:28
 * @describe
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
