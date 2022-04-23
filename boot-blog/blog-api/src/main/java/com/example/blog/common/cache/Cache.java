package com.example.blog.common.cache;


import java.lang.annotation.*;

/**
 * @author hap
 * @date 2022/4/19 16:06
 * @describe
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;

    String name() default "";

}