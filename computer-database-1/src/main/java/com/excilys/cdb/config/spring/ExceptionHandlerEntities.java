package com.excilys.cdb.config.spring;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.excilys.cdb.controller.web.ResourceNotFound;

@ControllerAdvice
public class ExceptionHandlerEntities 
  extends ResponseEntityExceptionHandler {
 
	@ExceptionHandler(value 
		      = { IllegalArgumentException.class, IllegalStateException.class })
    public String handleResourceNotFoundException() {
    	System.out.println("coucou");
    	return "404";
    }
    
   
    }
