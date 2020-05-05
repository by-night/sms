package com.zjh.sms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
  private String profession;
  /**
   * 班级
   */
  private String grade;
  /**
   * 姓名
   */
  private String realName;
  /**
   * 学号
   */
  private String no;
  /**
   * 学生账号
   */
  private String studentId;
  /**
   * 查询的学生绩点
   */
  private String pointByUser;
  /**
   * 查询的学生分数
   */
  private String scoreByUser;
  /**
   * 查询的学生学分
   */
  private String creditsByUser;
  /**
   *周数 start
   */
  private Integer start;
  /**
   * 周数 end
   */
  private Integer end;
  /**
   * 教室
   */
  private String room;
  /**
   * 创建时间
   */
  private Date createTime;
}
