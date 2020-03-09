package com.zjh.sms.service.User;

import com.zjh.sms.dto.User;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Description 教师用户接口
 * Author: zjh
 * Date2020/3/7 15:05
 **/
public interface TeacherService {
  /**
   * description: 新增教师账号
   * @param user
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void addTeacher(User user);

  /**
   * description: 删除教师账号
   *
   * @param ids
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void delete(List<Integer> ids);

  /**
   * description: 修改教师账号
   *
   * @param user
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void update(User user);

  /**
   * description: 获取教师账号信息列表
   * @param rowBounds
   * @param condition
   * @author zjh
   * @return com.jw.zjh.sms.utils.PagingResult
   * @date 2020/3/4 23:05
   */
  PagingResult<User> getTeacherList(RowBounds rowBounds, Map<String, Object> condition);
}
