package com.zjh.sms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 课程具体安排
 * Author: zjh
 * Date2020/5/3 14:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfo {
  /**
   * id
   */
  private Integer id;
  /**
   * 课程id
   */
  private String courseId;
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
   * 专业
   */
  private String profession;
}
