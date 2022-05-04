package com.example.blog.utils;

import com.example.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author hap
 * @date 2022/4/26 9:57
 * @describe 缓存工具类
 */
@Slf4j
@Component
public class CacheUtils {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 删除缓存
     */
    public void deleteCache(String keys) {
        Set<String> list_article = redisTemplate.keys(keys);
        list_article.forEach(s -> {
            redisTemplate.delete(s);
            log.info("更新了缓存：{}", s);
        });
    }
}
