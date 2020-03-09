package com.zjh.sms.service.User.impl;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dao.User.StudentMapper;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.StudentService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    studentMapper.addStudent(user);
  }

  @Override
  public void
  delete(List<Integer> ids) {
    studentMapper.delete(ids);
  }

  @Override
  public void update(User user) {
    studentMapper.update(user);
  }

  @Override
  public PagingResult<User> getStudentList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<User> formFlowList = studentMapper.getStudentList(pageRowBounds, condition);
    return new PagingResult<>(formFlowList, pageRowBounds.getTotal());
  }
}
