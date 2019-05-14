package com.excilys.cdb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Computer;

@Scope(value="singleton")
@Component
public class ComputerService extends Service<Computer> {


	private ComputerService(ComputerDao compDao) {
		super(compDao);
	}



	public List<Computer> computerSearch(Page page ,String keyWord) throws Exception {

		List<Computer> res = ((ComputerDao) this.dao).computerSearch(page,keyWord);

		return res;
	}

	public List<Computer> computerOrder(Page page ,String colonne,int mode) throws Exception {

		List<Computer> res = ((ComputerDao) this.dao).computerOrder(page,colonne,mode);

		return res;
	}
	
	public List<Computer> computerOrderSearch(Page page ,String colonne,int mode,String keyWord ) throws Exception {

		List<Computer> res = ((ComputerDao) this.dao).computerOrderSearch(page,colonne,mode,keyWord);

		return res;
	}
	
	public int count(String keyWord, int mode) {
		return (int)((ComputerDao) this.dao).count(keyWord,mode);
	}
	
	
}
