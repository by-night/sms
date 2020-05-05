package com.zjh.sms.service.User.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zjh.sms.dao.User.AdminMapper;
import com.zjh.sms.dao.User.StudentMapper;
import com.zjh.sms.dao.User.TeacherMapper;
import com.zjh.sms.dao.User.UserMapper;
import com.zjh.sms.dao.Upload.UploadMapper;
import com.zjh.sms.domain.Upload;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description 登陆用户业务层
 * Author: zjh
 * Date2019/10/21 21:09
 **/
@Service
public class UserServiceImpl implements UserService {
  @Resource
  private UserMapper userMapper;
  @Resource
  private StudentMapper studentMapper;
  @Resource
  private TeacherMapper teacherMapper;
  @Resource
  private AdminMapper adminMapper;

  @Override
  public User getStudentInfo(Map<String, Object> condition) {
    String keyValue = condition.get("level").toString();
    if (keyValue.equals("0")) {
      return userMapper.getAdminInfo(condition);
    } else if (keyValue.equals("1")) {
      return userMapper.getTeacherInfo(condition);
    } else {
      return userMapper.getStudentInfo(condition);
    }
  }

  @Override
  public boolean update(Map<String, Object> condition) {
    switch (condition.get("level").toString()) {
      case "0":
        condition.put("table", "admin");
        break;
      case "1":
        condition.put("table", "teacher");
        break;
      case "2":
        condition.put("table", "student");
        break;
    }
    Integer num = userMapper.checkPasswordCount(condition);
    if (num != 0) {
      userMapper.update(condition);
    }
    return num != 0;
  }

  @Override
  public List<Object> getTree() {
    // 获取专业
    List<User> studentList = studentMapper.getStudentTree();
    Set<User> studentSet = new HashSet<>(studentList);

//  转化为前端树形结构所需的数据格式
    ArrayList<Object> list = new ArrayList<>();
    for (User user : studentSet) {
      Map<String, Object> treeObj = new HashMap<>();
      ArrayList<Object> childrenList = new ArrayList<>();
      treeObj.put("label", user.getProfession());
      List<String> gradeList = studentMapper.getGradeByProfession(user.getProfession());

      // 转成int，然后再排序
      List<Integer> list1 = new ArrayList<>();
      for (String str : new HashSet<>(gradeList)) {
        int number = Integer.parseInt(str);
        list1.add(number);
      }
      Collections.sort(list1);

      for (Integer i : list1) {
        Map<String, Object> obj = new HashMap<>();
        obj.put("label", i.toString());
        childrenList.add(obj);
      }
      treeObj.put("children", childrenList);
      list.add(treeObj);
    }

    ArrayList<Object> treeList = new ArrayList<>();
    Map<String, Object> studentObj = new HashMap<>();
    Map<String, Object> teacherObj = new HashMap<>();
    Map<String, Object> adminObj = new HashMap<>();
    studentObj.put("label", "学生");
    studentObj.put("children", list);
    teacherObj.put("label", "教师");
//    teacherObj.put("children", list);
    adminObj.put("label", "管理员");
    treeList.add(studentObj);
    treeList.add(teacherObj);
    treeList.add(adminObj);
    return treeList;
  }

  @Override
  public String getToken(User user, long time) {
    Date start = new Date();
    long currentTime = System.currentTimeMillis() + time;//一小时有效时间
    Date end = new Date(currentTime);
    String token = "";
    token = JWT.create().withAudience(user.getLevel().toString()+user.getId().toString()).withIssuedAt(start).withExpiresAt(end)
       // 储存id和level
        .sign(Algorithm.HMAC256(user.getPassword())); // 储存password，用于解密
    return token;
  }

  @Override
  public User findUser(Map<String, Object> condition) {
    String id = condition.get("id").toString();
    String level = condition.get("level").toString();
    User user = new User();
    switch (level) {
      case "0":
        user = adminMapper.getUserById(id);
        break;
      case "1":
        user = teacherMapper.getUserById(id);
        break;
      case "2":
        user = studentMapper.getUserById(id);
        break;
    }
    return user;
  }

  @Override
  public boolean getSilent() {
    int value = userMapper.getSilent();
    return value == 1;
  }

  @Override
  public boolean setSilent(Integer state) {
    userMapper.setSilent(state);
    return state == 1;
  }

}
