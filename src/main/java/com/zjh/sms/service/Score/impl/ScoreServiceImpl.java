package com.zjh.sms.service.Score.impl;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dao.Course.CourseMapper;
import com.zjh.sms.dao.Score.ScoreMapper;
import com.zjh.sms.dao.User.StudentMapper;
import com.zjh.sms.dto.Course;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.Score.ScoreService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * Author: zjh
 * Date2020/3/13 15:49
 **/
@Service
public class ScoreServiceImpl implements ScoreService {
  @Autowired
  private ScoreMapper scoreMapper;

  @Override
  public PagingResult<Course> getCourseList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<Course> CourseList = scoreMapper.getCourseByMap(pageRowBounds, condition);
    return new PagingResult<>(CourseList, pageRowBounds.getTotal());
  }
}
