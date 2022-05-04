package com.example.blog;

import com.example.blog.utils.JWTUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author hap
 * @date 2022/4/17 10:24
 * @describe
 */

@SpringBootTest
public class TestBlog {

    @Test
    public void test1() {
//        String token = JWTUtils.createToken(100L);
//        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
//        System.out.println(stringObjectMap.values());

        char ad = 'A';
        System.out.println(ad-'a');
    }
}
