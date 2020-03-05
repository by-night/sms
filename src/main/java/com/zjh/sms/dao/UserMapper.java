package com.zjh.sms.dao;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 登陆用户mapper层
 * Author: zjh
 * Date2019/10/21 21:08
 **/
@Mapper
public interface UserMapper {
  /**
   * description: 根据学生用户、密码登陆
   * @params: condition
   * @return: com.zjh.sms.dto.User
   * @Author: zjh
   * @Date: 2019/10/21 21:05
   */
  User getStudentInfo(@Param("condition") Map<String, Object> condition);
  /**
   * description: 根据老师用户、密码登陆
   * @params: condition
   * @return: com.zjh.sms.dto.User
   * @Author: zjh
   * @Date: 2019/10/21 21:05
   */
  User getTeacherInfo(@Param("condition") Map<String, Object> condition);
  /**
   * description: 根据管理员用户、密码登陆
   * @params: condition
   * @return: com.zjh.sms.dto.User
   * @Author: zjh
   * @Date: 2019/10/21 21:05
   */
  User getAdminInfo(@Param("condition") Map<String, Object> condition);
  /**
   * description: 修改密码
   * @params: condition
   * @return: void
   * @Author: zjh
   * @Date: 2020/3/4 23:05
   */
  void update(@Param("condition") Map<String, Object> condition);
  /**
   * description: 查询是否与原密码相同
   * @params: condition
   * @return: Integer
   * @Author: zjh
   * @Date: 2020/3/4 23:05
   */
  Integer checkPasswordCount(@Param("condition") Map<String, Object> condition);    /**
   /**
   * description: 新增学生账号信息
   * @param user
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void addStudent(User user);
  /**
   * description: 新增教师账号信息
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
   * @return java.util.List<com.zjh.sms.dto.User>
   * @date 2020/3/4 23:05
   */
  List<User> getStudentList(PageRowBounds rowBounds);
  /**
   * description: 获取教师账号信息列表
   * @param rowBounds
   * @author zjh
   * @return java.util.List<com.zjh.sms.dto.User>
   * @date 2020/3/4 23:05
   */
  List<User> getTeacherList(PageRowBounds rowBounds);
}
