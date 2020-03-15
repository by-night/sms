package com.zjh.sms.dao.Score;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dto.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description
 * Author: zjh
 * Date2020/3/14 16:25
 **/
@Mapper
public interface ScoreMapper {
  /**
   * description: 根据专业等获取课程信息
   * param: rowBounds
   * param: condition
   * return: List<Course>
   * Author: zjh
   * @Date: 2020/3/11 15:03
   */
  List<Course> getCourseByMap(PageRowBounds rowBounds, @Param("condition") Map<String, Object> condition);
}
