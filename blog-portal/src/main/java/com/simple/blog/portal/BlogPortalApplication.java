package com.simple.blog.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 前台应用启动入口
 */
@SpringBootApplication(scanBasePackages = "com.simple.blog")
public class BlogPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogPortalApplication.class, args);
    }

}
