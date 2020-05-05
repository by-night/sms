package com.zjh.sms.dao.CourseInfo;

import com.zjh.sms.domain.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Description 课程具体安排表
 * Author: zjh
 * Date2020/5/3 14:57
 **/
@Mapper
public interface CourseInfoMapper {
  /**
   * description: 新增课程具体安排（周数等）
   * @param: courseInfo
   * return: void
   * Author: zjh
   * @Date: 2020/5/3 14:57
   */
  void addCourseInfo(CourseInfo courseInfo);
  /**
  * description: 修改课程具体安排（周数等）
  * @param: courseInfo
  * return: void
  * Author: zjh
  * @Date: 2020/5/3 14:57
  */
  void updateCourseInfo(CourseInfo courseInfo);
  /**
  * description: 删除课程具体安排
  * @param: String
  * return: void
  * Author: zjh
  * @Date: 2020/5/3 16:23
  */
  void deleteInfo(@Param("id") String id);
  /**
  * description: 获取课程信息详情
  * @param: Map<String, Object> condition
  * return: CourseInfo
  * Author: zjh
  * @Date: 2020/5/3 17:06
  */
  CourseInfo getInfo(@Param("condition") Map<String, Object> condition);
}
