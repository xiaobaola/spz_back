package com.spz.entrance;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@ComponentScans({@ComponentScan("com.spz.entrance.config"),@ComponentScan("com.spz.**.service"),@ComponentScan("com.spz.**.controller")})
@MapperScan("com.spz.**.mapper")
public class SpzApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpzApplication.class, args);
    }

}
