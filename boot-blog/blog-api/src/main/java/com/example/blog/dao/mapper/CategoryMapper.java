package com.example.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dao.pojo.Article;
import com.example.blog.dao.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hap
 * @date 2022/4/17 18:10
 * @describe
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
