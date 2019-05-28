package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.excilys.cdb.dao.JpaCompanyDao;
import com.excilys.cdb.model.Company;


@Scope(value="singleton")
@Component
public class CompanyService {
	@Autowired
	private JpaCompanyDao companyJdao;
	
	public Company read(int id) {
		return companyJdao.findById(id);
	}
	
	public List<Company> listAll(){
		return companyJdao.findAll();
	}
	

}
