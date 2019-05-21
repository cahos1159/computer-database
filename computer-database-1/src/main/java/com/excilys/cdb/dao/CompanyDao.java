package com.excilys.cdb.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.exception.InvalidPageSizeException;
import com.excilys.cdb.exception.InvalidPageValueException;
import com.excilys.cdb.model.Company;

@Scope
@Repository
public class CompanyDao extends Dao<Company>{

	private JdbcTemplate jdbcTemplate;
	private static Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	private CompanyDao() throws SQLException {
		super(
			"INSERT INTO company VALUES (?,?);",
			"UPDATE company SET name=? WHERE id=?;",
			"DELETE FROM company WHERE id=?;",
			"SELECT * FROM company WHERE id=?;",
			"SELECT * FROM company;",
			"SELECT * FROM company LIMIT ?,?;",
			"SELECT * FROM computer WHERE name LIKE(?)"
			
		);
		this.jdbcTemplate = new JdbcTemplate(dataBase.getDataSource());
	}
	
	


	@Override
	public int create(Company elem) throws Exception{
		return jdbcTemplate.update( this.sqlCreate,new Object[] { elem.getId(), elem.getName()});
	}

	@Override
	public int update(Company elem) throws Exception {
		return jdbcTemplate.update( this.sqlUpdate, new Object[] {elem.getName(),elem.getId()});
	}
	
	@Override
	public int delete(Company obj) throws Exception {
		return this.deleteById(obj.getId());
	}
	
	@Override
	public int deleteById(int id) throws Exception {
		return jdbcTemplate.update( this.sqlDelete, id);
	}

	@Override
	public Company read(int id) throws Exception {
		return jdbcTemplate.queryForObject(this.sqlSelect,new Object[] {id}, canyRM);
	}
	
	@Override
	public List<Company> listAll() throws Exception {
		return (List<Company>) jdbcTemplate.query( this.sqlListAll, canyRM);
	}
	
	@Override
	public List<Company> list(Page page) throws Exception {
		if (page.getNbElem() <= 0) {
			logger.error("",new InvalidPageSizeException(page.getNbElem()));
			throw new InvalidPageSizeException(page.getNbElem());
		}
		if (page.getNumero() <= 0) {
			logger.error("",new InvalidPageValueException(page.getNumero()));
			throw new InvalidPageValueException(page.getNumero());
		}
		int offset = (page.getNumero()-1)*page.getNbElem();
		return  jdbcTemplate.query( this.sqlList,new Object[] { offset,page.getNbElem()+offset }, canyRM);
	}
}
