package com.zjh.sms.dao.WeekCourse;

import com.zjh.sms.domain.WeekCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 课程表具体安排Mapper层
 * Author: zjh
 * Date2020/4/7 16:46
 **/
@Mapper
public interface WeekCourseMapper {
  /**
   * description: 新增week表数据
   * @param weekCourse
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void add(WeekCourse weekCourse);
  /**
  * description: 修改week表
  * @param: WeekCourse
  * return: void
  * Author: zjh
  * @Date: 2020/4/7 17:46
  */
  void update(WeekCourse weekCourse);
  /**
  * description: 根据专业班级获取课程表信息
  * @param: condition
  * return: List<WeekCourse>
  * Author: zjh
  * @Date: 2020/4/7 17:16
  */
  List<WeekCourse> getWeekCourse(@Param("condition") Map<String, Object> condition);
}
