package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.LoginService;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.LoginParam;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hap
 * @date 2022/4/17 15:57
 * @describe
 */

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    LoginService loginService;

    /**
     * 注册
     */
    @PostMapping
    @LogAnnotation(URL = "/register", operator = "注册")
    public Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }
}
