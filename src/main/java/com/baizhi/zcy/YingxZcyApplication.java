package com.baizhi.zcy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.baizhi.*")
@tk.mybatis.spring.annotation.MapperScan("com.baizhi.zcy.dao")
@MapperScan("com.baizhi.zcy.dao")

public class YingxZcyApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxZcyApplication.class, args);
    }

}
