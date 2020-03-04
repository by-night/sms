package com.zjh.sms.service.impl;

import com.zjh.sms.dto.User;
import com.zjh.sms.dao.UserMapper;
import com.zjh.sms.service.UserService;
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
  public User getUserInfo(Map<String, Object> condition) {
    User user =  userMapper.getUserInfo(condition);
    return user;
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
