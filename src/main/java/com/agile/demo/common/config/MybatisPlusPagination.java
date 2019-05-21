package com.agile.demo.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liuyi
 * @date 2019/5/8
 * Mybatis-plus分页插件
 */

@EnableTransactionManagement
@Configuration
@MapperScan("com.agile.demo.*.mapper*")
public class MybatisPlusPagination {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
