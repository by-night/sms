package com.zjh.sms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 教师课程任命表实体类
 * Author: zjh
 * Date2020/3/29 15:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCourse {
  /**
   * id
   */
  private Integer id;
  /**
   * 教师id
   */
  private String teacherId;
  /**
   * 教师用户名
   */
  private String username;
  /**
   * 课程名
   */
  private String name;
  /**
   * 专业
   */
  private String profession;
  /**
   * 班级
   */
  private String grade;
  /**
   * 学期
   */
  private Integer term;
  /**
   * 节数
   */
  private Integer number;
  /**
   * 教师姓名
   */
  private String realName;
  /**
   * 课程id
   */
  private String courseId;
  /**
   * 学分
   */
  private String credits;
  /**
   * 类型
   */
  private Integer type;
  /**
   * 开始时间
   */
  private Integer start;
  /**
   * 结束时间
   */
  private Integer end;
  /**
   * 教室
   */
  private Integer room;
}
