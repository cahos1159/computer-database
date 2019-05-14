package com.excilys.cdb.mapper;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;

@Scope(value="singleton")
@Component
public class CompanyMapper extends Mapper<CompanyDto, Company>{
	
	private CompanyMapper() {}
		
	@Override
	public Company dtoToModel(CompanyDto dtoObject) {
		if (dtoObject == null) {
			return null;
		} else {
			return new Company(
				Integer.parseInt(dtoObject.getId()),
				dtoObject.getName()
			);
		}
	}

	@Override
	public CompanyDto modelToDto(Company modelObject) {
		if (modelObject == null) {
			return null;
		} else {
			return new CompanyDto(
				Integer.toString(modelObject.getId()),
				modelObject.getName()
			);
		}
	}
	
}
