package com.zjh.student_mis.service;

import com.zjh.student_mis.dto.User;

import java.util.List;
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
  User getUserInfo(Map<String, Object> condition);
}