package com.example.dechuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.dechuan.mapper")
//@ComponentScan(basePackages = {"com.example"})
@SpringBootApplication
@EnableScheduling   //开启定时任务
public class YgWeighbridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(YgWeighbridgeApplication.class, args);
	}

}
