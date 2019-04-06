package com.catsix.systemprojectcatsix;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.catsix.systemprojectcatsix.mapper"})
public class SystemProjectCatsixApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemProjectCatsixApplication.class, args);
	}
}
