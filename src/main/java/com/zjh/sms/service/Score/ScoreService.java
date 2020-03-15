package com.zjh.sms.service.Score;

import com.zjh.sms.dto.Course;
import com.zjh.sms.dto.User;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;

/**
 * Description
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
}
