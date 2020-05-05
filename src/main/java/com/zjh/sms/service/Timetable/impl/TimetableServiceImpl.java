package com.zjh.sms.service.Timetable.impl;

import com.zjh.sms.dao.CourseInfo.CourseInfoMapper;
import com.zjh.sms.dao.TeacherCourse.TeacherCourseMapper;
import com.zjh.sms.dao.Timetable.TimetableMapper;
import com.zjh.sms.dao.User.StudentMapper;
import com.zjh.sms.dao.User.TeacherMapper;
import com.zjh.sms.dao.WeekCourse.WeekCourseMapper;
import com.zjh.sms.domain.CourseInfo;
import com.zjh.sms.domain.TeacherCourse;
import com.zjh.sms.domain.WeekCourse;
import com.zjh.sms.dto.Timetable;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.Timetable.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Description
 * Author: zjh
 * Date2020/4/7 14:17
 **/
@Service
public class TimetableServiceImpl implements TimetableService {

  @Resource
  private TimetableMapper timetableMapper;
  @Resource
  private StudentMapper studentMapper;
  @Resource
  private WeekCourseMapper weekCourseMapper;
  @Resource
  private TeacherCourseMapper teacherCourseMapper;
  @Resource
  private CourseInfoMapper courseInfoMapper;

  @Override
  @Transactional
  public void add(List<WeekCourse> list) {
    Map<String, Object> condition = new HashMap<>();
    condition.put("profession", list.get(0).getProfession());
    condition.put("grade", list.get(0).getGrade());
    condition.put("year", list.get(0).getYear());
    condition.put("term", list.get(0).getTerm());

    // 获取原本课程表信息
    List<Timetable> timeTableList = timetableMapper.getTimetable(condition);
    Set<Integer> ids = new HashSet<>();
    Set<Integer> weekIds = new HashSet<>();
    for (Timetable timetable : timeTableList) {
      ids.add(timetable.getId());
      weekIds.add(timetable.getWeekId());
    }
    if (ids.size() > 0) {
      // 修改
      for (int i = 0; i < list.size(); i++) {
        List<Integer> weekList = new ArrayList<>(weekIds);
        // 排序
        Collections.sort(weekList);
        WeekCourse weekCourse = list.get(i);
        weekCourse.setId(weekList.get(i));
        dealWeek(list.get(i));
        weekCourseMapper.update(list.get(i));
      }
    } else {
      // 删除旧课程表
      timetableMapper.deleteTimeTable(condition);
      // 新增
      for (WeekCourse weekCourse : list) {
        dealWeek(weekCourse);
        weekCourseMapper.add(weekCourse);
        Timetable timetable = new Timetable();
        timetable.setWeekId(weekCourse.getId());
        timetable.setProfession(weekCourse.getProfession());
        timetable.setGrade(weekCourse.getGrade());
        timetable.setYear(weekCourse.getYear());
        timetable.setTerm(weekCourse.getTerm());
        timetableMapper.add(timetable);
      }
    }
  }

  private void dealWeek(WeekCourse weekCourse) {
    if (weekCourse.getMonday() == null || weekCourse.getMonday().equals("")) {
      weekCourse.setMonday("一");
    }
    if (weekCourse.getTuesday() == null || weekCourse.getTuesday().equals("")) {
      weekCourse.setTuesday("一");
    }
    if (weekCourse.getWednesday() == null || weekCourse.getWednesday().equals("")) {
      weekCourse.setWednesday("一");
    }
    if (weekCourse.getThursday() == null || weekCourse.getThursday().equals("")) {
      weekCourse.setThursday("一");
    }
    if (weekCourse.getFriday() == null || weekCourse.getFriday().equals("")) {
      weekCourse.setFriday("一");
    }
    if (weekCourse.getSaturday() == null || weekCourse.getSaturday().equals("")) {
      weekCourse.setSaturday("一");
    }
    if (weekCourse.getSunday() == null || weekCourse.getSunday().equals("")) {
      weekCourse.setSunday("一");
    }
  }

  @Override
  public List<WeekCourse> getTimetable(Map<String, Object> condition) {
    int num = timetableMapper.checkCount(condition);
    List<WeekCourse> list = new ArrayList<>();
    if (num == 0) {
      for (int i = 1; i < 11; i++) {
        WeekCourse week = new WeekCourse();
        list.add(week);
      }
    } else {
      list = weekCourseMapper.getWeekCourse(condition);
      dealMethod(list, condition);
    }
    return list;
  }
  //  根据当前周过滤课程表中不属于当前周的课程
  private void dealMethod (List<WeekCourse> list, Map<String, Object> condition) {
    for (WeekCourse weekCourse : list) {
      Map<String, Object> map = new HashMap<>();
      map.put("profession", condition.get("profession").toString());
      map.put("week", condition.get("week").toString());
      map.put("name", weekCourse.getMonday().toString());
      weekCourse.setMonday(dealCourseInfo(map));
      map.put("name", weekCourse.getTuesday().toString());
      weekCourse.setTuesday(dealCourseInfo(map));
      map.put("name", weekCourse.getWednesday().toString());
      weekCourse.setWednesday(dealCourseInfo(map));
      map.put("name", weekCourse.getThursday().toString());
      weekCourse.setThursday(dealCourseInfo(map));
      map.put("name", weekCourse.getFriday().toString());
      weekCourse.setFriday(dealCourseInfo(map));
      map.put("name", weekCourse.getSaturday().toString());
      weekCourse.setSaturday(dealCourseInfo(map));
      map.put("name", weekCourse.getSunday().toString());
      weekCourse.setSunday(dealCourseInfo(map));
    }
  }
  private String dealCourseInfo (Map<String, Object> map) {
    CourseInfo courseInfo = courseInfoMapper.getInfo(map);
    if (courseInfo != null) {
      int start = courseInfo.getStart();
      int end = courseInfo.getEnd();
      int content = Integer.parseInt(map.get("week").toString());
      if (content < start || content > end) {
        return "一";
      }
    }
    return map.get("name").toString();
  }
  @Override
  public List<WeekCourse> getTimetableByStudent(Map<String, Object> condition) {
    User user = studentMapper.getStudentById(condition.get("studentName").toString());
    Map<String, Object> oldMap = new HashMap<>();
    oldMap.put("profession", user.getProfession());
    oldMap.put("grade", user.getGrade());
    oldMap.put("year", condition.get("year"));
    oldMap.put("term", condition.get("term"));
    oldMap.put("week", condition.get("week"));
    List<WeekCourse> list = weekCourseMapper.getWeekCourse(oldMap);
    dealMethod(list, oldMap);
    return list;
  }

  @Override
  public List<WeekCourse> getTimetableByTeacher(Map<String, Object> condition) {
    // 获取教师负责的专业、班级、课程
    List<TeacherCourse> list = teacherCourseMapper.getCourseListById(condition.get("teacherId").toString());
    List<WeekCourse> newList = new ArrayList<>();
    // 将新课程的每一项设为"一"
    for (int i = 1; i < 11; i++) {
      WeekCourse week = new WeekCourse();
      dealWeek(week);
      newList.add(week);
    }
    for (TeacherCourse teacherCourse : list) {
      Map<String, Object> map = new HashMap<>();
      map.put("profession", teacherCourse.getProfession());
      map.put("grade", teacherCourse.getGrade());
      map.put("term", teacherCourse.getTerm());
      map.put("week", condition.get("week"));
      // 根据专业班级获取课程表
      List<WeekCourse> weekCourseList = weekCourseMapper.getWeekCourse(map);
      dealMethod(weekCourseList, map);
      for (int i = 0; i < weekCourseList.size(); i++) {
        WeekCourse weekCourse = dealWeekCourse(newList.get(i), weekCourseList.get(i), teacherCourse.getName());
        newList.set(i, weekCourse);
      }
    }
    return newList;
  }

  @Override
  public void updateCourseInfo(CourseInfo courseInfo) {
    courseInfoMapper.updateCourseInfo(courseInfo);
  }


  private WeekCourse dealWeekCourse(WeekCourse newWeek, WeekCourse oldWeek, String name) {
    // 当课程表的课程等于教师负责的课程时，存入新的课程表newWeek

    if (oldWeek.getMonday().equals(name)) {
      newWeek.setMonday(oldWeek.getMonday());
    }
    if (oldWeek.getTuesday().equals(name)) {
      newWeek.setTuesday(oldWeek.getTuesday());
    }
    if (oldWeek.getWednesday().equals(name)) {
      newWeek.setWednesday(oldWeek.getWednesday());
    }
    if (oldWeek.getThursday().equals(name)) {
      newWeek.setThursday(oldWeek.getThursday());
    }
    if (oldWeek.getFriday().equals(name)) {
      newWeek.setFriday(oldWeek.getFriday());
    }
    if (oldWeek.getSaturday().equals(name)) {
      newWeek.setSaturday(oldWeek.getSaturday());
    }
    if (oldWeek.getSunday().equals(name)) {
      newWeek.setSunday(oldWeek.getSunday());
    }
    return newWeek;
  }
}
