package com.example.blog.admin.service.impl;

import com.example.blog.admin.dao.pojo.Admin;
import com.example.blog.admin.service.AdminService;
import com.example.blog.admin.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author hap
 * @date 2022/4/20 16:20
 * @describe 管理员用户安全验证
 */
@Service
@Slf4j
public class SecurityUserServiceImpl implements SecurityUserService, UserDetailsService {
    @Autowired
    private AdminService adminService;

    /**
     * 会在登录得时候把username传到这里
     * 根据用户名 查找用户，不存在 抛出异常，存在 将用户名，密码，授权列表 组装成springSecurity的User对象 并返回
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.findAdminByUserName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails userDetails = new User(username, admin.getPassword(), authorities);
        return userDetails;
    }
}
