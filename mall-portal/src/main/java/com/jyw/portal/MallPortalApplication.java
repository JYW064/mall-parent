package com.jyw.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRabbit
@MapperScan("com.jyw.portal.mapper")
@SpringBootApplication(scanBasePackages = "com.jyw")
public class MallPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallPortalApplication.class, args);
	}

}
