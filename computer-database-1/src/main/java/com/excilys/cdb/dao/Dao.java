package com.excilys.cdb.dao;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.database.DataBaseAccess;
import com.excilys.cdb.model.Model;


@Scope(value="singleton")
@Repository
public abstract class Dao<T extends Model> {

	
	protected DataBaseAccess dataBase;
	
	protected final String sqlCreate;
	protected final String sqlUpdate;
	protected final String sqlDelete;
	protected final String sqlSelect;
	protected final String sqlListAll;
	protected final String sqlList;
	protected final String sqlSearch;

	public Dao(String sqlCreate, String sqlUpdate, String sqlDelete, String sqlSelect, String sqlListall, String sqlList, String sqlSearch) {
		this.sqlCreate = sqlCreate;
		this.sqlUpdate = sqlUpdate;
		this.sqlDelete = sqlDelete;
		this.sqlSelect = sqlSelect;
		this.sqlListAll = sqlListall;
		this.sqlList = sqlList;
		this.sqlSearch = sqlSearch;
		this.dataBase= new DataBaseAccess();
		
	}
	
	public abstract T create(T obj) throws Exception;
	public abstract T update(T obj) throws Exception;
	public abstract T delete(T obj) throws Exception;
	public abstract T deleteById(int i) throws Exception;
	public abstract T read(int id) throws  Exception;
	public abstract List<T> listAll() throws Exception;
	public abstract List<T> list(Page page) throws Exception;
	
}
