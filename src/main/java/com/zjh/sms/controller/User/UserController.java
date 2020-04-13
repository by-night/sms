package com.zjh.sms.controller.User;

import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.UserService;
import com.zjh.sms.utils.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
  public User getStudentInfo (@RequestParam Map<String, Object> condition) {
    Map<String, Object> map = new HashMap<>();
    map.put("username", condition.get("username").toString());
    map.put("password", condition.get("password").toString());
    map.put("level", condition.get("level"));
    User user = userService.getStudentInfo(map);
    user.setToken(userService.getToken(user));
    user.setTime(60*30);
    return user;
  }

  /*测试token  不登录没有token*/
  @UserLoginToken
  @GetMapping("/getMessage")
  public String getMessage(){
    return "你已通过验证";
  }

  @GetMapping("/edit/password")
  public boolean update (@RequestParam Map<String, Object> condition) {
    Map<String, Object> map = new HashMap<>();
    map.put("username", condition.get("username").toString());
    map.put("password", condition.get("password").toString());
    map.put("passwordAgain", condition.get("passwordAgain").toString());
    return userService.update(map);
  }

  @GetMapping("/getTree")
  public List<Object> getTree () {
    return userService.getTree();
  }

}
