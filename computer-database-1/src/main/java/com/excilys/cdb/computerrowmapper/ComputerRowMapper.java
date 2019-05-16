package com.excilys.cdb.computerrowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;


@Scope(value="singleton")
@Component
public class ComputerRowMapper implements RowMapper<Computer> {
	 public Computer mapRow(ResultSet result, int num) throws SQLException {
		 return new Computer(result.getInt("id"),result.getString("name"),result.getTimestamp("introduced"),result.getTimestamp("discontinued"),result.getInt("company_id"));
         
     }
}
