package com.example.blog.admin.service;

import com.example.blog.admin.dao.pojo.Admin;
import com.example.blog.admin.dao.pojo.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hap
 * @date 2022/4/20 16:22
 * @describe
 */
public interface AdminService {
    public Admin findAdminByUserName(String username);

    List<Permission> findPermissionsByAdminId(Long id);
}
