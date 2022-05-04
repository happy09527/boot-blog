package com.example.blog.admin.controller;

import com.example.blog.admin.service.SysUserService;
import com.example.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hap
 * @date 2022/4/25 17:40
 * @describe
 */

@RestController
@RequestMapping("/user")
public class UserManageController {
    @Autowired
    SysUserService sysUserService;

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return sysUserService.delete(id);
    }
}
