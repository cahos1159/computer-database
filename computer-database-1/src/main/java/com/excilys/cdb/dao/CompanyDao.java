package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.exception.FailedSQLQueryException;
import com.excilys.cdb.exception.InvalidIdException;
import com.excilys.cdb.exception.InvalidPageSizeException;
import com.excilys.cdb.exception.InvalidPageValueException;
import com.excilys.cdb.exception.PrimaryKeyViolationException;
import com.excilys.cdb.model.Company;

@Scope(value="singleton")
@Repository
public class CompanyDao extends Dao<Company>{

	private static Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	private CompanyDao() {
		super(
			"INSERT INTO company VALUES (?,?);",
			"UPDATE company SET name=? WHERE id=?;",
			"DELETE FROM company WHERE id=?;",
			"SELECT * FROM company WHERE id=?;",
			"SELECT * FROM company;",
			"SELECT * FROM company LIMIT ?,?;",
			"SELECT * FROM computer WHERE name LIKE(?)"
			
		);
	}
	
	


	@Override
	public Company create(Company obj) throws Exception{
		if(obj.getId() <= 0) {
			logger.error("",new InvalidIdException(obj.getId()));
			throw new InvalidIdException(obj.getId());
		}
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlCreate);
		) {
			preparedStatement.setInt(1,obj.getId());
			preparedStatement.setString(2, obj.getName());
			
			int nbRow = preparedStatement.executeUpdate();
			if (nbRow == 1)
				return obj;
			else {
				logger.error("",new FailedSQLQueryException(this.sqlCreate));
				throw new FailedSQLQueryException(this.sqlCreate);
			}
		} catch (SQLException e) {
			logger.error("",new PrimaryKeyViolationException(obj.getId()));
			throw new PrimaryKeyViolationException(obj.getId());
		}
	}

	@Override
	public Company update(Company obj) throws Exception {
		Company company = this.read(obj.getId());
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlUpdate);
		) {
			company.setName(obj.getName());

			preparedStatement.setString(1, company.getName());
			preparedStatement.setInt(2, company.getId());
			
			if (preparedStatement.executeUpdate() == 1) 
				return company;
			else {
				logger.error("",new FailedSQLQueryException(this.sqlUpdate));
				throw new FailedSQLQueryException(this.sqlUpdate);
			}
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(this.sqlUpdate));
			throw new PrimaryKeyViolationException(obj.getId());
		}
	}

	@Override
	public Company delete(Company obj) throws Exception{
		return this.deleteById(obj.getId());
	}
	
	@Override
	public Company deleteById(int id) throws Exception {
		Company company = this.read(id);
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM computer WHERE company_id=?;");
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlDelete);
			
		) {
			connection.setAutoCommit(false);
			preparedStatement1.setInt(1, id);
			preparedStatement1.executeUpdate();
			preparedStatement.setInt(1, id);
			
			
			if (preparedStatement.executeUpdate() == 1) {
				connection.commit();
				connection.setAutoCommit(true);
				return company;
				}
			else {
				logger.error("",new FailedSQLQueryException(this.sqlDelete));
				throw new FailedSQLQueryException(this.sqlDelete);
			}
		} catch (SQLException e) {
			throw new SQLException();
		}
	}

	@Override
	public Company read(int id) throws Exception {
		if(id <= 0) {
			logger.error("",new InvalidIdException(id));
			throw new InvalidIdException(id);
		}
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlSelect);
		) {
			preparedStatement.setInt(1, id);
			try(ResultSet resultSet = preparedStatement.executeQuery();){
			
			if(resultSet.first()) {
				
				return new Company(id,resultSet.getString("name"));
			} else {
				logger.error("",new InvalidIdException(id));
				throw new InvalidIdException(id);
			}
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(this.sqlSelect));
			throw new FailedSQLQueryException(this.sqlSelect);
			}
		}
	}
	
	@Override
	public List<Company> listAll() throws Exception {
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlListAll);
		) {
			try(ResultSet resultSet = preparedStatement.executeQuery();){
			List<Company> companyList = new ArrayList<>();
			while(resultSet.next()) {
				companyList.add(new Company(resultSet.getInt("id"),resultSet.getString("name")));
			}
			return companyList;
			
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(this.sqlListAll));
			throw new FailedSQLQueryException(this.sqlListAll);
			}
		}
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
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlList);
		) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, page.getNbElem());
			
			try(ResultSet r = preparedStatement.executeQuery();){
			List<Company> lst = new ArrayList<>();
			while(r.next()) {
				lst.add(new Company(r.getInt("id"),r.getString("name")));
			}
			return lst;
			
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(this.sqlList));
			throw new FailedSQLQueryException(this.sqlList);
		
		}
	
	}
	}

}
