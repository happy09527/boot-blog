package com.example.blog.admin.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hap
 * @date 2022/4/20 16:40
 * @describe
 */
public interface AuthService {
    boolean auth(HttpServletRequest request, Authentication authentication);
    }
