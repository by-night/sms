package com.zjh.student_mis.dao;

import com.zjh.student_mis.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Description 登陆用户mapper层
 * Author: zjh
 * Date2019/10/21 21:08
 **/
@Mapper
public interface UserMapper {
  /**
   * description: 根据用户、密码登陆
   * @params: condition
   * @return: com.zjh.student_mis.dto.User
   * @Author: zjh
   * @Date: 2019/10/21 21:05
   */
  User getUserInfo(@Param("condition") Map<String, Object> condition);
}
