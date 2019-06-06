package com.excilys.cdb.mapper;

import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Scope(value="singleton")
@Component
public class ComputerMapper {
	

	public ComputerMapper() {

	}	
	
	
	public Computer dtoToModel(ComputerDto dtoObject){
		if (dtoObject == null) {
			return null;
		} else {
			int id = Integer.parseInt(dtoObject.getId());
			String name = dtoObject.getName();
			Timestamp t1;
			Timestamp t2;
			if(dtoObject.getIntroduction().equals("_")) {
				
				t1 = null;
			}
			else {
				
				t1 = this.castTimestamp(dtoObject.getIntroduction());
			}
			if(dtoObject.getDiscontinued().equals("_")) {
				
				t2 = null;
			}
			else {
				
				t2 = this.castTimestamp(dtoObject.getIntroduction());
			}
			int cid = Integer.parseInt(dtoObject.getCompanyId());
				
			return new Computer(id,name,t1,t2,new Company(cid,dtoObject.getCompanyName()));
		}
	}
	
	private Timestamp castTimestamp(String s) {
		try {
			return (s == null) ? null : Timestamp.valueOf(s);
		} catch (Exception e) {
			throw new InvalidDateValueException(s);
		}
	}
 
	
	public ComputerDto modelToDto(Computer modelObject) throws Exception {
		if (modelObject == null) {
			return null;
		} else if(modelObject.getCompany() == null){
			return new ComputerDto(
					Integer.toString(modelObject.getId()),
					modelObject.getName(),
					(modelObject.getDateIntro() == null) ? "_" : modelObject.getDateIntro().toString(),
					(modelObject.getDateDisc() == null) ? "_" : modelObject.getDateDisc().toString(),
					"",
					"Aucune company Spécifiée");
		}
		else {
			return new ComputerDto(
						Integer.toString(modelObject.getId()),
						modelObject.getName(),
						(modelObject.getDateIntro() == null) ? "_" : modelObject.getDateIntro().toString(),
						(modelObject.getDateDisc() == null) ? "_" : modelObject.getDateDisc().toString(),
						(modelObject.getCompany().getId() <= 0) ? "" : Integer.toString(modelObject.getCompany().getId()),
						(modelObject.getCompany().getId() <= 0) ? "" : modelObject.getCompany().getName())
					;
	}
	}
}
