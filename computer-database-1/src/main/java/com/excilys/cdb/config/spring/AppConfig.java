package com.excilys.cdb.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan({"com.excilys.cdb.computerrowmapper","com.excilys.cdb.dao","com.excilys.cdb.mapper","com.excilys.cdb.validateur","com.excilys.cdb.service"})
public class AppConfig {
	
}
