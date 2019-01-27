package com.ifood.ifood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackageClasses = IfoodApplication.class)
public class IfoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(IfoodApplication.class, args);
	}

}

