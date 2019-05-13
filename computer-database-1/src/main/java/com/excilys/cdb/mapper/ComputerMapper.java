package com.excilys.cdb.mapper;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;

@Scope(value="singleton")
@Component
public class ComputerMapper extends Mapper<ComputerDto, Computer>{
	
	@Autowired
	final private CompanyMapper compMap;
	@Autowired
	final private CompanyService compServ;
	
	private ComputerMapper(CompanyMapper cMap,CompanyService cServ) {
		this.compMap = cMap;
		this.compServ = cServ;
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
				(modelObject.getManufacturer() <= 0) ? new CompanyDto("0","") : compMap.modelToDto(compServ.read(modelObject.getManufacturer())))
			;
		}
	}
	
}
