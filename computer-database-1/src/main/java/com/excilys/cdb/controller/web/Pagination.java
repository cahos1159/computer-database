package com.excilys.cdb.controller.web;

import java.util.List;

import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.dto.ComputerDto;

public class Pagination {
	
	private static Pagination instance = new Pagination();
	
	public Pagination() {}
	
	public static Pagination getInstance() {
		return instance;
	}
	
	public List<ComputerDto> MiseEnPage(List<ComputerDto> elem, int pageSize, int numeroPage) throws Exception {
		int size = elem.size();
		System.out.println(size + "<>" + pageSize);
		if (size == 0)
			return null;
		if (size == 1) {
			return elem ;
		}
		if (size <= pageSize)
			return elem.subList(0, size - 1);
		int nbPage = (size % pageSize == 0) ? Integer.valueOf(size / pageSize) : Integer.valueOf(size / pageSize) + 1;
		if (numeroPage == nbPage) {
			return elem.subList((numeroPage - 1) * pageSize, size - 1);
		} else
			return elem.subList((numeroPage - 1) * pageSize, (numeroPage * pageSize) - 1);

	}
	
	
	


}
