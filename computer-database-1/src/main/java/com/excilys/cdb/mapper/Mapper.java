package com.excilys.cdb.mapper;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.model.Model;

@Scope(value="singleton")
@Component
public abstract class Mapper<T extends Dto, U extends Model> {

	protected Mapper() {}
	
	public abstract U dtoToModel (T dtoObject) throws RuntimeException;
	public abstract T modelToDto (U modelObject) throws RuntimeException, Exception;
	
	public int idToInt(String id) {
		return Integer.parseInt(id);
	}
}
