package com.zjh.sms.controller;

import com.zjh.sms.dto.User;
import com.zjh.sms.service.UserService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
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
  public User getStudentInfo (@RequestParam Map<String, Object> condition) {
    Map<String, Object> map = new HashMap<>();
    map.put("username", condition.get("username").toString());
    map.put("password", condition.get("password").toString());
    map.put("keyValue", condition.get("keyValue"));
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

  @PostMapping("/student")
  public void addStudent(@RequestBody User user) {
    userService.addStudent(user);
  }
  @PostMapping("/teacher")
  public void addTeacher(@RequestBody User user) {
    userService.addTeacher(user);
  }
  @GetMapping("/getStudentList")
  public PagingResult<User> getStudentList (@RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
                                                @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    RowBounds rowBounds = new RowBounds(offset, limit);
    return userService.getStudentList(rowBounds);
  }
  @GetMapping("/getTeacherList")
  public PagingResult<User> getTeacherList (@RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
                                            @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    RowBounds rowBounds = new RowBounds(offset, limit);
    return userService.getTeacherList(rowBounds);
  }

}
