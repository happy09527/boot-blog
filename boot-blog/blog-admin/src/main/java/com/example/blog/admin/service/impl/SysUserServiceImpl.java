package com.example.blog.admin.service.impl;

import com.example.blog.admin.dao.mapper.CommentMapper;
import com.example.blog.admin.dao.mapper.SysUserMapper;
import com.example.blog.admin.service.SysUserService;
import com.example.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hap
 * @date 2022/4/26 19:13
 * @describe 用户业务
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    CommentMapper commentMapper;
//    @Autowired

    @Override
    public Result delete(Long id) {
        sysUserMapper.deleteById(id);

        return null;
    }
}
