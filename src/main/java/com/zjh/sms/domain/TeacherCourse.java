package com.zjh.sms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 教师课程任命表
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
}
