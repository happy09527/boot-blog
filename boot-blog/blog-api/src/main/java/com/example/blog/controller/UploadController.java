package com.example.blog.controller;

import com.example.blog.common.aop.LogAnnotation;
import com.example.blog.dao.pojo.SysUser;
import com.example.blog.service.SysUserService;
import com.example.blog.utils.QiniuUtils;
import com.example.blog.utils.UserThreadLocal;
import com.example.blog.vo.ErrorCode;
import com.example.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author hap
 * @date 2022/4/18 22:43
 * @describe
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    QiniuUtils qiniuUtils;
    @Autowired
    SysUserService sysUserService;

    /**
     * 上传图片
     */
    @PostMapping
    @LogAnnotation(URL = "/upload", operator = "上传图片")
    public Result upload(@RequestParam("image") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload) {
            return Result.success(QiniuUtils.url + "/" + fileName);
        }
//        return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        return Result.fail(222222, "图片上传失败");
    }

    /**
     * 上传用户头像
     */
    @PostMapping("/avatar")
    @LogAnnotation(URL = "/upload/avatar", operator = "上传头像")
    public Result avatar(@RequestParam("image") MultipartFile file,
                         @RequestHeader("Authorization") String token) {
        return sysUserService.avatar(file, token);
    }
//    /**
//     * 上传用户头像
//     */
//    @PostMapping("/avatar")
//    @LogAnnotation(URL = "/upload/avatar", operator = "上传头像")
//    public Result avatar(@RequestParam("image") MultipartFile file){
//        SysUser sysUser = UserThreadLocal.get();
//        return sysUserService.avatar(file, sysUser);
//    }
}