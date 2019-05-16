package com.excilys.cdb.controller.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validateur.Validateur;

public abstract class AbstractServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected static  ComputerMapper cuterMap;

	protected static CompanyMapper canyMap;

	protected static ComputerService cuterServ;

	protected static CompanyService canyServ;
	
	protected static ApplicationContext ctx;
	
	protected final Validateur val;
    /**
	 * 
	 */
	
	
	
	static {
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		cuterServ = ctx.getBean(ComputerService.class);
		cuterMap = ctx.getBean(ComputerMapper.class);
		canyServ = ctx.getBean(CompanyService.class);
		canyMap = ctx.getBean(CompanyMapper.class);
	}

	public AbstractServlet() throws ServletException {
			
			
			this.val = ctx.getBean(Validateur.class);
		        
		  
	}
		
  
}