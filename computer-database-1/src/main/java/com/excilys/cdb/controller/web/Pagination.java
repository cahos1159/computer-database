package com.excilys.cdb.controller.web;

import java.util.List;

import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;

public class Pagination {
	
	private static Pagination instance = new Pagination();
	
	public Pagination() {}
	
	public static Pagination getInstance() {
		return instance;
	}
	
	public List<Computer> MiseEnPage(List<Computer> elem,Page page) throws Exception {
		int size = elem.size();
		
		if (size == 0)
			return null;
		if (size == 1) {
			return elem ;
		}
		if (size <= page.getNbElem())
			return elem.subList(0, size - 1);
		int nbPage = (size % page.getNbElem() == 0) ? Integer.valueOf(size / page.getNbElem()) : Integer.valueOf(size / page.getNbElem()) + 1;
		if (page.getNumero() == nbPage) {
			return elem.subList((page.getNumero() - 1) * page.getNbElem(), size - 1);
		} else
			return elem.subList((page.getNumero() - 1) * page.getNbElem(), (page.getNumero() * page.getNbElem()) - 1);

	}
	
	
	


}
