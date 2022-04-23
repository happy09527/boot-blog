package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.LoginService;
import com.example.blog.service.SysUserService;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hap
 * @date 2022/4/17 14:00
 * @describe
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * 登录
     */
    @PostMapping
    @LogAnnotation(URL = "/login", operator = "登录")
    public Result login(@RequestBody LoginParam loginParam) {
        return loginService.login(loginParam);
    }
}
