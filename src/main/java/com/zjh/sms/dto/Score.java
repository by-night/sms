package com.zjh.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 分数信息实体类
 * Author: zjh
 * Date2020/3/19 10:55
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
  /**
   * id
   */
  private Integer id;
  /**
   * 用户名
   */
  private String username;
  /**
   * 课程名
   */
  private String name;
  /**
   * 分数
   */
  private String scoreByUser;
  /**
   * 绩点
   */
  private String pointByUser;
  /**
   * 学分
   */
  private String creditsByUser;
  /**
   * 总分
   */
  private Integer score;
  /**
   * 学分
   */
  private String credits;
  /**
   * 学期
   */
  private Integer term;
  /**
   * 届时
   */
  private Integer year;
  /**
   * 学号
   */
  private String no;
  /**
   * 学生账号id
   */
  private String studentId;
  /**
   * 课程id
   */
  private String courseId;
}
