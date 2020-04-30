package com.zjh.sms.dao.User;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description 学生用户mapper层
 * Author: zjh
 * Date2020/3/7 15:11
 **/
@Mapper
public interface StudentMapper {
  /**
   * description: 新增学生账号信息
   * @param user
   * @return void
   * @author zjh
   * @date 2020/3/4 23:05
   */
  void addStudent(User user);

  /**
   * description: 删除学生账号
   *
   * @param ids
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void delete(@Param("ids") List<String> ids);

  /**
   * description: 修改学生账号
   *
   * @param user
   * @return void
   * @author zjh
   * @date 2019/8/29 14:55
   */
  void update(User user);

  /**
   * description: 获取学生账号信息列表
   * @param rowBounds
   * @author zjh
   * @return java.util.List<com.zjh.sms.dto.User>
   * @date 2020/3/4 23:05
   */
  List<User> getStudentList(PageRowBounds rowBounds,  @Param("condition") Map<String, Object> condition);
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/3/27 22:14
  */
  List<User> getStudentTree();
  /**
  * description: 根据专业获取该专业下的所有班级
  * @param: String
  * return: List<String>
  * Author: zjh
  * @Date: 2020/3/28 11:46
  */
  List<String> getGradeByProfession(@Param("code") String code);
  /**
   * description: 查看人数
   * return: Integer
   * Author: zjh
   * @Date: 2020/3/11 15:03
   */
  Integer checkCodeCount(@Param("condition") Map<String, Object> condition);
  /**
   * description: 根据id获取管理员信息
   * @param:
   * return:
   * Author: zjh
   * @Date: 2020/3/30 23:43
   */
  User getUserById(@Param("id") String id);
  /**
  * description: 根据id获取学生信息
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/4/7 21:13
  */
  User getStudentById(@Param("id") String id);
}
