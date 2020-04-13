package com.zjh.sms.controller.TeacherCourse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjh.sms.domain.TeacherCourse;
import com.zjh.sms.service.TeacherCourse.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Description 教师课程控制层
 * Author: zjh
 * Date2020/3/29 15:03
 **/
@RestController
@RequestMapping("/api/sms/teacher/course")
public class TeacherCourseController {

  @Autowired
  private TeacherCourseService teacherCourseService;

  @PostMapping
  public void add(@RequestBody JSONArray teacherCourseInfo) {
    List<TeacherCourse> list = JSONObject.parseArray(teacherCourseInfo.toJSONString(), TeacherCourse.class);
    teacherCourseService.add(list);
  }

  @DeleteMapping("/{ids}")
  public void delete(@PathVariable("ids") Integer[] ids) {
    List<Integer> idsList = Arrays.asList(ids);
    teacherCourseService.delete(idsList);
  }

  @PutMapping
  public void update(@RequestBody TeacherCourse teacherCourse) {
    teacherCourseService.update(teacherCourse);
  }

  @GetMapping("/getCourseListById/{id}")
  public List<TeacherCourse> getCourseListById(@PathVariable("id") String id) {
    return teacherCourseService.getCourseListById(id);
  }

  @GetMapping("/getProfessionInfoByTeacher/{teacherId}")
  public List<Map<String, Object>> getProfessionInfo(@PathVariable("teacherId") String teacherId) {
    return teacherCourseService.getProfessionInfo(teacherId);
  }

  @GetMapping("/getProfessionInfoByAdmin")
  public List<Map<String, Object>> getProfessionInfoByAdmin() {
    return teacherCourseService.getProfessionInfoByAdmin();
  }

  @GetMapping("/getCourseInfo")
  public TeacherCourse getCourseInfo(@RequestParam Map<String, Object> condition) {
    return teacherCourseService.getCourseInfo(condition);
  }
}
