package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.blog.dao.mapper.TagMapper;
import com.example.blog.dao.pojo.Article;
import com.example.blog.dao.pojo.Tag;
import com.example.blog.service.TagService;
import com.example.blog.vo.ArticleVo;
import com.example.blog.vo.Result;
import com.example.blog.vo.TagVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hap
 * @date 2022/4/16 20:35
 * @describe 标签业务层
 */
@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    /**
     * 通过文章id来查找所属标签
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> list = tagMapper.findTagsByArticleId(articleId);
        List<TagVo> listVo = copyList(list);
        return listVo;
    }

    /**
     * 标签下所拥有的文章最多，则为最热标签
     * 根据tag id进行分组 计数 排序，取前limit个标签
     */
    @Override
    public Result hots(int limit) {
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        //因为id in（1,2,3） 里面不能为空所以我们需要进行判断
        if (CollectionUtils.isEmpty(tagIds)) {
            return Result.success(Collections.emptyList());
        }
        //需求的是tagId 和tagName Tag对象
        //我们的sql语句类似于select * from tag where id in (1,2,3)
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);

    }

    @Override
    public Result tags() {
        List<Tag> list = tagMapper.selectList(new LambdaQueryWrapper<>());
//        log.info(list.get(0).getAvatar());
        return Result.success(copyList(list));
    }

    @Override
    public Result tagDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        TagVo tagVo = copy(tag);
        return Result.success(tagVo);
    }

    /**
     * 将entity对象集合转换为vo对象集合
     */
    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> list = new ArrayList<>();
        for (Tag tag : tagList) {
            list.add(copy(tag));
        }
        return list;
    }

    /**
     * 将entity对象转换为vo对象
     */
    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

}