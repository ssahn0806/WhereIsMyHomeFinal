package com.ssafy.house.config;

import javax.servlet.ServletContextListener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.house.listener.AppInitListener;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> appInitListener() {
		ServletListenerRegistrationBean<ServletContextListener> srb = new ServletListenerRegistrationBean<ServletContextListener>();
		srb.setListener(new AppInitListener());
		return srb;
	}
	
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/board/insertForm.do").setViewName("Board/insert_notice");
		registry.addViewController("/board/modifyform.do").setViewName("Board/modify");
		registry.addViewController("/board/guide.do").setViewName("Board/guide");
		registry.addViewController("/house/favorite.do").setViewName("House/favorite");
		
		registry.addViewController("/user/findPasswordForm.do").setViewName("User/findPassword");
		registry.addViewController("/user/loginform.do").setViewName("User/login");
		registry.addViewController("/user/registerform.do").setViewName("User/register");
		registry.addViewController("/user/detail.do").setViewName("User/detail");
		
		registry.addViewController("/index.do").setViewName("index");
		registry.addViewController("/").setViewName("index");
		
		registry.addViewController("/error/error.do").setViewName("Error/error");
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://127.0.0.1:8080").allowedMethods("GET","POST","PUT","DELETE").allowCredentials(true);
	}
}

