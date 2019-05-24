package com.excilys.cdb.computerrowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Scope(value="singleton")
@Component
public class CompanyRowMapper implements RowMapper<Company> {
	 public Company mapRow(ResultSet result, int num) throws SQLException {
		 return new Company(result.getInt("id"),result.getString("name"));
         
     }
}
