package com.excilys.cdb.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.model.Computer;

@Scope(value="singleton")
@Component
public class ComputerService extends Service<Computer> {


	public ComputerService(ComputerDao compDao) {
		super(compDao);
	}



	public List<Computer> computerSearch(Page page ,String keyWord) throws Exception {
		return  ((ComputerDao) this.dao).computerSearch(page,keyWord);
	}

	public List<Computer> computerOrder(Page page ,String colonne,int mode) throws Exception {
		return ((ComputerDao) this.dao).computerOrder(page,colonne,mode);
	}
	
	public List<Computer> computerOrderSearch(Page page ,String colonne,int mode,String keyWord ) throws Exception {
		return ((ComputerDao) this.dao).computerOrderSearch(page,colonne,mode,keyWord);
	}
	
	public int count(String keyWord, int mode) {
		return ((ComputerDao) this.dao).count(keyWord,mode);
	}
	
	
}
