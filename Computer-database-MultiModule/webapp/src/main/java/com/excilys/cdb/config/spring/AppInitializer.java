package com.excilys.cdb.config.spring;


import com.excilys.cdb.config.spring.WebMvcConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
    @Override
    protected Class<?>[] getRootConfigClasses() {
    	return new Class[] {SecurityConfig.class,WebMvcConfig.class};
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {};
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
    	final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
 }
}