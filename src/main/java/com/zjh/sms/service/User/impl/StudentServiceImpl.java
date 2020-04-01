package com.zjh.sms.service.User.impl;

import com.github.pagehelper.PageRowBounds;
import com.zjh.sms.dao.Profession.ProfessionMapper;
import com.zjh.sms.dao.User.StudentMapper;
import com.zjh.sms.domain.Profession;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.StudentService;
import com.zjh.sms.service.User.TeacherService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description 学生用户业务层
 * Author: zjh
 * Date2020/3/7 15:09
 **/
@Service
public class StudentServiceImpl implements StudentService {

  @Resource
  private StudentMapper studentMapper;
  @Autowired
  private ProfessionMapper professionMapper;

  @Override
  @Transactional
  public void addStudent(User user) {
    int professionId = 0;
    int count = professionMapper.checkProfessionCount(user.getProfession());
    if (count > 0) {
      Profession profession = professionMapper.getProfessionIdByName(user.getProfession());
      professionId = profession.getId();
    } else {
      Profession profession = new Profession();
      profession.setName(user.getProfession());
      professionMapper.addProfession(profession);
      professionId = profession.getId();
    }
    String professionStr = "";
    if (professionId < 10) {
      professionStr = "0" + Integer.toString(professionId);
    } else {
      professionStr = Integer.toString(professionId);
    }
    Map<String, Object> condition = new HashMap<>();
    condition.put("profession", user.getProfession());
    condition.put("grade", user.getGrade());
    int num = studentMapper.checkCodeCount(condition) + 1;
    String str = "";
    if (num < 10) {
      str = "0" + Integer.toString(num);
    } else if (num < 100) {
      str = Integer.toString(num);
    }
    String no = "3" + user.getAdmissionTime().substring(user.getAdmissionTime().length()-2)
        + "89" + professionStr + user.getGrade().substring(user.getGrade().length()-1) + str;
    user.setId(no);
    user.setUsername(no);
    user.setPassword("123456");
    studentMapper.addStudent(user);
  }

  @Override
  public void
  delete(List<String> ids) {
    studentMapper.delete(ids);
  }

  @Override
  public void update(User user) {
    studentMapper.update(user);
  }

  @Override
  public PagingResult<User> getStudentList(RowBounds rowBounds, Map<String, Object> condition) {
    PageRowBounds pageRowBounds = new PageRowBounds(rowBounds.getOffset(), rowBounds.getLimit());
//    for (int i = 0; i < 30; i++) {
//      User user = new User();
//      user.setRealName(getName().get("name").toString());
//      user.setSchool("福建工程学院");
//      user.setEmail(getEmail());
//      user.setPhone(getTelephone());
//      user.setSex(Integer.parseInt(getName().get("sex").toString()));
//      teacherService.addTeacher(user);
//    }

    List<User> StudentInfoList = studentMapper.getStudentList(pageRowBounds, condition);
    return new PagingResult<>(StudentInfoList, pageRowBounds.getTotal());
  }

  private int getNum(int start,int end) {
    return (int)(Math.random()*(end-start+1)+start);
  }

  private String getTelephone() {
    String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    int index= getNum(0,telFirst.length-1);
    String first=telFirst[index];
    String second=String.valueOf(getNum(1,888)+10000).substring(1);
    String thrid=String.valueOf(getNum(1,9100)+10000).substring(1);
    return first+second+thrid;
  }

  private String getEmail() {
    String base = "0123456789";
    String[] email_suffix="@qq.com".split(",");

      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < 10; i++) {
        int number = (int)(Math.random()*base.length());
        if (i == 0) {
          number = 1;
        }
        sb.append(base.charAt(number));
      }
      sb.append(email_suffix[(int)(Math.random()*email_suffix.length)]);
      return sb.toString();
    }

  private Map<String, Object> getName() {
    Random random = new Random();
    String[] Surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
        "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
        "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
        "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
        "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
        "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季"};
    String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    int index = random.nextInt(Surname.length - 1);
    String name = Surname[index]; //获得一个随机的姓氏
    int i = random.nextInt(3);//可以根据这个数设置产生的男女比例
    Integer sex = 0;
    if(i==2){
      int j = random.nextInt(girl.length()-2);
      if (j % 2 == 0) {
        name = name + girl.substring(j, j + 2);
      } else {
        name = name + girl.substring(j, j + 1);
      }
      sex = 1;
    }
    else{
      int j = random.nextInt(girl.length()-2);
      if (j % 2 == 0) {
        name = name + boy.substring(j, j + 2);
      } else {
        name = name + boy.substring(j, j + 1);
      }
      sex = 0;
    }
    Map<String, Object> map = new HashMap<>();
    map.put("name", name);
    map.put("sex", sex);
    return map;
  }
}
