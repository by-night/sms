package com.zjh.sms.service.User.impl;

import com.zjh.sms.dao.User.StudentMapper;
import com.zjh.sms.dao.User.UserMapper;
import com.zjh.sms.dto.Tree;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
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
      for (String str : new HashSet<>(gradeList)) {
        Map<String, Object> obj = new HashMap<>();
        obj.put("label", str);
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
    teacherObj.put("children", list);
    adminObj.put("label", "管理员");
    treeList.add(studentObj);
    treeList.add(teacherObj);
    treeList.add(adminObj);
    return treeList;
  }
}
