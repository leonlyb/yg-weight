package com.example.dechuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.example.dechuan.mapper")
//@ComponentScan(basePackages = {"com.example"})
@SpringBootApplication
public class YgWeighbridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(YgWeighbridgeApplication.class, args);
	}

}
