package com.example.blog.handler;

import com.example.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hap
 * @date 2022/4/16 23:03
 * @describe 对加了@Controller注解的方法进行拦截处理 AOP
 */

@ControllerAdvice
public class AllExceptionHandler {
    /**
     * 进行异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex) {
        ex.printStackTrace();
        return Result.fail(-999, "系统繁忙");
    }
}
