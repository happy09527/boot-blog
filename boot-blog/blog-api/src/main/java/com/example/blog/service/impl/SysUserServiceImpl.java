package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.blog.controller.UploadController;
import com.example.blog.dao.mapper.SysUserMapper;
import com.example.blog.dao.pojo.SysUser;
import com.example.blog.service.LoginService;
import com.example.blog.service.SysUserService;
import com.example.blog.utils.QiniuUtils;
import com.example.blog.vo.ErrorCode;
import com.example.blog.vo.LoginUserVo;
import com.example.blog.vo.Result;
import com.example.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author hap
 * @date 2022/4/16 21:02
 * @describe 用户业务
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    LoginService loginService;
    @Autowired
    QiniuUtils qiniuUtils;

    /**
     * 通过id来查找用户，如果找不到，则返回自定义信息
     */
    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("hap");
        }
        return sysUser;
    }

    /**
     * 通过account与password来查找用户信息。登录后
     */
    @Override
    public SysUser FindUserByAccount(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAdmin, SysUser::getNickname, SysUser::getAvatar);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 1、token合法性校验，
     * 2、如果校验失败，则返回错误；成功，返回结果LoginUserVo
     */
    @Override
    public Result findUserByToken(String token) {
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
//        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SysUser::getId, sysUser.getId());
//        queryWrapper.last("limit 1");
//        String avatar = sysUserMapper.selectOne(queryWrapper).getAvatar();
//        loginUserVo.setAvatar(avatar);
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        return Result.success(loginUserVo);
    }

    /**
     * 通过account查找用户
     */
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 保存用户信息
     */
    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    /**
     * 通过id查询用户
     */
    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("hap");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }
    /**
     * 使用本地线程池完成头像修改功能
     */
//    @Override
//    public Result avatar(MultipartFile file, SysUser sysUser) {
//        String originalFilename = file.getOriginalFilename();
//        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
//        boolean upload = qiniuUtils.upload(file, fileName);
//        if (sysUser == null) {
//            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
//        }
//        if (upload) {
//            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(SysUser::getId, sysUser.getId());
//            queryWrapper.last("limit 1");
//            SysUser sysUser1 = sysUserMapper.selectOne(queryWrapper);
//            sysUser1.setAvatar(QiniuUtils.url + "/" + fileName);
//            sysUserMapper.updateById(sysUser1);
//            return Result.success(QiniuUtils.url + "/" + fileName);
//        }
//        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "无法上传，等待管理员审核");
//    }

    /**
     * 修改用户头像，使用token
     */
    @Override
    public Result avatar(MultipartFile file, String token) {
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        if (upload) {
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getId, sysUser.getId());
            queryWrapper.last("limit 1");
            SysUser sysUser1 = sysUserMapper.selectOne(queryWrapper);
            sysUser1.setAvatar(QiniuUtils.url + "/" + fileName);
            sysUserMapper.updateById(sysUser1);
            return Result.success(QiniuUtils.url + "/" + fileName);
        }
        return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "无法上传，等待管理员审核");
    }
}
