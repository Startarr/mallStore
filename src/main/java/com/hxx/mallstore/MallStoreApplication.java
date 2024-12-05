package com.hxx.mallstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hxx.mallstore.mapper")
public class MallStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallStoreApplication.class, args);
    }

}
