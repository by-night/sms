package com.zjh.sms.controller.Course;

    import com.zjh.sms.dto.Course;
    import com.zjh.sms.service.Course.CourseService;
    import com.zjh.sms.utils.PagingResult;
    import org.apache.ibatis.session.RowBounds;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.Arrays;
    import java.util.List;
    import java.util.Map;

/**
 * Description 课程控制层
 * Author: zjh
 * Date2020/3/11 14:40
 **/
@RestController
@RequestMapping("/api/sms/course")
public class CourseController {

  @Autowired
  private CourseService courseService;

  @PostMapping
  public void addCourse(@RequestBody Course course) {
    courseService.addCourse(course);
  }

  @DeleteMapping("/{ids}")
  public void delete(@PathVariable("ids") Integer[] ids) {
    List<Integer> idsList = Arrays.asList(ids);
    courseService.delete(idsList);
  }

  @PutMapping
  public void update(@RequestBody Course course) {
    courseService.update(course);
  }

  @GetMapping("/getCourseList")
  private PagingResult<Course> getCourseList (@RequestParam Map<String, Object> condition,
                                              @RequestParam(required = false, name = "$limit", defaultValue = "10") Integer limit,
                                              @RequestParam(required = false, name = "$offset", defaultValue = "0") Integer offset) {
    RowBounds rowBounds = new RowBounds(offset, limit);
    return courseService.getCourseList(rowBounds, condition);
  }

  @GetMapping("/getCourseByMap")
  private List<Course> getCourseByMap (@RequestParam Map<String, Object> condition) {
    return courseService.getCourseByMap(condition);
  }
}
