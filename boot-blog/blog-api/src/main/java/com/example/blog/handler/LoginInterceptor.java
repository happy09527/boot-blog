//package com.example.blog.handler;
//
//import com.alibaba.fastjson.JSON;
//import com.example.blog.dao.pojo.SysUser;
//import com.example.blog.service.LoginService;
//import com.example.blog.utils.UserThreadLocal;
//import com.example.blog.vo.ErrorCode;
//import com.example.blog.vo.Result;
//import io.netty.util.internal.StringUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.HandlerMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author hap
// * @date 2022/4/17 16:55
// * @describe 拦截器
// */
//
//@Component
//public class LoginInterceptor implements HandlerInterceptor {
//    @Autowired
//    LoginService loginService;
//
//    /**
//     * 1. 需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
//     * 2. 判断 token是否为空，如果为空 未登录
//     * 3. 如果token 不为空，登录验证 loginService checkToken
//     * 4. 如果认证成功 放行即可
//     *             //在执行controller方法(Handler)之前进行执行
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//        String token = request.getHeader("Authorization");
//        if (StringUtils.isBlank(token)) {
//            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
//            //设置浏览器识别返回的是json
//            response.setContentType("application/json;charset=utf-8");
//            //https://www.cnblogs.com/qlqwjy/p/7455706.html response.getWriter().print()
//            //SON.toJSONString则是将对象转化为Json字符串
//            response.getWriter().print(JSON.toJSONString(result));
//            return false;
//        }
//        SysUser sysUser = loginService.checkToken(token);
//        if (sysUser == null) {
//            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
//            response.setContentType("application/json;charset=utf-8");
//            response.getWriter().print(JSON.toJSONString(result));
//            return false;
//        }
//        UserThreadLocal.put(sysUser);
//        return true;
//    }
//
//    /**
//     *       //如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
//     */
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        //如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
//        UserThreadLocal.remove();
//    }
//
//}
package com.example.blog.handler;

import com.alibaba.fastjson.JSON;
import com.example.blog.dao.pojo.SysUser;
import com.example.blog.service.LoginService;
import com.example.blog.utils.UserThreadLocal;
import com.example.blog.vo.ErrorCode;
import com.example.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法(Handler)之前进行执行
        /**
         * 1. 需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
         * 2. 判断 token是否为空，如果为空 未登录
         * 3. 如果token 不为空，登录验证 loginService checkToken
         * 4. 如果认证成功 放行即可
         */
        if (!(handler instanceof HandlerMethod)){
            //handler 可能是 RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录去查询
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");


        if (StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //登录验证成功，放行
        //我希望在controller中 直接获取用户的信息 怎么获取?
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}

