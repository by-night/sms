package com.zjh.sms.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 拦截器去获取token并验证token
 * Author: zjh
 * Date2020/3/30 23:25
 **/
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
  @Autowired
  UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
    String token = httpServletRequest.getHeader("accessToken");// 从 http 请求头中取出 token
    Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
           //2.遍历
     while(headerNames.hasMoreElements()){
               String name = headerNames.nextElement();
            //通过请求头的名称获取请求头的值
           String value = httpServletRequest.getHeader(name);
           System.out.println(name+"----"+value);
      }
    // 如果不是映射到方法直接通过
    if (!(object instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod handlerMethod = (HandlerMethod) object;
    Method method = handlerMethod.getMethod();
    //检查是否有passtoken注释，有则跳过认证
    if (method.isAnnotationPresent(PassToken.class)) {
      PassToken passToken = method.getAnnotation(PassToken.class);
      if (passToken.required()) {
        return true;
      }
    }
    //检查有没有需要用户权限的注解
    if (method.isAnnotationPresent(UserLoginToken.class)) {
      UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
      if (userLoginToken.required()) {
        // 执行认证
        if (token == null) {
          throw new RuntimeException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId = null;
        int id = Integer.parseInt(userId);
        try {
          userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
          throw new RuntimeException("401");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("level", Integer.toString(id).substring(0,1));
        map.put("id", Integer.toString(id).substring(1));
        User user = userService.findUser(map);
        if (user == null) {
          throw new RuntimeException("用户不存在，请重新登录");
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
          jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
          throw new RuntimeException("401");
        }
        return true;
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse,
                              Object o, Exception e) throws Exception {
  }
}
