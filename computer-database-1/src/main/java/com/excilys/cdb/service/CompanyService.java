package com.excilys.cdb.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Company;


@Scope(value="singleton")
@Component
public class CompanyService extends Service<Company>{
	
	private CompanyService(CompanyDao compDao) {
		super(compDao);
	}
	

}
