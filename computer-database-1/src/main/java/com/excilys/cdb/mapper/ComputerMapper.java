package com.excilys.cdb.mapper;

import java.sql.*;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;

public class ComputerMapper extends Mapper<ComputerDto, Computer>{
	private static ComputerMapper instance = new ComputerMapper();	
	
	private ComputerMapper() {}	
	
	public static ComputerMapper getInstance() {
		return instance;
	}
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject) throws RuntimeException {
		if (dtoObject == null) {
			return null;
		} else {
			int id = Integer.parseInt(dtoObject.getId());
			String name = dtoObject.getName();
			Timestamp t1, t2;
			t1 = this.castTimestamp(dtoObject.getIntroduction());
			t2 = this.castTimestamp(dtoObject.getDiscontinued());
			int cid = Integer.parseInt(dtoObject.getCompany().getId());
			
			Computer c = new Computer(id,name,t1,t2,cid);
			
			return c;
		}
	}
	
	private Timestamp castTimestamp(String s) throws RuntimeException {
		try {
			return (s == null) ? null : Timestamp.valueOf(s);
		} catch (Exception e) {
			throw new InvalidDateValueException(s);
		}
	}

	@Override
	public ComputerDto modelToDto(Computer modelObject) throws RuntimeException {
		if (modelObject == null) {
			return null;
		} else {
			return new ComputerDto(
				Integer.toString(modelObject.getId()),
				modelObject.getName(),
				(modelObject.getDateIntro() == null) ? "_" : modelObject.getDateIntro().toString(),
				(modelObject.getDateDisc() == null) ? "_" : modelObject.getDateDisc().toString(),
				(modelObject.getManufacturer() <= 0) ? new CompanyDto("0","") : CompanyService.getInstance().read(Integer.toString(modelObject.getManufacturer())))
			;
		}
	}
	
}
