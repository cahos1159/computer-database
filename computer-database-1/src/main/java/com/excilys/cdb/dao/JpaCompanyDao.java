package com.excilys.cdb.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;
@Repository
public interface JpaCompanyDao extends CrudRepository<Company, Integer>{
	
	List<Company> findAll();
	
	Company findById(int id);
	
}