package com.zjh.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 登陆信息
 * Author: zjh
 * Date2019/10/21 20:58
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  /**
   * id
   */
  private Integer id;
  /**
   * 用户名
   */
  private String username;
  /**
   * 密码
   */
  private String password;
  /**
   * 确认密码
   */
  private String passwordAgain;
  /**
   * 真实姓名
   */
  private String realName;
  /**
   * 等级
   */
  private Integer level;
  /**
   * 学校
   */
  private String school;
  /**
   * 入学时间
   */
  private String admissionTime;
  /**
   * 电子邮箱
   */
  private String email;
  /**
   * 专业
   */
  private String profession;
  /**
   * 联系电话
   */
  private String phone;
  /**
   *  性别
   */
  private Integer sex;
}
