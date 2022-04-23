package com.example.blog.admin.service;

import com.example.blog.admin.dao.pojo.Permission;
import com.example.blog.admin.vo.param.PageParam;
import com.example.blog.admin.vo.Result;

/**
 * @author hap
 * @date 2022/4/20 14:52
 * @describe
 */

public interface PermissionService {
    Result permissionList(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
