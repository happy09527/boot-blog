package com.example.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.blog.dao.pojo.SysUser;
import com.example.blog.service.LoginService;
import com.example.blog.service.SysUserService;
import com.example.blog.utils.JWTUtils;
import com.example.blog.vo.ErrorCode;
import com.example.blog.vo.Result;
import com.example.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hap
 * @date 2022/4/17 14:05
 * @describe
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Lazy
    @Autowired
    SysUserService sysUserService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    private static final String slat = "mszlu!@#";

    /**
     * 登录
     * 如果账号、密码有一个为空，则返回参数错误
     * 之后将密码进行md5加密 与数据库内的密码进行比对，
     * 如果成功，生成一个token，返回。并在redis进行存储
     */
    @Override
    public Result login(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.FindUserByAccount(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("token_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    /**
     * 检查token是否正确。
     */
    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> checkToken = JWTUtils.checkToken(token);
        if (checkToken == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("token_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        return JSON.parseObject(userJson, SysUser.class);
    }

    /**
     * 推出登录
     * 要删除redis中的token信息
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("token_" + token);
        return Result.success(null);
    }

    /**
     * 注册用户
     * 先判断参数，再判断用户是否存在，最后生成用户对象，存入用户表
     * @return
     */
    @Override
    public Result register(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password + slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("token_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
