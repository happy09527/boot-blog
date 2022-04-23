package com.example.blog.controller;

import com.example.blog.dao.pojo.SysUser;
import com.example.blog.utils.UserThreadLocal;
import com.example.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    /**
     * 测试本地线程池
     */
    @RequestMapping
    public Result test() {
        SysUser sysUser = UserThreadLocal.get();
        log.info(sysUser.toString());
        return Result.success(null);
    }
}

