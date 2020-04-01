package com.zjh.sms.service.TeacherCourse.impl;

import com.zjh.sms.dao.Score.ScoreMapper;
import com.zjh.sms.dao.TeacherCourse.TeacherCourseMapper;
import com.zjh.sms.domain.TeacherCourse;
import com.zjh.sms.dto.Course;
import com.zjh.sms.service.TeacherCourse.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * Author: zjh
 * Date2020/3/29 15:09
 **/
@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {

  @Autowired
  private TeacherCourseMapper teacherCourseMapper;

  @Override
  public void add(List<TeacherCourse> list) {
    List<Integer> ids = new ArrayList<>();
    for (TeacherCourse teacherCourse : list) {
      if (teacherCourse.getId() != -1) {
        ids.add(teacherCourse.getId());
      }
    }
    if (ids.size() > 0) {
      teacherCourseMapper.delete(ids);
    }
    for (TeacherCourse teacherCourse : list) {
      teacherCourseMapper.add(teacherCourse);
    }
  }

  @Override
  public void delete(List<Integer> ids) {
    teacherCourseMapper.delete(ids);
  }

  @Override
  public void update(TeacherCourse teacherCourse) {
    teacherCourseMapper.update(teacherCourse);
  }

  @Override
  public List<TeacherCourse> getCourseListById(String id) {
    return teacherCourseMapper.getCourseListById(id);
  }
}
