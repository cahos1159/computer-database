package com.excilys.cdb.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;



import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.controller.CdbController;
import com.excilys.cdb.userinterface.CdbUi;



public class CdbApplication {

	public static void main(String[] args){  
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		CdbUi c = new CdbUi(System.in,System.out, System.err,ctx.getBean(CdbController.class));
		c.run();
	}
}