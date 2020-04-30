package com.zjh.sms.controller.User;

import com.zjh.sms.dto.User;
import com.zjh.sms.service.User.AdminService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.List;

/**
 * Description 管理员账户控制层
 * Author: zjh
 * Date2020/3/28 11:05
 **/
@RestController
@RequestMapping("/api/sms/user/admin")
public class AdminController {
  @Autowired
  private AdminService adminService;

  @PostMapping
  public void addAdmin(@RequestBody User user) {
    adminService.add(user);
  }

  @DeleteMapping("/{ids}")
  public void delete(@PathVariable("ids") Integer[] ids) {
    List<Integer> idsList = Arrays.asList(ids);
    adminService.delete(idsList);
  }

  @PutMapping
  public void update(@RequestBody User user) {
    adminService.update(user);
  }

  @GetMapping("/getAdminList")
  public PagingResult<User> getAdminList (@RequestParam Map<String, Object> condition,
                                            @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
                                            @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    RowBounds rowBounds = new RowBounds(offset, limit);
    return adminService.getAdminList(rowBounds, condition);
  }

}
