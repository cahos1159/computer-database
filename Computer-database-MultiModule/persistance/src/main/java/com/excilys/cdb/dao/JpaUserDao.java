package com.excilys.cdb.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.User;

@Repository
public interface JpaUserDao extends CrudRepository<User, Integer>{
	
	User findByUsername(String username);
	
		
}
