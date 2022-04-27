package com.example.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blog.admin.dao.mapper.AdminMapper;
import com.example.blog.admin.dao.mapper.PermissionMapper;
import com.example.blog.admin.dao.pojo.Admin;
import com.example.blog.admin.dao.pojo.Permission;
import com.example.blog.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/20 16:23
 * @describe
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findAdminByUserName(String username) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername, username).last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    public List<Permission> findPermissionsByAdminId(Long adminId){
        return adminMapper.findPermissionsByAdminId(adminId);
    }
}