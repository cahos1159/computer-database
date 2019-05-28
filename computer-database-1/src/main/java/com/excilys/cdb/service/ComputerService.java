package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import com.excilys.cdb.dao.JpaComputerDao;
import com.excilys.cdb.model.Computer;

@Scope(value="singleton")
@Component
public class ComputerService  {
	
	@Autowired
	private JpaComputerDao computerJdao;

	
	public List<Computer> listComputer(String keyWord,Pageable page) throws Exception {
		org.springframework.data.domain.Page<Computer> tmp = computerJdao.findAllByNameContains(keyWord,page);
		return  tmp.getContent();
	}
	
	
	public void create(Computer computer) {
		computerJdao.save(computer);
	}
	
	
	public void update(Computer computer) {
		computerJdao.save(computer);
	}
	

	public void delete(int id) {
		computerJdao.deleteById(id);
	}
	
	public int count(String keyWord) {
		return computerJdao.countByNameContains(keyWord);
	}
	
	public Computer read(int id) {
		return computerJdao.findById(id);
	}
	

	
	
}
