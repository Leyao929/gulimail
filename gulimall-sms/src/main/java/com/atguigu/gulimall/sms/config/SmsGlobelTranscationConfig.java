package com.atguigu.gulimall.sms.config;


import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class SmsGlobelTranscationConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(@Value("${spring.datasource.url}")String url) {

        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setJdbcUrl(url);


        return  hikariDataSource;
    }


    @Primary
    @Bean
    public DataSource dataSource(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

}
