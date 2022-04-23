package com.example.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/16 19:01
 * @describe
 */

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查找标签列表
     *
     */
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
