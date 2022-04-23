package com.example.blog.admin.service.impl;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author hap
 * @date 2022/4/20 16:49
 * @describe
 */
public class MySimpleGrantedAuthority implements GrantedAuthority {
    private String authority;
    private String path;

    public MySimpleGrantedAuthority(){}

    public MySimpleGrantedAuthority(String authority){
        this.authority = authority;
    }

    public MySimpleGrantedAuthority(String authority,String path){
        this.authority = authority;
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getPath() {
        return path;
    }
}
