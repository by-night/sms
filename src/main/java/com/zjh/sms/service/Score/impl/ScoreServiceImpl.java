package com.zjh.sms.service.Score.impl;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dao.Score.ScoreMapper;
import com.zjh.sms.dto.Course;
import com.zjh.sms.dto.Score;
import com.zjh.sms.service.Score.ScoreService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    List<Course> courseList = new ArrayList<>();
    switch (condition.get("level").toString()) {
      case "0":
        courseList = scoreMapper.getCourseByAdmin(pageRowBounds, condition);
        for (Course course : courseList) {
          adminCourseMethod(course);
        }
        break;
      case "1":
        courseList = scoreMapper.getCourseByMap(pageRowBounds, condition);
        break;
      case "2":
        courseList = scoreMapper.getCourseByStudent(pageRowBounds, condition);
    }
    return new PagingResult<>(courseList, pageRowBounds.getTotal());
  }

  private void adminCourseMethod (Course course) {
    Map<String, Object> map = new HashMap<>();
    map.put("id", course.getNo());
    map.put("courseName", course.getName());
    Course courseById = scoreMapper.getScoreById(map);
    if (courseById != null) {
      course.setCreditsByUser(courseById.getCreditsByUser());
      course.setPointByUser(courseById.getPointByUser());
      course.setScoreByUser(courseById.getScoreByUser());
    }
  }

  @Override
  public void addEntry(List<Score> list) {
      for (Score score : list) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("StudentId", score.getNo());
        condition.put("CourseName", score.getName());
        Integer num = scoreMapper.checkCount(condition);
        if (num == 0) {
          scoreMapper.addEntry(score);
        } else {
          scoreMapper.updateEntry(score);
        }
      }
  }

  @Override
  public List<Course> getExportList(Map<String, Object> condition) {
    List<Course> courseList = new ArrayList<>();
    switch (condition.get("level").toString()) {
      case "0":
        courseList = scoreMapper.getExportListByAdmin(condition);
        for (Course course : courseList) {
          adminCourseMethod(course);
        }
        break;
      case "1":
        courseList = scoreMapper.getExportList(condition);
        break;
      case "2":
        courseList = scoreMapper.getExportListByStudent(condition);
    }
    return courseList;
  }
}
