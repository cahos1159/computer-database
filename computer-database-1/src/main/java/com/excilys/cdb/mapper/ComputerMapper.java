package com.excilys.cdb.mapper;

import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;

@Scope(value="singleton")
@Component
public class ComputerMapper implements Mapper<ComputerDto, Computer>{
	
	
	private final CompanyMapper compMap;
	
	private final CompanyService compServ;
	
	public ComputerMapper(CompanyMapper cMap,CompanyService cServ) {
		this.compMap = cMap;
		this.compServ = cServ;
	}	
	
	@Override
	public Computer dtoToModel(ComputerDto dtoObject){
		if (dtoObject == null) {
			return null;
		} else {
			int id = Integer.parseInt(dtoObject.getId());
			String name = dtoObject.getName();
			Timestamp t1;
			Timestamp  t2;
			t1 = this.castTimestamp(dtoObject.getIntroduction());
			t2 = this.castTimestamp(dtoObject.getDiscontinued());
			int cid = Integer.parseInt(dtoObject.getCompanyId());
			
			
			
			return new Computer(id,name,t1,t2,cid);
		}
	}
	
	private Timestamp castTimestamp(String s) {
		try {
			return (s == null) ? null : Timestamp.valueOf(s);
		} catch (Exception e) {
			throw new InvalidDateValueException(s);
		}
	}
 
	@Override
	public ComputerDto modelToDto(Computer modelObject) throws Exception {
		if (modelObject == null) {
			return null;
		} else {
			return new ComputerDto(
				Integer.toString(modelObject.getId()),
				modelObject.getName(),
				(modelObject.getDateIntro() == null) ? "_" : modelObject.getDateIntro().toString(),
				(modelObject.getDateDisc() == null) ? "_" : modelObject.getDateDisc().toString(),
				(modelObject.getManufacturer() <= 0) ? "" : compMap.modelToDto(compServ.read(modelObject.getManufacturer())).getId(),
				(modelObject.getManufacturer() <= 0) ? "" : compMap.modelToDto(compServ.read(modelObject.getManufacturer())).getName())
			;
		}
	}
	
}
