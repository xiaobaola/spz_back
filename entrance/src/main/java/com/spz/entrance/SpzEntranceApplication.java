package com.spz.entrance;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@ComponentScans({
        @ComponentScan("com.spz.**.controller"),
        @ComponentScan("com.spz.**.service"),
        @ComponentScan("com.spz.**.interceptor"),
        @ComponentScan("com.spz.**.config"),
        @ComponentScan("com.spz.**.aop"),
        @ComponentScan("com.spz.common")
})
@MapperScan("com.spz.**.mapper")
@EnableCaching //开启缓存
public class SpzEntranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpzEntranceApplication.class, args);
    }

}
