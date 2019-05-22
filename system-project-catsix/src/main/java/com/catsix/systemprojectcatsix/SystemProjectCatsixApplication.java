package com.catsix.systemprojectcatsix;

import com.catsix.systemprojectcatsix.properties.FileUploadProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;
import javax.servlet.Filter;

@SpringBootApplication
@EnableConfigurationProperties(	FileUploadProperties.class)
@MapperScan(basePackages = {"com.catsix.systemprojectcatsix.mapper"})
public class SystemProjectCatsixApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemProjectCatsixApplication.class, args);
	}

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}
}
