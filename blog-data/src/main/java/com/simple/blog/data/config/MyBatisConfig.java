package com.simple.blog.data.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis 配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.simple.blog.data.mapper")
public class MyBatisConfig {
}
