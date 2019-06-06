package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.persistanceconfig.PersistanceConfig;

@Configuration
@Import({PersistanceConfig.class})
@ComponentScan({"com.excilys.cdb.service","com.excilys.cdb.mapper"})
public class Config {

}
