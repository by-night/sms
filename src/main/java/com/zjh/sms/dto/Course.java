package com.zjh.sms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 课程信息实体类
 * Author: zjh
 * Date2020/3/11 14:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
  /**
   * id
   */
  private String id;
  /**
   * 课程名
   */
  private String name;
  /**
   * 学分
   */
  private Double credits;
  /**
   * 绩点
   */
  private Double point;
  /**
   * 满分   */
  private Integer score;
  /**
   * 课时
   */
  private Integer number;
  /**
   * 届时
   */
  private Integer year;
  /**
   * 学期
   */
  private Integer term;
  /**
   * 类型 1： 必修  2：选修
   */
  private Integer type;
  /**
   * 专业
   */
  private String professional;
  /**
   * 姓名
   */
  private String realName;
  /**
   * 学号
   */
  private String no;
}
