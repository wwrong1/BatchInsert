package com.wwr.demo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 应用启动类
 *
 * @author wwr
 * @date 2019/7/2
 * @since JDK 1.8
 */
@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.wwr.demo.mapper")
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }


}