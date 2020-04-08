package com.zjh.sms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * Author: zjh
 * Date2020/4/7 14:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekCourse {
  /**
   * id
   */
  private Integer id;
  /**
   * 周一
   */
  private String monday;
  /**
   * 周二
   */
  private String tuesday;
  /**
   * 周三
   */
  private String wednesday;
  /**
   * 周四
   */
  private String thursday;
  /**
   * 周五
   */
  private String friday;
  /**
   * 周六
   */
  private String saturday;
  /**
   * 周日
   */
  private String sunday;
  /**
   * 专业
   */
  private String profession;
  /**
   * 班级
   */
  private String grade;
}
