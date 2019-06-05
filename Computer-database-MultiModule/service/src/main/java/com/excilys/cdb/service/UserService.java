package com.excilys.cdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dao.JpaUserDao;
import com.excilys.cdb.model.User;

@Scope
@Component
public class UserService {
	
	@Autowired
	private JpaUserDao userJdao;
	
	public User getUser(String username,String password) {
		BCryptPasswordEncoder hash = new BCryptPasswordEncoder(); 
		return userJdao.findByUsernameAndPassword(username,hash.encode(username));
	}
	
	public void addUser(User us) {
		BCryptPasswordEncoder hash = new BCryptPasswordEncoder();
		System.out.println("coucou");
		us.setMdp(hash.encode(us.getMdp()));
		userJdao.save(us);
	}
}
