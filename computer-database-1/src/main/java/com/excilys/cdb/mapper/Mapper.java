package com.excilys.cdb.mapper;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.model.Model;

@Scope(value="singleton")
@Component
public abstract interface Mapper<T extends Dto, U extends Model> {


	
	public abstract U dtoToModel (T dtoObject);
	public abstract T modelToDto (U modelObject) throws Exception;
	
	public static int idToInt(String id) {
		return Integer.parseInt(id);
	}
}
