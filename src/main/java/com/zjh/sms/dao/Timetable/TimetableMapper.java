package com.zjh.sms.dao.Timetable;

import com.zjh.sms.dto.Timetable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 课程表Mapper层
 * Author: zjh
 * Date2020/4/7 14:18
 **/
@Mapper
public interface TimetableMapper {
  /**
   * description: 新增课程表
   * @param timetable
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void add(Timetable timetable);

  /**
   * description: 获取timetable课程表信息
   * @param condition
   * @author zjh
   * @return List<Timetable>
   * @date 2020/3/4 23:05
   */
  List<Timetable> getTimetable(@Param("condition") Map<String, Object> condition);

  /**
  * description: 根据专业班级查看课程表数量（验证是否添加课程表）
  * @param: Map<String, Object> condition
  * return: Integer
  * Author: zjh
  * @Date: 2020/4/7 17:24
  */
  Integer checkCount(@Param("condition") Map<String, Object> condition);
  /**
  * description: 删除 timetable表
  * @param: Map<String, Object> condition
  * return:void
  * Author: zjh
  * @Date: 2020/4/7 17:34
  */
  void deleteTimeTable(@Param("condition") Map<String, Object> condition);
}
