package com.zjh.sms.dao.Profession;

import com.zjh.sms.domain.Profession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Description
 * Author: zjh
 * Date2020/3/31 17:34
 **/
@Mapper
public interface ProfessionMapper {
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/3/31 17:34
  */
  List<Profession> getProfessionList();
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/3/31 17:35
  */
  Integer checkProfessionCount(@Param("name") String name);
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/3/31 17:36
  */
  void addProfession(Profession profession);
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/3/31 17:37
  */
  void deleteProfession(@Param("id") Integer id);
  /**
  * description:
  * @param:
  * return:
  * Author: zjh
  * @Date: 2020/3/31 17:52
  */
  Profession getProfessionIdByName(@Param("name") String name);
}
