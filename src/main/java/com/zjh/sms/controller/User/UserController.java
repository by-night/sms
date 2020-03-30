package com.zjh.sms.controller.User;

import com.zjh.sms.dto.Tree;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
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
    return userService.getStudentInfo(map);
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
