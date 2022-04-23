package com.example.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.dao.dos.Archives;
import com.example.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/16 18:59
 * @describe 文章表的mapper接口
 */

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 通过文章发布时间归档
     */
    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
