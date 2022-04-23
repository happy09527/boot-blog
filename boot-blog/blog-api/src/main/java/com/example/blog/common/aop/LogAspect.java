package com.example.blog.common.aop;

import com.example.blog.utils.HttpContextUtils;
import com.example.blog.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author hap
 * @date 2022/4/18 22:23
 * @describe
 */
@Component
@Aspect //切面 定义了通知和切点的关系
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.example.blog.common.aop.LogAnnotation)")
    public void pt() {

    }

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("URL:{}", logAnnotation.URL());
        log.info("operator:{}", logAnnotation.operator());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try{
            String params = JSON.toJSONString(args[0]);
            log.info("params:{}", params);
        }catch (Exception e){
            log.info("params:{null}");
        }

        //获取request 设置IP地址

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));
        log.info("execute time : {} ms", time);
    }

}
