package com.zjh.sms.service.TeacherCourse;

import com.zjh.sms.domain.TeacherCourse;

import java.util.List;
import java.util.Map;

/**
 * Description 教师课程Service层
 * Author: zjh
 * Date2020/3/29 15:09
 **/
public interface TeacherCourseService {
  /**
   * description: 新增
   * @param list
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void add(List<TeacherCourse> list);

  /**
   * description: 删除
   *
   * @param ids
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void delete(List<Integer> ids);

  /**
   * description: 修改
   * @param teacherCourse
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void update(TeacherCourse teacherCourse);

  /**
   * description: 根据教师id获取教师课程信息
   * @param: String
   * return: List<TeacherCourse>
   * Author: zjh
   * @Date: 2020/3/29 14:31
   */
  List<TeacherCourse> getCourseListById(String id);
  /**
  * description: 根据教师id获取教师用户所管理的专业、班级、课程名等信息
  * @param: String
  * return: List<Map<String, Object>>
  * Author: zjh
  * @Date: 2020/4/4 20:54
  */
  List<Map<String, Object>> getProfessionInfo(String teacherId);
  /**
  * description: 管理员用户获取所有的班级和课程，渲染成下拉框选择
  * return: List<Map<String, Object>>
  * Author: zjh
  * @Date: 2020/4/5 0:16
  */
  List<Map<String, Object>> getProfessionInfoByAdmin();
  /**
  * description: 课程表中获取课程详细信息
  * @param: Map<String, Object> condition
  * return: TeacherCourse
  * Author: zjh
  * @Date: 2020/4/9 17:29
  */
  TeacherCourse getCourseInfo(Map<String, Object> condition);
}
