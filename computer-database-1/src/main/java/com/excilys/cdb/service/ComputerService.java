package com.excilys.cdb.service;

import java.util.List;
import java.util.stream.Collectors;

import com.excilys.cdb.dao.*;
import com.excilys.cdb.dto.*;
import com.excilys.cdb.exception.InvalidIntegerException;
import com.excilys.cdb.mapper.*;
import com.excilys.cdb.model.Computer;

public class ComputerService extends Service<ComputerDto, Computer>{
	private static ComputerService instance = new ComputerService();
	
	private ComputerService() {
		super(ComputerMapper.getInstance(), ComputerDao.getInstance());
	}
	
	public static ComputerService getInstance() {
		return instance;
	}
	
	public List<ComputerDto> computerSearch(String keyWord) throws Exception {
		
		List<ComputerDto> res = this.dao.computerSearch(keyWord).stream().map(s -> mapper.modelToDto(s)).collect(Collectors.toList());
		
		return res;
	}
	
	public List<ComputerDto> MiseEnPageSearch(String keyWord,int pageSize,int numeroPage) throws Exception {
		int size = computerSearch(keyWord).size();
		System.out.println(size+"<>"+pageSize);
		if(size == 0) return null;
		if(size <= pageSize) return computerSearch(keyWord).subList(0, size-1);
		int nbPage = (size % pageSize == 0) ? Integer.valueOf(size/pageSize) : Integer.valueOf(size/pageSize)+1;
		if(numeroPage == nbPage) {
			return computerSearch(keyWord).subList((numeroPage-1)*pageSize, size-1);
		}
		else return computerSearch(keyWord).subList((numeroPage-1)*pageSize, (numeroPage*pageSize)-1);
		 
	
	}
}
