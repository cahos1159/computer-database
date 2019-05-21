package com.excilys.cdb.config.spring;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan({"com.excilys.cdb.computerrowmapper","com.excilys.cdb.config","com.excilys.cdb.controller.web","com.excilys.cdb.dao","com.excilys.cdb.mapper","com.excilys.cdb.validateur"})
public class WebMvcConfig extends WebMvcConfigurerAdapter  {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/AppCdb/**").addResourceLocations("/AppCdb/");
	}
	
	@Override
	   public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
	       exceptionResolvers.add((HandlerExceptionResolver) new ExceptionHandlerEntities());
	   }
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}