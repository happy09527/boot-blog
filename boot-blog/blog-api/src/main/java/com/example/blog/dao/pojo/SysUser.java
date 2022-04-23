package com.example.blog.dao.pojo;
import lombok.Data;
import lombok.ToString;

/**
 * @author hap
 * @date 2022/4/16 18:56
 * @describe 用户表
 */


@Data
@ToString
public class SysUser {

    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
