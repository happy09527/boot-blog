package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.LoginService;
import com.example.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hap
 * @date 2022/4/17 15:50
 * @describe
 */

@RestController
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    LoginService loginService;

    /**
     * 推出账号
     */
    @GetMapping
    @LogAnnotation(URL = "/logout", operator = "推出账号")
    public Result logout(@RequestHeader("Authorization") String token) {
        return loginService.logout(token);
    }

}
