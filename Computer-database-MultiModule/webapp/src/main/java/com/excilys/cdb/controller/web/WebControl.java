package com.excilys.cdb.controller.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.mapper.UserMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.UserService;
import com.excilys.cdb.validateur.Validateur;

abstract class WebControl {
	
	@Autowired
	ComputerService cuterServ;
	@Autowired
	ComputerMapper	cuterMap;
	@Autowired
	CompanyService canyServ;
	@Autowired
	CompanyMapper canyMap;
	@Autowired
	Validateur val;
	@Autowired
	UserService userServ;
	@Autowired
	UserMapper userMap;
	
}
