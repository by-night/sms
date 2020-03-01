package com.zjh.student_mis.controller;

import com.zjh.student_mis.dto.User;
import com.zjh.student_mis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Description 登陆用户控制层
 * Author: zjh
 * Date2019/10/21 21:00
 **/
@RestController
@RequestMapping("/api/mis/user")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public User getUserInfo (@RequestParam Map<String, Object> condition) {
    Map<String, Object> map = new HashMap<>();
    map.put("username", condition.get("username").toString());
    map.put("password", condition.get("password").toString());
    return userService.getUserInfo(map);
  }
  @GetMapping("/hello")
  public String hello() {
    return "hahaha";
  }


}
