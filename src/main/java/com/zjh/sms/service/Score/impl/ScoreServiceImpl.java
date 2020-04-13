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

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

  private void adminCourseMethod(Course course) {
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
      // string转double
      double scoreByUser = Double.parseDouble(score.getScoreByUser());
      BigDecimal bg = new BigDecimal((scoreByUser/10-5));
      // 取两位有效数字
      double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
      String point = scoreByUser > 59 ? String.valueOf(f1):"0";
      String credits = scoreByUser >= score.getScore()*0.6 ? score.getCredits() : "0.00";
      score.setPointByUser(point);
      score.setCreditsByUser(credits);

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

  @Override
  public List<Map<String, Object>> getUserNum(Map<String, Object> condition) {
    List<Map<String, Object>> list = new ArrayList<>();
    List<Course> courseList = new ArrayList<>();
    switch (condition.get("level").toString()) {
      case "0":
        courseList = scoreMapper.getExportListByAdmin(condition);
        for (Course course : courseList) {
          adminCourseMethod(course);
        }
        list = dealScore(courseList);
        break;
      case "1":
        courseList = scoreMapper.getExportList(condition);
        list = dealScore(courseList);
        break;
      case "2":
        courseList = scoreMapper.getExportListByStudent(condition);
        list = dealScore(courseList);
    }
    return list;
  }

  @Override
  public Map<String, Object> getUserTotal(Map<String, Object> condition) {
    List<Course> list = scoreMapper.getUserTotal(condition);
    double credits = 0.00;
    double point = 0.00;
    for (Course course : list) {
      double a = Double.parseDouble(course.getCreditsByUser());
      credits += a;
      double b = Double.parseDouble(course.getPointByUser());
      point += b;
    }
    Map<String, Object> map = new HashMap<>();
    map.put("credits", credits);
    map.put("point", point);
    return map;
  }

  private List<Map<String, Object>> dealScore(List<Course> courseList) {
    if (courseList.size() > 0) {
      Map<String, Object> failCondition = new HashMap<>();
      Map<String, Object> passCondition = new HashMap<>();
      Map<String, Object> goodCondition = new HashMap<>();
      int fail = 0;
      int pass = 0;
      int good = 0;
      for (Course course : courseList) {
        if (course.getScoreByUser() != null) {
          double scoreFull = course.getScore() / 100;
          double score = Double.parseDouble(course.getScoreByUser()) * scoreFull;
          if (score < 60) {
            fail++;
          } else if (score < 85) {
            pass++;
          } else {
            good++;
          }
        }
      }
      int passLine = (int) (courseList.get(0).getScore() * 0.6);
      int goodLine = (int) (courseList.get(0).getScore() * 0.85);
      int FullLine = courseList.get(0).getScore();
      String failLabel = "不及格(" + "0-" + (passLine - 1) + ")";
      String passLabel = "及格(" + passLine + "-" + (goodLine - 1) + ")";
      String goodLabel = "优秀(" + goodLine + "-" + FullLine + ")";
      failCondition.put("label", failLabel);
      failCondition.put("value", fail);
      passCondition.put("label", passLabel);
      passCondition.put("value", pass);
      goodCondition.put("label", goodLabel);
      goodCondition.put("value", good);
      List<Map<String, Object>> list = new ArrayList<>();
      list.add(goodCondition);
      list.add(passCondition);
      list.add(failCondition);
      return list;
    } else {
      return new ArrayList<>();
    }
  }
}
