package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blog.dao.mapper.BlogMapper;
import com.example.blog.dao.pojo.Blog;
import com.example.blog.service.BlogService;
import com.example.blog.utils.HttpContextUtils;
import com.example.blog.utils.IpUtils;
import com.example.blog.vo.params.BlogParam;
import com.example.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hap
 * @date 2022/4/24 19:56
 * @describe
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Result getTime() {
        Long time = blogMapper.selectById(1).getTime();
        return Result.success(time);
    }

    /**
     * 获取留言信息
     */
    @Override
    public Result getMessage() {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Blog::getId);
        queryWrapper.select(Blog::getId, Blog::getTime, Blog::getContent, Blog::getAuth, Blog::getAvatar);
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        return Result.success(blogs);
    }

    /**
     * 发布留言
     * 将留言存入blog
     */
    @Override
    public Result publishMessage(BlogParam blogParam) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        Blog blog = new Blog();
        String ip = IpUtils.getIpAddr(request);
        blog.setIp(ip);
        blog.setTime(blogParam.getTime());
        blog.setContent(blogParam.getContent());
        blog.setAvatar(blogParam.getAvatar());
        String auth = blogParam.getNickname();

        if (!StringUtils.isBlank(auth)) {
            blog.setAuth(auth);
            blogMapper.insert(blog);
        } else {
            blog.setAuth("游客");
            blogMapper.insert(blog);
        }
        return Result.success(blog);
    }
}
