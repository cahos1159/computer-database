package com.excilys.cdb.service;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.InvalidIntegerException;
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

	public List<ComputerDto> computerSearch(String keyWord) throws Exception {

		List<ComputerDto> res = this.dao.computerSearch(keyWord).stream().map(s -> mapper.modelToDto(s))
				.collect(Collectors.toList());

		return res;
	}

	public List<ComputerDto> computerOrder(String colonne,int mode) throws Exception {

		List<ComputerDto> res = this.dao.computerOrder(colonne,mode).stream().map(s -> mapper.modelToDto(s))
				.collect(Collectors.toList());

		return res;
	}
	
	public List<ComputerDto> computerOrderSearch(String colonne,int mode,String keyWord ) throws Exception {

		List<ComputerDto> res = this.dao.computerOrderSearch(colonne,mode,keyWord).stream().map(s -> mapper.modelToDto(s))
				.collect(Collectors.toList());

		return res;
	}
	
}
