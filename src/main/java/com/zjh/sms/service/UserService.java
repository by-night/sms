package com.zjh.sms.service;

import com.zjh.sms.dto.User;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;

/**
 * Description 登陆用户接口
 * Author: zjh
 * Date2019/10/21 21:01
 **/
public interface UserService {
  /**
  * description: 根据用户、密码登陆
  * @params: condition
  * @return: com.zjh.student_mis.dto.User
  * @Author: zjh
  * @Date: 2019/10/21 21:05
  */
  User getStudentInfo(Map<String, Object> condition);
  /**
   * description: 修改密码
   * @param condition
   * @return void
   * @author zjh
   * @Date: 2020/3/4 23:05
   */
  boolean update(Map<String, Object> condition);
  /**
   * description: 新增学生账号
   * @param user
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void addStudent(User user);
  /**
   * description: 新增教师账号
   * @param user
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void addTeacher(User user);
  /**
   * description: 获取学生账号信息列表
   * @param rowBounds
   * @author zjh
   * @return com.jw.zjh.sms.utils.PagingResult
   * @date 2020/3/4 23:05
   */
  PagingResult<User> getStudentList(RowBounds rowBounds);
  /**
   * description: 获取教师账号信息列表
   * @param rowBounds
   * @author zjh
   * @return com.jw.zjh.sms.utils.PagingResult
   * @date 2020/3/4 23:05
   */
  PagingResult<User> getTeacherList(RowBounds rowBounds);
}
