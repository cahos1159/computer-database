package com.excilys.cdb.config.spring;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@ControllerAdvice
public class ExceptionHandlerEntities 
  extends ExceptionHandlerExceptionResolver {
 
	@ExceptionHandler(value 
		      = { NoHandlerFoundException.class})
    public String handleResourceNotFoundException() {
    	return "404";
    }
	@ExceptionHandler(value 
		      = { RuntimeException.class})
	public String handleErrorException() {
		return "500";
  }
}
