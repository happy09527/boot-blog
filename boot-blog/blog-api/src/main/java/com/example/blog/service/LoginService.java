package com.example.blog.service;

import com.example.blog.dao.pojo.SysUser;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.LoginParam;

/**
 * @author hap
 * @date 2022/4/17 14:05
 * @describe
 */

public interface LoginService {
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParam loginParam);
}


