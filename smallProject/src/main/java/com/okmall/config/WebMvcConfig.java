package com.okmall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Value("${uploadPath}")
	private String uploadPath;
	/*
		addResourceHanlders() : resource 를 핸들링 하는 메소드
		
		addResourceHandler("/images/**") : project 내부 경로 - 웹에서 접근 경로 논리경로
		addResourceLocations(uploadPath) : 실제 PC(Server)에서의 경로 = 물리적경로
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//여기서 루트는 리소스의 스태틱폴더이다
		//논리적 경로라 실제로 없어도 동작은 한다
		registry.addResourceHandler("/images/**")
				.addResourceLocations(uploadPath);
	}
}
