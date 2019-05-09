package com.excilys.cdb.dao;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.*;
import com.excilys.cdb.model.*;

// TODO: Throw les exceptions jusqu'au controller

public class ComputerDao extends Dao<Computer>{
	private final String SQL_SELECT_UPDATE_COMPANY = "UPDATE computer SET company_id=? WHERE id=?;";
	private static Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	private static ComputerDao instance = new ComputerDao();
	
	private final String order1 ="SELECT * FROM computer ORDER BY ";
	private final String order2 =" LIMIT ?,?;";
	private final String orderSearch1 ="SELECT * FROM computer WHERE name LIKE ? ORDER BY ";
	private final String orderSearch2 =" LIMIT ?,?;";
	
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
	
	public static ComputerDao getInstance() {
		return instance;
	}

	@Override
	public Computer create(Computer obj) throws Exception {
		int nbRow = 0;
		
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_CREATE,Statement.RETURN_GENERATED_KEYS)
		) {
			//preparedStatement.setInt(1,obj.getId());
			preparedStatement.setString(1, obj.getName());
			preparedStatement.setTimestamp(2, obj.getDateIntro());
			preparedStatement.setTimestamp(3, obj.getDateDisc());
			preparedStatement.setNull(4, java.sql.Types.INTEGER);
			
			nbRow = preparedStatement.executeUpdate();
			System.out.println("----"+obj.getId());
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					obj.setId((int)generatedKeys.getLong(1));
					System.out.println("----"+obj.getId());
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
				 logger.error("",new FailedSQLQueryException(this.SQL_CREATE));
				 throw new FailedSQLQueryException(this.SQL_CREATE);
			}
		} else {
			try (
					Connection connection = dataBase.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_SELECT_UPDATE_COMPANY);
			) {
				preparedStatement.setInt(1, obj.getManufacturer());
				preparedStatement.setInt(2, obj.getId());
				
				nbRow += preparedStatement.executeUpdate();
				if (nbRow == 2) {
					return obj;
				} else {
					 logger.error("",new FailedSQLQueryException(this.SQL_SELECT_UPDATE_COMPANY));
					 throw new FailedSQLQueryException(this.SQL_SELECT_UPDATE_COMPANY);
				}
			} catch (SQLException e) {
				 logger.error("", new ForeignKeyViolationException(obj.getManufacturer(), "company"));
				 throw new ForeignKeyViolationException(obj.getManufacturer(), "company");
			}
		}
	}

	@Override
	public Computer update(Computer obj) throws Exception {
		Computer returnComputer = this.read(obj.getId());

		if (obj.getName().contentEquals("")) {
			returnComputer.setName(obj.getName());
		}
		if (obj.getDateIntro() != null) {
			returnComputer.setDateIntro(obj.getDateIntro());
		}
		if (obj.getDateDisc() != null) {
			returnComputer.setDateDisc(obj.getDateDisc());
		}
		if (obj.getManufacturer() != -1) {
			returnComputer.setManufacturer(obj.getManufacturer());
		}
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_UPDATE);
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
				 logger.error("",new FailedSQLQueryException(this.SQL_UPDATE));
				 throw new FailedSQLQueryException(this.SQL_UPDATE);
			}		
		} catch (SQLException e) {
			 logger.error("",new ForeignKeyViolationException(returnComputer.getManufacturer(), "company"));
			 throw new ForeignKeyViolationException(returnComputer.getManufacturer(), "company");
		}
	}

	@Override
	public Computer delete(Computer obj) throws Exception {
		return this.deleteById(obj.getId());
	}
	
	public Computer deleteById(int id) throws Exception {
		Computer returnComputer = this.read(id);
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_DELETE);
		) {
			preparedStatement.setInt(1, id);
			
			if (preparedStatement.executeUpdate() == 1) {
				return returnComputer;
			} else {
				
				 logger.error("",new FailedSQLQueryException(this.SQL_DELETE));
				 throw new FailedSQLQueryException(this.SQL_DELETE);
			}
		} catch (SQLException e) {
			 logger.error("",e);
			 throw e;
		}
	}

	@Override
	public Computer read(int id) throws RuntimeException {
		if(id <= 0) {
			 logger.error("",new InvalidIdException(id));
			 throw new InvalidIdException(id);
		}
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_SELECT);
		) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.first()) {
				return new Computer(id,resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id"));
			} else {
				throw new InvalidIdException(id);
			}
		} catch (SQLException e) {
			 logger.error("",new FailedSQLQueryException(this.SQL_SELECT));
			 throw new FailedSQLQueryException(this.SQL_SELECT);
		}
	}
	
	@Override
	public List<Computer> listAll() throws Exception {
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_LISTALL);
		) {
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Computer> computerList = new ArrayList<Computer>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
			}
			return computerList;
			
		} catch (SQLException e) {
			 logger.error("",new FailedSQLQueryException(this.SQL_LISTALL));
			 throw new FailedSQLQueryException(this.SQL_LISTALL);
		}
	}
	
	
	
	@Override
	public List<Computer> list(int page, int size) throws Exception {
		if (size <= 0) {
			 logger.error("",new InvalidPageSizeException(size));
			 throw new InvalidPageSizeException(size);
		}
		if (page <= 0) {
			logger.error("",new InvalidPageValueException(page));
			throw new InvalidPageValueException(page);
		}
		int offset = (page-1)*size;
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_LIST);
		) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, size);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Computer> computerList = new ArrayList<Computer>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
			}
			return computerList;
			
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(this.SQL_LIST));
			throw new FailedSQLQueryException(this.SQL_LIST);
		}
	}
	
	
	public List<Computer> computerSearch(int page, int size ,String keyWord) throws Exception {
		if (size <= 0) {
			throw new InvalidPageSizeException(size);
		}
		if (page <= 0) {
			throw new InvalidPageValueException(page);
		}
		if(keyWord == null) {
			return listAll();
		}
		int offset = (page-1)*size;
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_SEARCH);
		) {
			preparedStatement.setString(1, "%" + keyWord + "%");
			preparedStatement.setInt(2, offset);
			preparedStatement.setInt(3, size);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Computer> computerList = new ArrayList<Computer>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
			}
			return computerList;
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(this.SQL_SEARCH));
			throw new FailedSQLQueryException(this.SQL_SEARCH);
		}
	}
	
	public List<Computer> computerOrder(int page, int size ,String colonne, int chx) throws Exception {
		if (size <= 0) {
			logger.error("",new InvalidPageSizeException(size));
			throw new InvalidPageSizeException(size);
		}
		if (page <= 0) {
			logger.error("",new InvalidPageSizeException(page));
			throw new InvalidPageValueException(page);
		}
		int offset = (page-1)*size;
		String mode = chx == 0 ? "ASC":"DESC";
		if(colonne =="name" || colonne =="introduced"||colonne =="discontinued"||colonne =="company") return listAll();
		String requete = order1+colonne+" "+mode+order2;
		if(colonne == null || colonne == "") {
			return listAll();
		}
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(requete);
		) {
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, size);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Computer> computerList = new ArrayList<Computer>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
			}
			System.out.println(computerList.toString());
			return computerList;
		} catch (SQLException e) {
			logger.error("",e);
			throw new FailedSQLQueryException(requete);
		}
	}
	
	
	public List<Computer> computerOrderSearch(int page, int size ,String colonne, int chx, String keyWord) throws Exception {
		if (size <= 0) {
			logger.error("",new InvalidPageSizeException(size));
			throw new InvalidPageSizeException(size);
		}
		if (page <= 0) {
			logger.error("",new InvalidPageSizeException(page));
			throw new InvalidPageValueException(page);
		}
		int offset = (page-1)*size;
		String mode = chx == 0 ? "ASC":"DESC";
		if(colonne =="name" || colonne =="introduced"||colonne =="discontinued"||colonne =="company") return computerSearch(page,size,keyWord);
		String requete = orderSearch1+colonne+" "+mode+orderSearch2;
		if(colonne == null || colonne == "") {
			return computerSearch(page,size,keyWord);
		}
		
		try (
			Connection connection = dataBase.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(requete);
		) {
			
			preparedStatement.setString(1, "%" + keyWord + "%");
			preparedStatement.setInt(2, offset);
			preparedStatement.setInt(3, size);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<Computer> computerList = new ArrayList<Computer>();
			while(resultSet.next()) {
				computerList.add(new Computer(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getTimestamp("introduced"),resultSet.getTimestamp("discontinued"), resultSet.getInt("company_id")));
			}
			return computerList;
		} catch (SQLException e) {
			logger.error("",new FailedSQLQueryException(requete));
			throw new FailedSQLQueryException(requete);
		}
	}
	
	public int count(String keyWord, int mode) {
		int res;
		String stat;
		switch(mode) {
		
		case 1:
			stat = "SELECT COUNT(*) FROM computer WHERE name LIKE ?;";
			break;
	
		default:
			stat = "SELECT COUNT(*) FROM computer;";
		}
		try (
				Connection connection = dataBase.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(stat);
			) {
				if(mode==1) preparedStatement.setString(1, "%" + keyWord + "%");
				ResultSet resultSet = preparedStatement.executeQuery();
				System.out.println("pass");
				resultSet.next();
				res = resultSet.getInt(1);
				System.out.println(res);
			} catch (SQLException e) {
				logger.error("",new FailedSQLQueryException(stat));
				throw new FailedSQLQueryException(stat);
			}	
		return res;
	}

}
