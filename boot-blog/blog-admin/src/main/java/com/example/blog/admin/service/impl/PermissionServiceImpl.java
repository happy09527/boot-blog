package com.example.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.admin.dao.mapper.PermissionMapper;
import com.example.blog.admin.dao.pojo.Permission;
import com.example.blog.admin.service.PermissionService;
import com.example.blog.admin.vo.PageResult;
import com.example.blog.admin.vo.param.PageParam;
import com.example.blog.admin.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hap
 * @date 2022/4/20 14:51
 * @describe
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public Result permissionList(PageParam pageParam) {
        Page<Permission> permissionPage = new Page<>(
                pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(pageParam.getQueryString())) {
            queryWrapper.eq(Permission::getName, pageParam.getQueryString());
        }
        Page<Permission> permissionPage1 = permissionMapper.selectPage(permissionPage, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage1.getRecords());
        pageResult.setTotal(permissionPage1.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }


}
