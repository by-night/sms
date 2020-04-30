package com.zjh.sms.service.Course.impl;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dao.Course.CourseMapper;
import com.zjh.sms.dto.Course;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.Course.CourseService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 课程信息业务层
 * Author: zjh
 * Date2020/3/11 14:49
 **/
@Service
public class CourseServiceImpl implements CourseService {
  @Resource
  private CourseMapper courseMapper;

  @Override
  public void addCourse(Course course) {
    String id = "";
    String str = course.getYear().toString() + "0" + course.getTerm();
    Map<String, Object> condition = new HashMap<>();
    condition.put("profession", course.getProfession());
    condition.put("year", course.getYear());
    Integer num = courseMapper.checkCodeCount(condition);
    if (num < 100) {
      id = str + "00" + num;
    } else if (num < 1000) {
      id = str + "0" + num;
    } else {
      id = str + num;
    }
    course.setId(id);
    courseMapper.addCourse(course);
  }

  @Override
  public void delete(List<Integer> ids) {
    courseMapper.delete(ids);
  }

  @Override
  public void update(Course course) {
    courseMapper.update(course);
  }

  @Override
  public PagingResult<Course> getCourseList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
    List<Course> CourseList = courseMapper.getCourseList(pageRowBounds, condition);
    return new PagingResult<>(CourseList, pageRowBounds.getTotal());
  }

  @Override
  public List<Course> getCourseByMap(Map<String, Object> condition) {
    return courseMapper.getCourseByMap(condition);
  }

}
