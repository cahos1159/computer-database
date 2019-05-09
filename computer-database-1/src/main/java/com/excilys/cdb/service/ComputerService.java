package com.excilys.cdb.service;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Computer;

public class ComputerService extends Service<ComputerDto, Computer> {
	private static ComputerService instance = new ComputerService();

	private ComputerService() {
		super(ComputerMapper.getInstance(), ComputerDao.getInstance());
	}

	public static ComputerService getInstance() {
		return instance;
	}

	public List<ComputerDto> computerSearch(int page, int size ,String keyWord) throws Exception {

		List<ComputerDto> res = ((ComputerDao) this.dao).computerSearch(page,size,keyWord).stream().map(s -> mapper.modelToDto(s))
				.collect(Collectors.toList());

		return res;
	}

	public List<ComputerDto> computerOrder(int page, int size ,String colonne,int mode) throws Exception {

		List<ComputerDto> res = ((ComputerDao) this.dao).computerOrder(page,size,colonne,mode).stream().map(s -> mapper.modelToDto(s))
				.collect(Collectors.toList());

		return res;
	}
	
	public List<ComputerDto> computerOrderSearch(int page, int size ,String colonne,int mode,String keyWord ) throws Exception {

		List<ComputerDto> res = ((ComputerDao) this.dao).computerOrderSearch(page,size,colonne,mode,keyWord).stream().map(s -> mapper.modelToDto(s))
				.collect(Collectors.toList());

		return res;
	}
	
	public int count(String keyWord, int mode) {
		return (int)((ComputerDao) this.dao).count(keyWord,mode);
	}
	
	
}
