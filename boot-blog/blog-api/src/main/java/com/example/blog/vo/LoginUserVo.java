package com.example.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author hap
 * @date 2022/4/17 15:02
 * @describe  登录用户信息
 */

@Data
public class LoginUserVo {
    private String id;

    private String account;

    private String nickname;

    private String avatar;
}
