package com.hejun.sys.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
@ConditionalOnClass(value= {PaginationInterceptor.class})
public class MybatisPlusConfig { //分页配置
    @Bean
    public PaginationInterceptor  paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
