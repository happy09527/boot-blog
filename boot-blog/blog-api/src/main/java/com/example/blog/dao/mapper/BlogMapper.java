package com.example.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dao.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hap
 * @date 2022/4/24 19:46
 * @describe
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
}
