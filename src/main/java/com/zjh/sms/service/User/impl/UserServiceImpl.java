package com.zjh.sms.service.User.impl;

import com.zjh.sms.dao.User.UserMapper;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Description 登陆用户业务层
 * Author: zjh
 * Date2019/10/21 21:09
 **/
@Service
public class UserServiceImpl implements UserService {
  @Resource
  private UserMapper userMapper;

  @Override
  public User getStudentInfo(Map<String, Object> condition) {
    String keyValue = condition.get("level").toString();
    if (keyValue.equals("0")) {
      return userMapper.getAdminInfo(condition);
    } else if (keyValue.equals("1")) {
      return userMapper.getTeacherInfo(condition);
    } else {
      return userMapper.getStudentInfo(condition);
    }
  }

  @Override
  public boolean update(Map<String, Object> condition) {
    Integer num = userMapper.checkPasswordCount(condition);
    if (num != 0) {
      userMapper.update(condition);
    }
    return num != 0;
  }
}
