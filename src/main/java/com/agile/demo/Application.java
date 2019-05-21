package com.agile.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liuyi
 * @date 2019/4/25
 */

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.agile.demo.*"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("================agile_demo启动成功================");
    }
}
