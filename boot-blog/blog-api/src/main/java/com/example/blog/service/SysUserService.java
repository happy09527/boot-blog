package com.example.blog.service;

import com.example.blog.dao.pojo.SysUser;
import com.example.blog.vo.Result;
import com.example.blog.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hap
 * @date 2022/4/16 20:41
 * @describe
 */

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser FindUserByAccount(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long id);

//    Result avatar(MultipartFile file, SysUser sysUser);

    Result avatar(MultipartFile file,String token); //使用token进行头像修改

}