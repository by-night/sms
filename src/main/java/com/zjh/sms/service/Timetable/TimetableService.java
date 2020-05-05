package com.zjh.sms.service.Timetable;

import com.zjh.sms.domain.CourseInfo;
import com.zjh.sms.domain.WeekCourse;

import java.util.List;
import java.util.Map;

/**
 * Description 课程表Service层
 * Author: zjh
 * Date2020/4/7 14:03
 **/
public interface TimetableService {
  /**
   * description: 新增课程表
   * @param list
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void add(List<WeekCourse> list);

  /**
   * description: 管理员用户获取课程表
   * @param condition
   * @author zjh
   * @return List<WeekCourse>
   * @date 2020/3/4 23:05
   */
  List<WeekCourse> getTimetable(Map<String, Object> condition);
  /**
  * description: 学生用户获取课程表
  * @param: condition
  * return: List<WeekCourse>
  * Author: zjh
  * @Date: 2020/4/7 21:10
  */
  List<WeekCourse> getTimetableByStudent(Map<String, Object> condition);
  /**
  * description: 教师用户获取课程表
  * @param: condition
  * return: List<WeekCourse>
  * Author: zjh
  * @Date: 2020/4/7 21:31
  */
  List<WeekCourse> getTimetableByTeacher(Map<String, Object> condition);
  /**
  * description: 新增课程具体安排（周数等）
  * @param: CourseInfo
  * return: void
  * Author: zjh
  * @Date: 2020/5/3 14:50
  */
  void updateCourseInfo(CourseInfo courseInfo);
}
