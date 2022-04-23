package com.example.blog.vo.params;

import lombok.Data;

/**
 * @author hap
 * @date 2022/4/17 14:06
 * @describe 登录参数、注册参数
 */

@Data
public class LoginParam {
    private String account;
    private String password;
    private String nickname;
}
