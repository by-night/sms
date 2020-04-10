package com.zjh.sms.dao.TeacherCourse;

import com.zjh.sms.domain.TeacherCourse;
import com.zjh.sms.dto.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description
 * Author: zjh
 * Date2020/3/29 15:13
 **/
@Mapper
public interface TeacherCourseMapper {
  /**
   * description: 新增
   * @param teacherCourse
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void add(TeacherCourse teacherCourse);

  /**
   * description: 删除
   *
   * @param ids
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void delete(@Param("ids") List<Integer> ids);

  /**
   * description: 修改
   *
   * @param teacherCourse
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void update(TeacherCourse teacherCourse);

  /**
   * description:
   * @param:
   * return:
   * Author: zjh
   * @Date: 2020/3/29 14:33
   */
  List<TeacherCourse> getCourseListById(@Param("id") String id);
  /**
   * description: 获取教师id和专业获取班级、课程名
   * @param:
   * return:
   * Author: zjh
   * @Date: 2020/4/4 20:54
   */
  List<TeacherCourse> getGradeInfoByMap(@Param("condition") Map<String, Object> condition);
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/4/7 16:17
  */
  String getTeacherId(@Param("condition") Map<String, Object> condition);
  /**
  * description: 课程表中获取课程详细信息
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/4/9 17:31
  */
  TeacherCourse getCourseInfo(@Param("condition") Map<String, Object> condition);
}
