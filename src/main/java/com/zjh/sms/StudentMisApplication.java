package com.zjh.sms;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.zjh.sms.service.Upload.impl.UploadServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.zjh.student_mis.dao")
public class StudentMisApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudentMisApplication.class, args);
  }

}
