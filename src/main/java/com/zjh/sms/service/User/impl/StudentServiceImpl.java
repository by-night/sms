package com.zjh.sms.service.User.impl;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dao.User.StudentMapper;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.StudentService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 学生用户业务层
 * Author: zjh
 * Date2020/3/7 15:09
 **/
@Service
public class StudentServiceImpl implements StudentService {

  @Resource
  private StudentMapper studentMapper;

  @Override
  public void addStudent(User user) {
    Map<String, Object> condition = new HashMap<>();
    condition.put("profession", user.getProfession());
    condition.put("grade", user.getGrade());
    int num = studentMapper.checkCodeCount(condition) + 1;
    String str = "";
    if (num < 10) {
      str = "00" + Integer.toString(num);
    } else if (num < 100) {
      str = "0" + Integer.toString(num);
    } else {
      str = Integer.toString(num);
    }
    String no = "3" + user.getAdmissionTime().substring(user.getAdmissionTime().length()-2)
        + "89" + "01" + user.getGrade() + str;
    user.setId(no);
    user.setUsername(no);
    user.setPassword("123456");
    studentMapper.addStudent(user);
  }

  @Override
  public void
  delete(List<String> ids) {
    studentMapper.delete(ids);
  }

  @Override
  public void update(User user) {
    studentMapper.update(user);
  }

  @Override
  public PagingResult<User> getStudentList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<User> StudentInfoList = studentMapper.getStudentList(pageRowBounds, condition);
    return new PagingResult<>(StudentInfoList, pageRowBounds.getTotal());
  }
}
