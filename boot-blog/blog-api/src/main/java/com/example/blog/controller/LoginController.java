package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.LoginService;
import com.example.blog.service.SysUserService;
import com.example.blog.utils.CreateVerificationCode;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

/**
 * @author hap
 * @date 2022/4/17 14:00
 * @describe 登录
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
        BufferedImage verificationCode = CreateVerificationCode.getVerifiCodeImage();
        String code = new String(CreateVerificationCode.getVerifiCode());
        return loginService.login(loginParam);
    }
}
