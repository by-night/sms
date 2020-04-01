package com.zjh.sms.service.User.impl;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dao.User.TeacherMapper;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.TeacherService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 教师用户业务层
 * Author: zjh
 * Date2020/3/7 15:10
 **/
@Service
public class TeacherServiceImpl implements TeacherService {

  @Resource
  private TeacherMapper teacherMapper;

  @Override
  public void addTeacher(User user) {
    int num = teacherMapper.checkCodeCount();
    String str = "";
    if (num < 10) {
      str = "00" + Integer.toString(num);
    } else if (num < 100) {
      str = "0" + Integer.toString(num);
    } else {
      str = Integer.toString(num);
    }
    String no = "389"+str+user.getSex().toString();
    user.setUsername(no);
    user.setId(no);
    user.setPassword("159357");
    teacherMapper.addTeacher(user);
  }

  @Override
  public void delete(List<Integer> ids) {
    teacherMapper.delete(ids);
  }

  @Override
  public void update(User user) {
    teacherMapper.update(user);
  }

  @Override
  public PagingResult<User> getTeacherList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<User> TeacherInfoList = teacherMapper.getTeacherList(pageRowBounds, condition);
    return new PagingResult<>(TeacherInfoList, pageRowBounds.getTotal());
  }
}
