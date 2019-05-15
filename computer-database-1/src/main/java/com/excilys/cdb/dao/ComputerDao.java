package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

@Scope(value="singleton")
@Repository
public class ComputerDao extends Dao<Computer>{
	private static final String SQL_SELECT_UPDATE_COMPANY = "UPDATE computer SET company_id=? WHERE id=?;";
	private static final Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	
	private static final String ORDER1 ="SELECT * FROM computer ORDER BY ";
	private static final String ORDER2 =" LIMIT ?,?;";
	private static final String ORDERSEARCH1 ="SELECT * FROM computer WHERE name LIKE ? ORDER BY ";
	private static final String ORDERSEARCH2 =" LIMIT ?,?;";
	private static final String INTRO = "introduced";
	private static final String DISC = "discontinued";
	private static final String COMPANY = "company";
	private static final String COMPANYID = "company_id";
	
	
	private ComputerDao() {
		super(
			"INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?);",
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;",
			"DELETE FROM computer WHERE id=?;",
			"SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;",
			"SELECT id,name,introduced,discontinued,company_id FROM computer;",
			"SELECT id,name,introduced,discontinued,company_id FROM computer LIMIT ?,?;",
			"SELECT id,name,introduced,discontinued,company_id FROM computer WHERE name LIKE ? LIMIT ?,?;"
			
		);
	}
	static Map<String,String>  att = new HashMap<>();
	static {		
		att.put("name", "name");
		att.put(INTRO, INTRO);
		att.put(DISC, DISC);
		att.put(COMPANY, COMPANY);
	}
	
	
	
	
	@Override
	
	public Computer create(Computer obj) throws Exception {
		int nbRow = 0;
		
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlCreate,Statement.RETURN_GENERATED_KEYS)
		) {
			
			preparedStatement.setString(1, obj.getName());
			preparedStatement.setTimestamp(2, obj.getDateIntro());
			preparedStatement.setTimestamp(3, obj.getDateDisc());
			preparedStatement.setNull(4, java.sql.Types.INTEGER);
			
			nbRow = preparedStatement.executeUpdate();
		
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					obj.setId((int)generatedKeys.getLong(1));
					
					}
				else
					throw new FailedSQLQueryException("id non conforme");
}
		} catch (SQLException e) {
			 logger.error("",new PrimaryKeyViolationException(obj.getId()));
			 throw new PrimaryKeyViolationException(obj.getId());
		}
		
		if (obj.getManufacturer() == 0) {
			if (nbRow == 1) {
				return obj;
			} else {
				 logger.error("",new FailedSQLQueryException(this.sqlCreate));
				 throw new FailedSQLQueryException(this.sqlCreate);
			}
		} else {
			try (
					Connection connection = dataBase.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_UPDATE_COMPANY);
			) {
				preparedStatement.setInt(1, obj.getManufacturer());
				preparedStatement.setInt(2, obj.getId());
				
				nbRow += preparedStatement.executeUpdate();
				if (nbRow == 2) {
					return obj;
				} else {
					 logger.error("",new FailedSQLQueryException(SQL_SELECT_UPDATE_COMPANY));
					 throw new FailedSQLQueryException(SQL_SELECT_UPDATE_COMPANY);
				}
			} catch (SQLException e) {
				 logger.error("", new ForeignKeyViolationException(obj.getManufacturer(), COMPANY));
				 throw new ForeignKeyViolationException(obj.getManufacturer(), COMPANY);
			}
		}
	}

	@Override
	public Computer update(Computer obj) throws Exception {
		Computer returnComputer = this.read(obj.getId());

		
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlUpdate);
		) {
			preparedStatement.setString(1, returnComputer.getName());
			preparedStatement.setTimestamp(2, returnComputer.getDateIntro());
			preparedStatement.setTimestamp(3, obj.getDateDisc());
			if (returnComputer.getManufacturer() == 0) {
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			} else {
				preparedStatement.setInt(4, returnComputer.getManufacturer());
			}
			preparedStatement.setInt(5, obj.getId());

			if (preparedStatement.executeUpdate() == 1) {
				return returnComputer;
			} else {
				 logger.error("",new FailedSQLQueryException(this.sqlUpdate));
				 throw new FailedSQLQueryException(this.sqlUpdate);
			}		
		} catch (SQLException e) {
			 logger.error("",new ForeignKeyViolationException(returnComputer.getManufacturer(), COMPANY));
			 throw new ForeignKeyViolationException(returnComputer.getManufacturer(), COMPANY);
		}
	}

	@Override
	public Computer delete(Computer obj) throws Exception {
		return this.deleteById(obj.getId());
	}
	
	public Computer deleteById(int id) throws SQLException {
		Computer returnComputer = this.read(id);
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlDelete);
		) {
			preparedStatement.setInt(1, id);
			
			if (preparedStatement.executeUpdate() == 1) {
				return returnComputer;
			} else {
				
				 logger.error("",new FailedSQLQueryException(this.sqlDelete));
				 throw new FailedSQLQueryException(this.sqlDelete);
			}
		} catch (SQLException e) {
			 logger.error("",e);
			 throw new SQLException();
		}
	}

	@Override
	public Computer read(int id) throws SQLException {
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
				return new Computer(id,resultSet.getString("name"),resultSet.getTimestamp(INTRO),resultSet.getTimestamp(DISC), resultSet.getInt(COMPANYID));
			} else {
				throw new InvalidIdException(id);
			}
		} catch (SQLException e) {
			 logger.error("",new FailedSQLQueryException(this.sqlSelect));
			 throw new FailedSQLQueryException(this.sqlSelect);
		}
		}
	}
	
	@Override
	public List<Computer> listAll() throws Exception {
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlListAll);
		) {
			
			try(ResultSet resultSet = preparedStatement.executeQuery();){
			List<Computer> computerList = new ArrayList<>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp(INTRO),resultSet.getTimestamp(DISC), resultSet.getInt(COMPANYID)));
			}
			return computerList;
			
		} catch (SQLException e) {
			 logger.error("",new FailedSQLQueryException(this.sqlListAll));
			 throw new FailedSQLQueryException(this.sqlListAll);
			 
		}
		}
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
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlList);
		) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, page.getNbElem());
			
			try(ResultSet resultSet = preparedStatement.executeQuery();){
			List<Computer> computerList = new ArrayList<>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp(INTRO),resultSet.getTimestamp(DISC), resultSet.getInt(COMPANYID)));
			}
			return computerList;
			
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(this.sqlList));
			throw new FailedSQLQueryException(this.sqlList);
		}
		}
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
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.sqlSearch);
		) {
			preparedStatement.setString(1, "%" + keyWord + "%");
			preparedStatement.setInt(2, offset);
			preparedStatement.setInt(3, page.getNbElem());
			
			try(ResultSet resultSet = preparedStatement.executeQuery();){
			List<Computer> computerList = new ArrayList<>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp(INTRO),resultSet.getTimestamp(DISC), resultSet.getInt(COMPANYID)));
			}
			return computerList;
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(this.sqlSearch));
			throw new FailedSQLQueryException(this.sqlSearch);
		}
		}
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
		
		
		if(!att.containsKey(colonne)) return listAll();
		String requete = ORDER1+att.get(colonne)+" "+mode+ORDER2;
		if(colonne == null ||  "".equals(colonne)) {
			return listAll();
		}
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(requete);
		) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, page.getNbElem());
			try(ResultSet resultSet = preparedStatement.executeQuery();){
			List<Computer> computerList = new ArrayList<>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp(INTRO),resultSet.getTimestamp(DISC), resultSet.getInt(COMPANYID)));
			}
			
			return computerList;
		} catch (SQLException e) {
			logger.error("",e);
			throw new FailedSQLQueryException(requete);
		}
		}
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
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(requete);
		) {
			keyWord = "%" + keyWord + "%";
			preparedStatement.setString(1, keyWord);
			preparedStatement.setInt(2, offset);
			preparedStatement.setInt(3, offset+page.getNbElem());
			try(ResultSet resultSet = preparedStatement.executeQuery();){
			List<Computer> computerList = new ArrayList<>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp(INTRO),resultSet.getTimestamp(DISC), resultSet.getInt(COMPANYID)));
			}
			return computerList;
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(requete));
			throw new FailedSQLQueryException(requete);
		}
		}
	}
	
	public int count(String keyWord, int mode) {
		int res;
		String stat;
		if(mode == 1) 
			stat = "SELECT COUNT(*) FROM computer WHERE name LIKE ?;";
		else
			stat = "SELECT COUNT(*) FROM computer;";
		
		try (
				Connection connection = dataBase.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(stat);
			) {
				if(mode==1) preparedStatement.setString(1, "%" + keyWord + "%");
				try(ResultSet resultSet = preparedStatement.executeQuery();){
			
				resultSet.next();
				res = resultSet.getInt(1);
				}
			} catch (SQLException e) {
				logger.error("",new FailedSQLQueryException(stat));
				throw new FailedSQLQueryException(stat);
			}	
		return res;
	}

}
