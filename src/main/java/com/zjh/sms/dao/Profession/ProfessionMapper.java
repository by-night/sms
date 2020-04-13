package com.zjh.sms.dao.Profession;

import com.zjh.sms.domain.Profession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description 专业信息 Mapper层
 * Author: zjh
 * Date2020/3/31 17:34
 **/
@Mapper
public interface ProfessionMapper {
  /**
  * description: 获取所有专业
  * return: List<Profession>
  * Author: zjh
  * @Date: 2020/3/31 17:34
  */
  List<Profession> getProfessionList();
  /**
  * description: 根据专业名查询专业数量（判断是否存在该专业）
  * @param: String
  * return: Integer
  * Author: zjh
  * @Date: 2020/3/31 17:35
  */
  Integer checkProfessionCount(@Param("name") String name);
  /**
  * description: 新增专业
  * @param: Profession
  * return: void
  * Author: zjh
  * @Date: 2020/3/31 17:36
  */
  void addProfession(Profession profession);
  /**
  * description: 删除专业
  * @param: Integer
  * return: void
  * Author: zjh
  * @Date: 2020/3/31 17:37
  */
  void deleteProfession(@Param("id") Integer id);
  /**
  * description: 根据专业名查询专业信息
  * @param: String
  * return: Profession
  * Author: zjh
  * @Date: 2020/3/31 17:52
  */
  Profession getProfessionIdByName(@Param("name") String name);
}
