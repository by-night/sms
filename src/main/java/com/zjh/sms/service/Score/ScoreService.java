package com.zjh.sms.service.Score;

import com.zjh.sms.dto.Course;
import com.zjh.sms.dto.Score;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Description 分数信息Service类
 * Author: zjh
 * Date2020/3/13 15:47
 **/
public interface ScoreService {
  /**
   * description: 根据班级等信息获取课程信息
   * @param rowBounds
   * @param condition
   * @author zjh
   * @return com.jw.zjh.sms.utils.PagingResult
   * @date 2020/3/4 23:05
   */
  PagingResult<Course> getCourseList(RowBounds rowBounds, Map<String, Object> condition);
  /**
  * description: 录入成绩
  * @param: List<Score>
  * return: void
  * Author: zjh
  * @Date: 2020/3/19 16:23
  */
  void addEntry(List<Score> list);
  /**
  * description: 导出成绩到Excel
  * @param: Map<String, Object> condition
  * return: List<Course>
  * Author: zjh
  * @Date: 2020/3/21 16:26
  */
  List<Course> getExportList(Map<String, Object> condition);
  /**
  * description: 根据成绩划分等级（不及格、及格、优秀）
  * @param: Map<String, Object> condition
  * return: List<Map<String, Object>>
  * Author: zjh
  * @Date: 2020/4/3 22:53
  */
  List<Map<String, Object>> getUserNum(Map<String, Object> condition);
  /**
   * description: 学生用户根据id获取总学分和总绩点
   * @param: Map<String, Object> condition
   * return: Map<String, Object>
   * Author: zjh
   * @Date: 2020/4/3 22:53
   */
  Map<String, Object> getUserTotal(Map<String, Object> condition);
}
