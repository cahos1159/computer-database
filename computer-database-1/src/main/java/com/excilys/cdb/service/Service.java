package com.excilys.cdb.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.dao.Dao;
import com.excilys.cdb.exception.InvalidIntegerException;
import com.excilys.cdb.model.Model;

@Scope(value="singleton")
@Component
public abstract class Service<U extends Model> {
	protected Dao<U> dao;
	
	protected Service( Dao<U> dao) {
		this.dao = dao;
	}
	
	public U create(U modelObject) throws Exception {
		return this.dao.create(modelObject);
	}
	
	public U update(U modelObject) throws Exception {
		return this.dao.update(modelObject);
	}
	
	public U delete(U modelObject) throws Exception {
		return this.dao.delete(modelObject);
	}
	
	public U read(int id) throws Exception {
		return this.dao.read(id);
	}
	
	public List<U> listAllElements() throws Exception {
		return (List<U>) this.dao.listAll();
	}
	
	public List<U> list(String pageStr, String sizeStr) throws Exception {
		int numero;
		int size;
		
		try {
			numero = Integer.parseInt(pageStr);
		} catch (IllegalArgumentException e) {
			throw new InvalidIntegerException(pageStr);
		}
		try {
			size = Integer.parseInt(sizeStr);
		} catch (IllegalArgumentException e) {
			throw new InvalidIntegerException(sizeStr);
		}
		Page page = new Page(numero,size);
		
		return this.dao.list(page);
	}
}
