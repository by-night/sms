package com.zjh.sms.controller.Score;

import com.zjh.sms.dto.Course;
import com.zjh.sms.dto.User;
import com.zjh.sms.service.Course.CourseService;
import com.zjh.sms.service.Score.ScoreService;
import com.zjh.sms.utils.PagingResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * Description 成绩查询控制层
 * Author: zjh
 * Date2020/3/13 15:25
 **/

@RestController
@RequestMapping("/api/sms/score")
public class ScoreController {

  @Autowired
  private ScoreService scoreService;
  @GetMapping("/getCourseList")
  public PagingResult<Course> getCourseList (@RequestParam Map<String, Object> condition,
                                             @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
                                             @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    RowBounds rowBounds = new RowBounds(offset, limit);
    return scoreService.getCourseList(rowBounds, condition);
  }
}
