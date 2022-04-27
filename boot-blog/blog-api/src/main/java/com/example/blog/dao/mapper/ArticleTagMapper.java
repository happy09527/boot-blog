package com.example.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dao.pojo.ArticleTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hap
 * @date 2022/4/18 20:09
 * @describe
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    void deleteByArticleId(Long articleId);
}
