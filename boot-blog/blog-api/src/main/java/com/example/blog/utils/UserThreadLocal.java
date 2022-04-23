package com.example.blog.utils;

import com.example.blog.dao.pojo.SysUser;

/**
 * @author hap
 * @date 2022/4/17 17:33
 * @describe 本地线程
 */
public class UserThreadLocal {
    private UserThreadLocal() {

    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }

}
