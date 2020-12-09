package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@MapperScan(value= "com.example.demo.mapper.UserMapper")
//@ComponentScan(basePackages = "com.example.demo.mapper")
@SpringBootApplication()
public class DemoApplication {

    public static void main (String[] args) {
        SpringApplication.run (DemoApplication.class, args);
    }

}
