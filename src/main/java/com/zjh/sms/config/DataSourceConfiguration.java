package com.zjh.sms.config;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

/**
 * Description
 * Author: zjh
 * Date2020/2/26 23:39
 **/
@Configuration
// 配置mybatis的扫描路径
@MapperScan("com.zjh.student_mis.dao")
public class DataSourceConfiguration {
  @Value("${jdbc.driver}")
  private String jdbcDriver;
  @Value("${jdbc.url}")
  private String jdbcUrl;
  @Value("${jdbc.username}")
  private String jdbcUsername;
  @Value("${jdbc.password}")
  private String jdbcPassword;

  @Bean(name="dataSource")
  public ComboPooledDataSource createDataSource() throws PropertyVetoException {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setDriverClass(jdbcDriver);
    dataSource.setJdbcUrl(jdbcUrl);
    dataSource.setUser(jdbcUsername);
    dataSource.setPassword(jdbcPassword);
    // 关闭连接后不自动commit
    dataSource.setAutoCommitOnClose(false);
    return dataSource;
  }
}
