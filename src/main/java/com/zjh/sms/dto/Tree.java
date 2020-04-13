package com.zjh.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description 树形实体类
 * Author: zjh
 * Date2020/3/27 22:07
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tree {
  private String name;
  private String [] school;
  private String [] profession;
}
