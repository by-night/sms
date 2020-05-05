package com.zjh.sms.dao.Course;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dto.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 课程信息mapper层
 * Author: zjh
 * Date2020/3/11 15:02
 **/
@Mapper
public interface CourseMapper {
  /**
   * description: 新增课程信息
   * @param course
   * @return void
   * @author zjh
   * @Date 2020/3/4 23:05
   */
  void addCourse(Course course);

  /**
   * description: 删除课程信息
   *
   * @param ids
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void delete(@Param("ids") List<Integer> ids);

  /**
   * description: 修改课程信息
   *
   * @param course
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void update(Course course);
  /**
  * description: 获取课程信息
  * param: rowBounds
  * param: condition
  * return: List<Course>
  * Author: zjh
  * @Date: 2020/3/11 15:03
  */
  List<Course> getCourseList(PageRowBounds rowBounds, @Param("condition") Map<String, Object> condition);
  /**
   * description: 查看课程最大id
   * return: Integer
   * Author: zjh
   * @Date: 2020/3/11 15:03
   */
  String checkCodeCount(@Param("condition") Map<String, Object> condition);
  /**
  * description: 根据专业、学期获取课程列表
  * @param: condition
  * return:  List<Course>
  * Author: zjh
  * @Date: 2020/3/31 22:29
  */
  List<Course> getCourseByMap(@Param("condition") Map<String, Object> condition);

  /**
   * description: 根据课程id获取课程信息
   * @param: String
   * return: Course
   * Author: zjh
   * @Date: 2020/3/31 22:29
   */
  Course getCourseById(@Param("courseId") String courseId);
}
