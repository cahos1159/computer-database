package com.excilys.cdb.controller.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validateur.Validateur;

public abstract class AbstractServlet extends HttpServlet {


	
	static protected ComputerMapper c_uterMap;

	static protected CompanyMapper c_anyMap;

	static protected ComputerService c_uterServ;

	static protected CompanyService c_anyServ;
	
	final protected Validateur val;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static protected AutowireCapableBeanFactory ctx;

	public AbstractServlet() throws ServletException {
			ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
			this.c_uterServ = ctx.getBean(ComputerService.class);
			this.c_uterMap = ctx.getBean(ComputerMapper.class);
			this.c_anyServ = ctx.getBean(CompanyService.class);
			this.c_anyMap = ctx.getBean(CompanyMapper.class);
			this.val = ctx.getBean(Validateur.class);
		        
		  
	}
		
    @Override
    public void init() throws ServletException {
        
        
       

    }
}