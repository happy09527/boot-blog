package com.example.blog.common.aop;

/**
 * @author hap
 * @date 2022/4/18 22:19
 * @describe
 */

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String URL() default "";
    String operator() default "";
}
