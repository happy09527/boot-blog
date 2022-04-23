package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.service.SysUserService;
import com.example.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.web.bind.annotation.*;

/**
 * @author hap
 * @date 2022/4/17 14:55
 * @describe
 */

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    SysUserService sysUserService;

    /**
     * 通过请求头判断是否登录
     */
    @GetMapping("currentUser")
    @LogAnnotation(URL = "/users/currentUser", operator = "判断是否登录")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserByToken(token);
    }
}