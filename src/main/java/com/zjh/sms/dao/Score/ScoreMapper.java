package com.zjh.sms.dao.Score;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dto.Course;
import com.zjh.sms.dto.Score;
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
  /**
   * description:
   * @param:
   * return:
   * Author: zjh
   * @Date: 2020/3/19 16:23
   */
  void addEntry(Score score);
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/3/20 11:52
  */
  void updateEntry(Score score);
  /**
   * description: 查看是否录入成绩
   * return: Integer
   * Author: zjh
   * @Date: 2020/3/11 15:03
   */
  Integer checkCount(@Param("condition") Map<String, Object> condition);
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/3/21 16:27
  */
  List<Course> getExportList(@Param("condition") Map<String, Object> condition);
}
