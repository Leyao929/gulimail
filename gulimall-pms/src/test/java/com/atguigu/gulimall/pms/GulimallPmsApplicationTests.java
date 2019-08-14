package com.atguigu.gulimall.pms;

import com.atguigu.gulimall.pms.config.PmsGlobelTranscationConfig;
import com.atguigu.gulimall.pms.util.StringAppendUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallPmsApplicationTests {

    @Test
    public void contextLoads() {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(PmsGlobelTranscationConfig.class);

        Object dataSource = annotationConfigApplicationContext.getBean("DataSource");

        System.out.println(dataSource);


    }

}
