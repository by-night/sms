package com.zjh.sms.service.TeacherCourse.impl;

import com.zjh.sms.dao.Course.CourseMapper;
import com.zjh.sms.dao.Profession.ProfessionMapper;
import com.zjh.sms.dao.Score.ScoreMapper;
import com.zjh.sms.dao.TeacherCourse.TeacherCourseMapper;
import com.zjh.sms.dao.User.StudentMapper;
import com.zjh.sms.domain.Profession;
import com.zjh.sms.domain.TeacherCourse;
import com.zjh.sms.dto.Course;
import com.zjh.sms.service.TeacherCourse.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description
 * Author: zjh
 * Date2020/3/29 15:09
 **/
@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {

  @Autowired
  private TeacherCourseMapper teacherCourseMapper;
  @Autowired
  private ProfessionMapper professionMapper;
  @Autowired
  private StudentMapper studentMapper;
  @Autowired
  private CourseMapper courseMapper;

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
      Course course = courseMapper.getCourseById(teacherCourse.getCourseId());
      teacherCourse.setName(course.getName());
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

  @Override
  public List<Map<String, Object>> getProfessionInfo(String teacherId) {
    List<TeacherCourse> list = teacherCourseMapper.getCourseListById(teacherId);
    List<Map<String, Object>> arr = new ArrayList<>();
    Set<String> professionSet = new HashSet<>();
    for (TeacherCourse teacherCourse : list) {
      professionSet.add(teacherCourse.getProfession());
    }
    for (String s : professionSet) {
      Map<String, Object> condition = new HashMap<>();
      condition.put("teacherId", teacherId);
      condition.put("profession", s);
      List<TeacherCourse> listObj = teacherCourseMapper.getGradeInfoByMap(condition);
      Set<String> gradeSet = new HashSet<>();
      Set<String> courseSet = new HashSet<>();
      for (TeacherCourse teacherCourse : listObj) {
        gradeSet.add(teacherCourse.getGrade());
        courseSet.add(teacherCourse.getName());
      }
      condition.put("grade", gradeSet);
      condition.put("course", courseSet);
      arr.add(condition);
    }
    return arr;
  }

  @Override
  public List<Map<String, Object>> getProfessionInfoByAdmin() {
    List<Map<String, Object>> arr = new ArrayList<>();
    List<Profession> professionList = professionMapper.getProfessionList();
    for (Profession profession : professionList) {
      Map<String, Object> condition = new HashMap<>();
      condition.put("profession", profession.getName());
      List<String> gradeList = studentMapper.getGradeByProfession(profession.getName());
      List<Course> courseList = courseMapper.getCourseByMap(condition);
      List<String> courseNameList = new ArrayList<>();
      for (Course course : courseList) {
        courseNameList.add(course.getName());
      }

      // 转成int，然后再排序
      List<Integer> list = new ArrayList<>();
      for (String str : new HashSet<>(gradeList)) {
        int number = Integer.parseInt(str);
        list.add(number);
      }
      Collections.sort(list);

      condition.put("grade", list);
      condition.put("course", new HashSet<>(courseNameList));
      arr.add(condition);
    }
    return arr;
  }

  @Override
  public TeacherCourse getCourseInfo(Map<String, Object> condition) {
    return teacherCourseMapper.getCourseInfo(condition);
  }
}
