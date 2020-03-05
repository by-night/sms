package com.zjh.sms.service.impl;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dto.User;
import com.zjh.sms.dao.UserMapper;
import com.zjh.sms.service.UserService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
    String keyValue = condition.get("keyValue").toString();
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

  @Override
  public void addStudent(User user) {
    userMapper.addStudent(user);
  }

  @Override
  public void addTeacher(User user) {
    userMapper.addTeacher(user);
  }

  @Override
  public PagingResult<User> getStudentList(RowBounds rowBounds) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<User> formFlowList = userMapper.getStudentList(pageRowBounds);
    return new PagingResult<>(formFlowList, pageRowBounds.getTotal());
  }

  @Override
  public PagingResult<User> getTeacherList(RowBounds rowBounds) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<User> formFlowList = userMapper.getTeacherList(pageRowBounds);
    return new PagingResult<>(formFlowList, pageRowBounds.getTotal());
  }
}
