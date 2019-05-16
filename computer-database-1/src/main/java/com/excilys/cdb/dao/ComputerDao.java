package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

@Scope(value="singleton")
@Repository
public class ComputerDao extends Dao<Computer>{
	private static final Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	
	private static final String ORDER1 ="SELECT * FROM computer ORDER BY ";
	private static final String ORDER2 =" LIMIT ?,?;";
	private static final String ORDERSEARCH1 ="SELECT * FROM computer WHERE name LIKE ? ORDER BY ";
	private static final String ORDERSEARCH2 =" LIMIT ?,?;";
	private static final String INTRO = "introduced";
	private static final String DISC = "discontinued";
	private static final String COMPANY = "company";
	private static final String COMPANYID = "company_id";
	private JdbcTemplate jdbcTemplate;

	private String sqlOrder; 
	public ComputerDao() throws SQLException {
		super(
			"INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?);",
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;",
			"DELETE FROM computer WHERE id=?;",
			"SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;",
			"SELECT id,name,introduced,discontinued,company_id FROM computer;",
			"SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?,?;",
			"SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ? LIMIT ?,?;"
			
		);
		this.jdbcTemplate = new JdbcTemplate(dataBase.getDataSource());
	}
	static Map<String,String>  att = new HashMap<>();
	static {		
		att.put("name", "name");
		att.put(INTRO, INTRO);
		att.put(DISC, DISC);
		att.put(COMPANY, COMPANY);
	}
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
	    this.jdbcTemplate = jdbcTemplate;  
	}  
	
	@Override
	
	public int create(Computer elem) throws Exception {
		return jdbcTemplate.update( this.sqlCreate, elem.getName(), elem.getDateIntro(),elem.getDateDisc(),elem.getManufacturer());
	}

	@Override
	public int update(Computer elem) throws Exception {
		
		return jdbcTemplate.update( this.sqlUpdate, elem.getName(), elem.getDateIntro(),elem.getDateDisc(),elem.getManufacturer(),elem.getId());
	}
	
	@Override
	public int delete(Computer obj) throws Exception {
		return this.deleteById(obj.getId());
	}
	
	public int deleteById(int id) throws SQLException {
		return jdbcTemplate.update( this.sqlDelete, id);
	}


	@Override
	public Computer read(int id) throws SQLException {
		return (Computer) jdbcTemplate.query( this.sqlSelect,new Object[] {id}, cuterRM);
	}
	
	@Override
	public List<Computer> listAll() throws Exception {
		return (List<Computer>) jdbcTemplate.query( this.sqlListAll, cuterRM);
	}
	
	
	
	@Override
	public List<Computer> list(Page page) throws Exception {
		
		if (page.getNbElem() <= 0) {
			 logger.error("",new InvalidPageSizeException(page.getNbElem()));
			 throw new InvalidPageSizeException(page.getNbElem());
		}
		if (page.getNumero() <= 0) {
			logger.error("",new InvalidPageValueException(page.getNumero()));
			throw new InvalidPageValueException(page.getNumero());
		}
		int offset = (page.getNumero()-1)*page.getNbElem();
		return  jdbcTemplate.query( this.sqlList,new Object[] { offset,page.getNbElem()+offset }, cuterRM);
	}
	
	
	public List<Computer> computerSearch(Page page ,String keyWord) throws Exception {
		if (page.getNbElem() <= 0) {
			throw new InvalidPageSizeException(page.getNbElem());
		}
		if (page.getNumero() <= 0) {
			throw new InvalidPageValueException(page.getNumero());
		}
		if(keyWord == null) {
			return listAll();
		}
		int offset = (page.getNumero()-1)*page.getNbElem();
		
		return jdbcTemplate.query( this.sqlSearch,new Object[] { "%"+keyWord+"%",offset,page.getNbElem()+offset }, cuterRM);

	}
	
	public List<Computer> computerOrder(Page page ,String colonne, int chx) throws Exception {
		if (page.getNbElem() <= 0) {
			logger.error("",new InvalidPageSizeException(page.getNbElem()));
			throw new InvalidPageSizeException(page.getNbElem());
		}
		if (page.getNumero() <= 0) {
			logger.error("",new InvalidPageSizeException(page.getNumero()));
			throw new InvalidPageValueException(page.getNumero());
		}
		int offset = (page.getNumero()-1)*page.getNbElem();
		String mode = chx == 0 ? "ASC":"DESC";
		
		
		if(!att.containsKey(colonne)) return list(page);
		String requete = ORDER1+att.get(colonne)+" "+mode+ORDER2;
		if(colonne == null ||  "".equals(colonne)) {
			return list(page);
		}
	
		return jdbcTemplate.query( requete,new Object[] { offset,page.getNbElem()+offset }, cuterRM);
	}
	
	
	public List<Computer> computerOrderSearch(Page page ,String colonne, int chx, String keyWord) throws Exception {
		if (page.getNbElem() <= 0) {
			logger.error("",new InvalidPageSizeException(page.getNbElem()));
			throw new InvalidPageSizeException(page.getNbElem());
		}
		if (page.getNumero() <= 0) {
			logger.error("",new InvalidPageSizeException(page.getNumero()));
			throw new InvalidPageValueException(page.getNumero());
		}
		int offset = (page.getNumero()-1)*page.getNbElem();
		String mode = chx == 0 ? "ASC":"DESC";
		if(!att.containsKey(colonne))return computerSearch(page,keyWord);
		String requete = ORDERSEARCH1+att.get(colonne)+" "+mode+ORDERSEARCH2;
		if(colonne == null || "".equals(colonne)) {
			return computerSearch(page,keyWord);
		}
		keyWord = "%" + keyWord + "%";
		return jdbcTemplate.query( requete,new Object[] { keyWord,offset,page.getNbElem()+offset }, cuterRM);

	}
	
	public int count(String keyWord, int mode) {
		String stat;
		keyWord = "%"+keyWord+"%";
		if(mode == 1) {
			stat = "SELECT COUNT(*) FROM computer WHERE name LIKE ?;";
			return jdbcTemplate.queryForObject(stat,new Object[] {keyWord}, Integer.class);
		}
		else {
			stat = "SELECT COUNT(*) FROM computer;";
			return jdbcTemplate.queryForObject(stat, Integer.class);
		}
		
	}
}
