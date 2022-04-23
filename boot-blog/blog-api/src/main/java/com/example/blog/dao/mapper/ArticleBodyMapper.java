package com.example.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dao.pojo.Article;
import com.example.blog.dao.pojo.ArticleBody;


import org.apache.ibatis.annotations.Mapper;

/**
 * @author hap
 * @date 2022/4/17 18:09
 * @describe
 */

@Mapper
public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {
}
